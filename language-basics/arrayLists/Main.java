import java.util.ArrayList; // Can only contain objects. That's why you have to use the wrapper classes such as `Integer`
import java.util.Arrays; // Various methods for interacting with Arrays
import java.util.List; // Is an interface (and includes static methods)

public class Main {
    /**
     * @param args
     */
    public static void main( String[] args ) {
        ArrayList<String> list = new ArrayList<>();
        list.add( "A string" );
        System.out.println( list );

        List<Integer> list2 = Arrays.asList( 1337 );
        System.out.println( list2 );
        System.out.println( list2.getClass().getName() ); // Says its an array list

        // ArrayList is a child class of List, so you can do this
        List<String> list3;
        list3 = list;
        System.out.println( list3 );

        
        var friends = new ArrayList<>(List.of("Peter", "Paul"));
        System.out.println( friends );

        ArrayList<Integer> ar = new ArrayList<>(List.of( 1,2,3 ));

        // Fancy iteration
        for( var i : ar ) {
            System.out.println( i );
        }

        initialization();
    }

    private static void initialization() {
        // Can't do this
        // List<Integer> = { 1,2,3 };

        int[] ar = { 1,2,3 };

        // Interestingly you can't do this
        // Integer[] ar2 = ar;

        // You can do this though
        Integer[] ar2 = {1,2,3};
        System.out.println( ar2 ); // OK Here we go. When you print this, you basically get a memory address thing. It's the array list that gives you pretty printing

        // You can't do this
        // List<Integer> ar3 = ar2;

        // But you can do this
        List<Integer> ar3 = List.of(ar2);
        System.out.println( ar3 ); // And it gives you the pretty printing


    }
}

