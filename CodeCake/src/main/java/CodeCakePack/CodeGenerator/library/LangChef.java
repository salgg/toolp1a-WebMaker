//https://stackoverflow.com/questions/43067869/lexical-analyser-in-java/43067962
//https://github.com/IraKorshunova/JavaCompiler/tree/master/src/token
package CodeCakePack.CodeGenerator.library;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

// main calling for Lang 
// Lang example -->  HTML(TITLE('Hellow')) + (FORM(INPUT[class='input']*5+BUTTON[class='btn'])) 
public class LangChef { 

    public static void test001(String inputStr)
    {
        Lexer lex = new Lexer(inputStr);
        List<Token> list = lex.tokenizer();
        Parser parser = new Parser(list);
        parser.run();

        /*System.out.println("-- test 001:  lexer 1 --");
        for (Token t : list) {
            System.out.println(t.toString());
        }*/
        System.out.println("-- test 002: Parser 1 (factor) -- ");
        System.out.println(parser.factorsToString());
        System.out.println("-- test 003: Parser 2 (terms) -- ");
        System.out.println(parser.termsToString());
    }
}

enum TokenType {
    MINUS("-"),
    PLUS("\\+"),
    MUL("\\*"),
    POPEN("\\("), 
    PCLOSE("\\)"), 
    QOPEN("\\["),
    QCLOSE("\\]"),
    ASSIGN("\\="),
    INT("[0-9]+"), // integer
    STR("\'[^\']\'"), // anything inside ''
    COM("\\w+"), // command or special word
    OTHER(":other:") // for errors or not clasificated
;
    private String regex;

    TokenType(String regex) { 
        this.regex = regex;
    }

    String getRegex () {
        return "^"+this.regex+"$";
    }

}


// indivivual part on the language

class Token {

    private String type;
    private String value;
    
    Token (String type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public String toString() // token's representation
    {   
        String info = this.type + ":" + this.value;
        return info;
    }

    public String getType(){ return this.type; }
    public String getValue(){ return this.value; }


}

// Lexical analisis (token creator)
class Lexer {
    String inputText;

    public Lexer (String inputText)
    {
        this.inputText = inputText;
    }

    // tokenizer() with non optional parameter
    public List<Token> tokenizer(String inputstr)
    {   
        String[] str_tokens = inputstr.split(" ");
        List<Token> tokens = new ArrayList<Token>();
        Boolean match; 
        
        for (String strToken : str_tokens) {
            match = false;
            for(TokenType tt: TokenType.values()){
                Pattern pattern = Pattern.compile(tt.getRegex(), Pattern.CASE_INSENSITIVE); // case sensitive 
                Matcher matcher = pattern.matcher(strToken);
                if(matcher.find()){
                    tokens.add(new Token(tt.name(), strToken));
                    match = true;
                    break; // take te first match only 
                }
            }
            if(!match){ tokens.add(new Token(TokenType.OTHER.toString(), strToken)); }
        }
        return tokens;
    }
    public List<Token> tokenizer(){// tokenizer() with optional parameter
        return this.tokenizer(this.inputText);
    }

}

//** Parser   example->   EXPRESSION:(TERM: TERM:(FACTOR + FACTOR) + TERM:(FACTOR + FACTOR)  ) *//
//** Term examples ->  45 + 45   ,  HTML ,      */
class Parser {
    List<Token> tokens ;
    List<Term> terms;
    List<Factor> factors;
    List<String> errors; // toDo

    Parser (List<Token> tokens) {
        this.tokens = tokens;
    }

    // run (main method)
    public void run(){
        if (this.createfactors()){
            if(this.createTerms()){
                if(!this.createExpressions()){
                    System.out.println("Error creating expression");
                };
            } else {
                System.out.println("Error creating terms");
            };
        } else {
            System.out.println("Error creating factors");
        };
    }

    // expression will be a binary tree structure
    class Expression 
    {

    }

    enum FactorCat 
    {   
        VAL, // VALUE =  INT, STR 
        UNOP, //UNARYOPERATOR =  COM
        BINOP,  //BINARYOPERATOR =  PLUS, MINUS, MUL, ASSIGN
        HMOD, //HIERACHYMODIFICATOR = POPEN, PCLOSE
        ATTMOD,  //ATTRIBUTEMODIFICATOR =  QOPEN, QCLOSE
        ERR; //ERROR =  ERR
    }

