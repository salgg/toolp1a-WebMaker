package CodeCakePack.CodeGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the class who take the command and "translate" to code (toDo NLP processor)
 *
 */

public class Waiter implements common{
    private String inputOrder = "";

    public Waiter(String inputOrder) {
        this.inputOrder = inputOrder;
    }

    public Waiter() {
    }

    // setters
    public void setinputOrder(String inputOrder) {
        this.inputOrder = inputOrder;
    }

    public void askOrder() throws IOException
    {
        System.out.println("Welcome! how can i help you?");
        System.out.println("pelase use only one space between words.");
        BufferedReader consolereader = new BufferedReader(new InputStreamReader(System.in));
        this.inputOrder = consolereader.readLine();
    }

    // most important method
    public List<String> makeOrder()
    {
        String order = this.inputOrder;
        String[] arrOrder = order.split(" ",0);
        List<String> cmmdList = Arrays.asList(common.posibleCommands);
        List<String> orderPass = new ArrayList<String>();
        Integer i = 0;
        for (String word : arrOrder) {
            if(cmmdList.contains(word)){
                orderPass.add(word);
            }
            i++;
        }
        System.out.println("Orders accepted: ");
        System.out.println(orderPass);
        return orderPass;
    }   

}
