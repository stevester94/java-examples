public class Main {
    public static void main( String[] args ) {
        String a = "";
        System.out.println( a );

        // Strings are immutable in Java, so this is creating a new string
        a += "lel";
        System.out.println( a );

        // This is not valid since strings immutable
        // a[0] = 'l';

        // StringBuilder is the right way to go about doing repeated string ops, because it holds an internal buffer for the chars
        // which is mutable
        StringBuilder sb = new StringBuilder(a);
        sb.append( "Another" );
        sb[0] = 'j';
        System.out.println( sb.toString() );

        

    }
}
