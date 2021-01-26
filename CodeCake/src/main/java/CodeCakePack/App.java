package CodeCakePack;

import java.io.IOException;

import CodeCakePack.CodeGenerator.Chef;

/**
 * Main Class (App)
 *
 */
public class App {
    public static void main(String[] args) throws IOException
    {   
        Chef maker = new Chef();
        maker.serve(); // throws IOException
        //System.out.println( "Hello World!" );
    }
}
