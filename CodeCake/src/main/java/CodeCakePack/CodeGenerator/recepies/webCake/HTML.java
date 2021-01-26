package CodeCakePack.CodeGenerator.recepies.webCake;

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
        //String input = Input.cook("", 1);
        //String form = Form.cook(input,2);//here toDo
        //body = body + form;
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






