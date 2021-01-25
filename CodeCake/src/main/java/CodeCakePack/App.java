package CodeCakePack;

import CodeCakePack.Maker.Maker;

/**
 * Main Class (App)
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
        Maker maker = new Maker();
        maker.serve();
        //System.out.println( "Hello World!" );
    }
}
