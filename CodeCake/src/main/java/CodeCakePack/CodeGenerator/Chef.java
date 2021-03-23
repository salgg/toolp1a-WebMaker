package CodeCakePack.CodeGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CodeCakePack.CodeGenerator.library.Utils;
import CodeCakePack.CodeGenerator.library.LangChef;
import CodeCakePack.CodeGenerator.recipies.webCake.MoldWebCake;

/**
 * Web Code Maker
 *
 */

public class Chef implements commonLanguage {
    private String outputFile;
    private String[] ordersList;
    private MoldWebCake moldHTML ;

    public Chef() {
        this.outputFile = "WForm_Output.html";
        this.moldHTML = new MoldWebCake(); // initialize all posible commands 
    }

    public Chef(String outputFile) {
        this.outputFile = outputFile;
    }

    public void serve() throws IOException {

        Waiter waiter = new Waiter();
        waiter.askOrder(); // throws IOException
        String orders = waiter.inputOrder; // List<String> -> waiter.makeOrder();
        this.ordersList = orders.split(" ");
        String code = cook();

        // @test 
        LangChef.test001(orders);

        try {
            FileWriter myWriter = new FileWriter(this.outputFile);
            myWriter.write(code);
            myWriter.close();
            System.out.println("Successfully wrote to the file " + this.outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred. (ChefServe)");
            e.printStackTrace();
        }
    }

    String cook() {
        String code = "";
        List<String> commandList = Arrays.asList(commonLanguage.webCakeRelatedTag);

        //temp attributes for tag (tempArr and puts)
        Map<String, String> tempAttr = new HashMap<String, String>();
        tempAttr.put("title", "title");
        tempAttr.put("head", "head");
        tempAttr.put("inside", "body");

        for (String order : this.ordersList) {
            //System.out.println("order=" + order + "."); //temp debug

            if (Utils.strIsIn(order, commandList)) {
                String cooked = this.moldHTML.list.get(order).cook(tempAttr);
                code = code + cooked;
            }
        }
        return code;
    }

}

// comon interface
interface commonLanguage {
    final String[] conectors = {"+", "-", "(", ")", ":", "'"}; // here next step 
    final String[] webCakeRelatedTag = {"HTML", "FORM" };
}
