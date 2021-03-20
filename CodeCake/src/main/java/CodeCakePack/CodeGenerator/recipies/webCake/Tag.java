package CodeCakePack.CodeGenerator.recipies.webCake;

/**
 * Web Code Maker
 *
 */
public class Tag {
    private final Boolean needClose;
    private final String name;
    private final String[][] pref; //preferences
    private final String inside;
    private final Integer tabLvl;

    private Tag(MoldBuilder builder)
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
        String att = ""; // atribute name <Tag att="val"/>
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

        public Tag build()
        {
            Tag mold = new Tag(this);
            validateTagObject(mold);
            return mold;
        }

        private void validateTagObject(Tag mold)
        {
            //toDo
            //Do some basic validations to check if user object does not break any assumption of system
            // check if this.pref has blank att with non blank val ej:<Tag ""="value"/> <-- error!!
            // check if this.name and this.needClose  there's no null or empty types
            //if(this.inside == null ){ this.inside = ""; }
        }
        
    }
    
}