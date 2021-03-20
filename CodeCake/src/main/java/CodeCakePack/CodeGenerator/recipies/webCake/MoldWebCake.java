package CodeCakePack.CodeGenerator.recipies.webCake;

import java.util.HashMap;
import java.util.Map;

public class MoldWebCake {
    public Map<String, HTML> list = new HashMap<String, HTML>();

    public MoldWebCake()
    {
        this.list = prepare();
    }

    public Map<String, HTML> prepare()
    {
        list.put("HTML", new HTML());
        list.put("FORM", new Form());
        return list;
    }

    public String cook(String KeyName, Map<String, String> attrib)
    {
        HTML tag = this.list.get(KeyName);
        return tag.cook(attrib);
    }
}
