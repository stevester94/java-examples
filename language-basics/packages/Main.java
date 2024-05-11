/*
 * - Packages do not nest. Ex: java.util and java.util.regex have nothing to do with each other.
 * - Import declarations are a convenience, not a necessity.
 * - Package naming must match their directory structure
 */


import myPackage.Another;

public class Main {
    public static void main( String[] args ) {
        System.out.println( "Running" );

        Another a; // a will be null

        // The compiler will not let you do this because a has provably never been initialized
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
