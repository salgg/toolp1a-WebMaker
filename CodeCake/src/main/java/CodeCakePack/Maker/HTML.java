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
        /* code testing */
        String input = Input.cook("", 1);
        String form = Form.cook(input,2);//here toDo
        body = body + form;
        /* end code testing */
        String inside ="\t<head>\n\t\t<meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n\t\t<title>"+title+"</title>"+head+"\n\t</head>\n\t<body>\n"+body+"\n\t</body>";
        Label html = new Label.MoldBuilder(true, "html").inside(inside).pref(pref).tabLvl(0).build();
        return "<!DOCTYPE html>\n" + html.toString();
    }

}

// FORM classes

class Form {

    public static String cook(String inside, Integer tabLvl)
    {   
        String[] id = {"id","id"};
        String[] class_ = {"class",""};
        String[] style = {"style",""};
        String[][] pref = {
            {"action",""},
            {"method","get"},
            id, class_, style
        };
        
        Label form = new Label.MoldBuilder(true, "form").inside(inside).pref(pref).tabLvl(tabLvl).build();
        return form.toString();
    }
}

class Input {

    public static String cook(String inside, Integer tabLvl)
    {   
        String[] id = {"id","id"};
        String[] class_ = {"class",""};
        String[] style = {"style",""};
        String[][] pref = {
            {"type","text"},
            id, class_, style
        };
        
        Label form = new Label.MoldBuilder(false, "input").pref(pref).tabLvl(tabLvl).build();
        return form.toString();
    }
}


// Label *Most important Class*
class Label {
    private final Boolean needClose;
    private final String name;
    private final String[][] pref; //preferences
    private final String inside;
    private final Integer tabLvl;

    private Label(MoldBuilder builder)
    {
        this.needClose = builder.needClose;
        this.name = builder.name;
        this.pref = builder.pref;
        this.inside = builder.inside;
        this.tabLvl = builder.tabLvl;
    }

    public Boolean getNeedClose(){ return needClose; }
    public String getName(){ return name; }
    public String[][] getPref(){ return pref; }
    public String getInside(){ return inside; }
    public Integer getTabLvl() { return tabLvl; }

    // Main method
    @Override 
    public String toString()
    { 
        String code = "<";
        String name = this.name;
        String pref = cookPref();
        String tabLvl = cookTabLvl();
        String inside = ((this.inside == null)? "": this.inside);
        if(this.needClose)
        { 
            code = tabLvl + code + name + pref + ">\n" + tabLvl +  inside + "\n" + tabLvl + "</" + name + ">";
        } else {
            code = tabLvl + code + name + pref + "/>";
        }
        
        return code;
    }

    private String cookTabLvl()
    {
        if(this.tabLvl == null || this.tabLvl <= 0){ return ""; }
        String code = "";
        for (int i = this.tabLvl; i >= 1 ; i--) {
            code = code + "\t";
        }

        return code;
    }

    private String cookPref()
    {
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
        private Integer tabLvl;

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

        public MoldBuilder tabLvl(Integer tabLvl)
        {
            this.tabLvl = tabLvl;
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




