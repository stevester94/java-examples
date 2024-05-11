import java.util.*;;
public class Main {
    public static void main(String[] args) {
        // This relies on "Autoboxing"
        Integer i = 0;
        System.out.println(i);

        // This is using "unboxing"
        int lel = 1337 + i;
        System.out.println( lel );

        // Note how they can also be null
        i = null;
        System.out.println( i );

        // And of course they are used in generics
        List<Integer> muh = new ArrayList<Integer>();
        System.out.println( muh );
    }
    
}
