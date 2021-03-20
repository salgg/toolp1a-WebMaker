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
        System.out.println("-- test 001 --");
        for (Token t : list) {
            System.out.println(t.type + " : " + t.value);
        }
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
    INT("\\d+"), // integer
    STR("\'[^\']\'"), // anything inside ''
    COM("\\w+"), // command or special word
    ERR(":err:")
;
    private String regex;

    TokenType(String regex) 
    { 
        this.regex = regex;
    }

    String getRegex ()
    {
        return this.regex;
    }

}


// indivivual part on the language

class Token {

    String type;
    String value;
    String regex;
    
    public Token (String type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public String info() // token's representation
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
        
        for (String strToken : str_tokens) {
            for(TokenType tt: TokenType.values()){
                Pattern pattern = Pattern.compile(tt.getRegex(), Pattern.CASE_INSENSITIVE); // case sensitive 
                Matcher matcher = pattern.matcher(strToken);
                if(matcher.find()){
                    tokens.add(new Token(tt.name(), strToken));
                    break; // take te first match only 
                }
            }
        }
        return tokens;
    }
    public List<Token> tokenizer(){// tokenizer() with optional parameter
        return this.tokenizer(this.inputText);
    }

}


class Parser {
    
}


