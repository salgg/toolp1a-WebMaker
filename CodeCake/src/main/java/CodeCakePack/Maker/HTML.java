package CodeCakePack.Maker;

/**
 * Web Code Maker
 *
 */

public class HTML {
    
    public static String cook(String title, String head, String body)
    {   
        String[][] pref = {
            {"lang","en"}
        };
        String form = Form.cook("");
        body = body + form + "hello wordl!";
        String inside ="<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>"+title+"</title>"+head+"</head><body>"+body+"</body>";
        Label html = new Label.MoldBuilder(true, "html").inside(inside).pref(pref).build();
        return "<!DOCTYPE html>" + html.toString();
    }

}

// FORM classes

class Form {

    public static String cook(String inside)
    {   
        String[] id = {"id","id"};
        String[] class_ = {"class",""};
        String[] style = {"style",""};
        String[][] pref = {
            {"action",""},
            {"method","get"},
            id, class_, style
        };
        
        Label form = new Label.MoldBuilder(true, "form").inside(inside).pref(pref).build();
        return form.toString();
    }
}


class Label {
    private final Boolean needClose;
    private final String name;
    private final String[][] pref; //preferences
    private final String inside;

    private Label(MoldBuilder builder)
    {
        this.needClose = builder.needClose;
        this.name = builder.name;
        this.pref = builder.pref;
        this.inside = builder.inside;
    }

    public Boolean getNeedClose(){ return needClose; }
    public String getName(){ return name; }
    public String[][] getPref(){ return pref; }
    public String getInside(){ return inside; }

    // Main method
    @Override // because java already has toString method
    public String toString()
    { 
        String code = "<";
        String name = this.name;
        String pref = cookPref();
        String inside = ((this.inside == null)? "": this.inside);
        if(this.needClose)
        { 
            code = code + name + pref + ">" + inside + "</" + name + ">";
        } else {
            code = code + name + pref + "/>";
        }
        
        return code;
    }

    private String cookPref(){
        String code = "";
        String att = ""; // atribute name <label att="val"/>
        String val = "";
        String[][] preferences = this.pref;

        if(preferences == null || preferences.length == 0){ 
            return "";
        }

        for (int i = 0; i < preferences.length; i++) {
            att = preferences[i][0];
            val = preferences[i][1];
            if(val == "" || val == null) { val = ""; }
            code = code + " " + att + "=\"" + val + "\"";
        }
        
        return code;
    }

    // builder patter
    public static class MoldBuilder {
        private final Boolean needClose;
        private final String name;
        private String[][] pref;
        private String inside;

        // constructors
        public MoldBuilder(Boolean needClose, String name)
        {
            this.needClose = needClose;
            this.name = name;
        }

        public MoldBuilder(String name)
        {
            this.needClose = true;
            this.name = name;
        }

        // builders
        public MoldBuilder pref(String[][] pref)
        {
            this.pref = pref;
            return this;
        }

        public MoldBuilder inside(String inside)
        {
            this.inside = inside;
            return this;
        }

        public Label build()
        {
            Label mold = new Label(this);
            validateLabelObject(mold);
            return mold;
        }

        private void validateLabelObject(Label mold)
        {
            //toDo
            //Do some basic validations to check if user object does not break any assumption of system
            // check if this.pref has blank att with non blank val ej:<label ""="value"/> <-- error!!
            // check if this.name and this.needClose  there's no null or empty types
            //if(this.inside == null ){ this.inside = ""; }
        }
        
    }
    
}




