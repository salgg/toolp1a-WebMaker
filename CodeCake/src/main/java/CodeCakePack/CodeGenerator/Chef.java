package CodeCakePack.CodeGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import CodeCakePack.CodeGenerator.recepies.webCake.HTML;

/**
 * Web Code Maker
 *
 */

public class Chef implements common{
    private String outputFile;
    private List<String> orders;

    public Chef() {
        this.outputFile = "WForm_Output.html";
    }

    public Chef(String outputFile) {
        this.outputFile = outputFile;
    }

    public void serve() throws IOException {
        
        Waiter waiter = new Waiter();
        waiter.askOrder(); // throws IOException
        this.orders = waiter.makeOrder();
        String code = cook();

        try {
            FileWriter myWriter = new FileWriter(this.outputFile);
            myWriter.write(code);
            myWriter.close();
            System.out.println("Successfully wrote to the file " + this.outputFile);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          } 
    }

    private String cook()
    {   
        String code = "";
        for (String order : this.orders) {
            System.out.println("order=" + order+".");
            if(order.equals("html")){ 
                code = code + HTML.cook("title", "", "");
            }
        }
        return code;
    }

}

// comon interface
interface common {
    final String[] posibleCommands = { "html", "form", "input" };
}
