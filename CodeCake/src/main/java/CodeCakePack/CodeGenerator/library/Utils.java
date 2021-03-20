package CodeCakePack.CodeGenerator.library;

import java.util.List;
import java.util.Map;


/**
 * Utilities 
 *
 */

public class Utils {
    
    public static Boolean strIsIn(String inputString, List<String> commandList)
    {   
        for (String tag : commandList){
            if(tag.equals(inputString)){
                return true;
            }
        }
        return false;
    }

    // useful when we need convert the Attributes map to preferences String[][] Tag
    public static String[][] Map22DArr(Map<String, String> map)
    {
        String[][] pref = new String[map.size()][2];
        Integer i = 0;

        for(Map.Entry<String,String> entry : map.entrySet())
        {
            pref[i] =  new String[] {entry.getKey(), entry.getValue()};
            i++;
        }
        return pref;
    }
    
}