    class Factor
    {
        private Token token; // value & type 
        private String cat; // FactorCat
        private Integer id; // identifier
        private Integer termId; 

        Factor(Token token, String cat, Integer id){
            this.token = token;
            this.cat = cat;
            this.id = id;
        }

        public String getValue(){ return this.token.getValue(); } // maybe not good practice
        public Token getToken(){ return this.token; }
        public String getCat(){ return this.cat; }
        public Integer getId(){ return this.id; }
        public Integer getTermId(){ return this.termId; }
        public void setTermId(Integer termId){ this.termId = termId; }

        public String toString(){
            return "id" + this.id.toString() + " cat= " + this.cat + ", token( type= " + this.token.getType() + ", value= " + this.getValue() + ")" ;
        }

    }

    class Term // binary tree nodes
    {   
        private Factor NL, NR, operation; //NL=NodeLeft NR=NodeRight (Node=Factor)
        private Integer id;
        Term (Factor NL, Factor operation, Factor NR, Integer id){ // NL + NR 
            this.NL = NL;
            this.NR = NR;
            this.operation = operation;
            this.id = id;
        }

        public Integer getId(){ return this.id; }
        public Factor getNL(){ return this.NL; }
        public Factor getNR(){ return this.NR; }
        public Factor getOp(){ return this.operation; }
        
        public String toString(){
            return "term #"+ this.id + ": " + this.NL.getToken().getValue().toString() + " " + this.operation.getToken().getValue().toString() + " " + this.NR.getToken().getValue().toString(); 
        }
    }

    // Create factors 
    boolean createfactors() {
        List<Factor> factors = new ArrayList<Factor>();
        Integer id = 0;
        for (Token t : this.tokens) {
            String tt = t.getType();
            Factor f;
            if(tt == TokenType.INT.toString() || tt == TokenType.STR.toString() ){
                f = new Factor(t, FactorCat.VAL.toString(), id);
            } else if(tt == TokenType.COM.toString() ) {
                f = new Factor(t, FactorCat.UNOP.toString(), id);
            } else if(tt == TokenType.PLUS.toString() 
                || tt == TokenType.MINUS.toString()
                || tt == TokenType.MUL.toString()
                || tt == TokenType.ASSIGN.toString()
                ) {
                f = new Factor(t, FactorCat.BINOP.toString(), id);
            } else if(tt == TokenType.POPEN.toString() || tt == TokenType.PCLOSE.toString() ) {
                f = new Factor(t, FactorCat.HMOD.toString(), id);
            } else if(tt == TokenType.QOPEN.toString() || tt == TokenType.QCLOSE.toString() ) {
                f = new Factor(t, FactorCat.ATTMOD.toString(), id);
            } else {
                f = new Factor(t, FactorCat.ERR.toString(), id);
            }
            factors.add(f);
            id++;
        }
        this.factors = factors;        
        return true;
    }

    // Create Terms
    boolean createTerms() {
        List<Term> terms = new ArrayList<Term>();
        Integer id = 0;
        for (Factor f : this.factors) {
            Factor nl, nr;
            Term term;
            Integer cFId = f.getId(); // current factor Id
            if(f.cat == FactorCat.BINOP.toString()){ // if made for binary operators
                if(this.factors.get(cFId - 1) != null){ 
                    nl = this.factors.get(cFId - 1);  
                } else { 
                    nl = null;
                }
                if(this.factors.get(cFId + 1) != null){ 
                    nr = this.factors.get(cFId + 1);  
                } else { 
                    nr = null;
                }
                nl.setTermId(id);
                nr.setTermId(id);
                term = new Term(nl, f, nr, id);
                terms.add(term);
                id++;
            }
        }
        this.terms = terms;
        return true;
    }

    // Create Expressions
    boolean createExpressions() {
        return true;
    }

    // for debug 
    public String factorsToString(){
        String result = "null";
        if(this.factors != null) {
            result = "";
            for (Factor f : this.factors) {
                result += f.toString() + "\n";
            }
        }
        return result;
    }
    public String termsToString(){
        String result = "null";
        if(this.terms != null) {
            result = "";
            for (Term t : this.terms) {
                result += t.toString() + "\n";
            }
        }
        return result;
    }


}


