package CodeCakePack.Maker;
import java.io.FileWriter;
import java.io.IOException;

public class Maker {
    private String outputFile;

    public Maker() {
        this.outputFile = "WForm_Output.html";
    }

    public Maker(String outputFile) {
        this.outputFile = outputFile;
    }

    public void serve() {
        String code = HTML.cook("title","","");
        try {
            FileWriter myWriter = new FileWriter(outputFile);
            myWriter.write(code);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          } 
    }

}
