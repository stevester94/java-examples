public class Main {
    public static void main( String[] args ) {
        Eh e = new Eh();
        System.out.println( e ); // Just an address

        System.out.println( e.itsStatic );

        MuhRecord muh = new MuhRecord( 1.337, "hell" );
        System.out.println( muh );
        System.out.println( muh.lel() );

        finalIsALie( e );


    }

    // The real point of final in arguments is so that subclasses can't hide them/override them
    private static void finalIsALie( final Eh e ) {
        e.pub = 141234; // This works

        // e = new Eh(); // This isn't allowed
    }
}
