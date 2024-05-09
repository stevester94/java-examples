import myPackage.Another;

import myPackage.Broken;

public class Main {
    public static void main( String[] args ) {
        System.out.println( "Running" );

        Another a; // a will be null

        // The compiler will not let you do this
        // if( a == null ) {
        //     System.out.println( "a is null");
        // }

        a = null;
        if( a == null ) {
            System.out.println( "a is still null");
        }

        try {
            a.publicMethod();
        } catch( Exception e ) {
            System.out.println( "You tried to use an unitialized reference" );
            System.out.println( e );
        }

        // You always have to use `new` when creating a new object
        a = new Another();

        a.publicMethod();
    }
}
