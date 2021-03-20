package CodeCakePack.CodeGenerator.recipies.webCake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import CodeCakePack.CodeGenerator.library.Utils;

/**
 * Web Code Maker
 *
 */


public class HTML {

    List<String> nonOptionalAttrib = new ArrayList<String>();

    public HTML() {
        this.nonOptionalAttrib = Arrays.asList("title", "head", "inside");
    }

    //public String cook(String title, String head, String body) {
    public String cook(Map<String, String> attrib) 
    {
        String[][] pref = { { "lang", "en" } };
        /* code testing */
        // String input = Input.cook("", 1);
        // String form = Form.cook(input,2);//here toDo
        // body = body + form;
        /* end code testing */
        if(attribValidator(attrib) == false){
            return "<error code=001 where=HTML>";
        }
        String inside = "\t<head>\n\t\t<meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n\t\t<title>"
                + attrib.get("title") + "</title>" + attrib.get("head") + "\n\t</head>\n\t<body>\n" + attrib.get("inside") + "\n\t</body>";
        Tag html = new Tag.MoldBuilder(true, "html").inside(inside).pref(pref).tabLvl(0).build();
        return "<!DOCTYPE html>\n" + html.toString();
    }

    // return true if exist all posible attributes on lists
    Boolean attribValidator(Map<String, String> attrib)
    {
        for(String att: this.nonOptionalAttrib)
        {
            if(attrib.containsKey(att) == false){
                return false;
            }
        }
        return true; 
    }

}

// <form>Inside</form>

class Form extends HTML {

    public Form()
    {
        this.nonOptionalAttrib = Arrays.asList("inside", "tabLvl");
    }    

    public String cook(Map<String, String> attrib)
    {   
        // temp variables
        String inside = "";
        Integer tabLvl = 0;
        String[][] pref = Utils.Map22DArr(attrib); // optimize 

        Tag form = new Tag.MoldBuilder(true, "form").inside(inside).pref(pref).tabLvl(tabLvl).build();
        return form.toString();
    }

    void validator(){
        
    }

}





