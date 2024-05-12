import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // sets();
        // lists();
        // iterators();
        // maps();
        initialization();
    }

    /*
     * Set is the interface, HashSet is a specific type
     * - HashSet: Your basic hash set
     * - Enum set: High performance, must come from a single enumeration
     * - LinkedHashSet: Maintains order
     * - TreeSet: Uses a tree, and maintains order based on a comparator
     */
    static void sets() {
        Set<Integer> s = new HashSet<Integer>();
        s.add(1);
        s.add(2);
        s.add(1);

        System.out.println( s );

        s.contains(1); // true
        s.remove(1);
        s.contains(1); // false
    }

    /*
     * It is best practice to use the least restrictive type possible for vars and args. IE
     * use a list instead of an ArrayList
     */
    static void lists() {
        List<Integer> l = new ArrayList<Integer>();

        l.add(1);
        l.add(2);

        Collections.addAll(l, 1,2,3 );
    }

    static void iterators() {
        List<Integer> l = new ArrayList<Integer>();
        Collections.addAll(l, 1,2,3 );

        Iterator<Integer> it = l.iterator();
        while( it.hasNext() ) {
            System.out.println( it.next() );
        }

        // You can also use enhanced for loops
        for( Integer el : l ) {
            System.out.println( el );
        }
    }

    static void maps() {
        Map<String, Integer> m = new HashMap<String, Integer>();

        m.put( "lel", 1337 );

        System.out.println( m.get("lel") );
        System.out.println(m.get( "1234" )); // null but doesn't crash
        System.out.println( m.getOrDefault("1235", 2) );
        System.out.println( m.getOrDefault(m, 2) ); // OK So this is a little weird, the key is just of type Object

        // TODO: There's a merge method that makes this more elegant. Look into best/common practices
        m.put("lel", m.getOrDefault("lel", 0) + 1 );
    }

    /*
     * - List.of(<>,<>,...) is a varargs method and expects the actual elements
     * - Arrays.asList does indeed take an array, but must be an array of the wrapper type, not primitive
     *   - It does not make a copy
     * - You can use streams to convert primitive arrays, but these are making copies
     *   - Only primitives int, long, float have streams
     *   - For the rest you either must use a workaround to get a stream (such as ByteBuffer) or just use a for loop
     * 
     * - I think the crux really is that generics in java do not support primitive types
     */
    static void initialization() {
        byte[] b = {1,2,4};
        int[] i = {1,2,4};

        Byte[] w_b = {1,2,3};
        Integer[] w_i = {1,2,3};


        /*
         * List.of cannot take args like this. They are actuall vararg methods which expect actual elements
         */
        // List<Byte> bytes = List.of( b );
        // List<Integer> bytes = List.of( i );

        /*
         * Arrays.asList expects wrapper classes, not primitives
         */
        // List<Byte> bytes = Arrays.asList( b );
        // List<Integer> ints = Arrays.asList( i );

        /*
         * These both work because the arrays are of the wrapper classes
         */
        List<Byte> bytes = Arrays.asList( w_b );
        List<Integer> ints = Arrays.asList( w_i );

        /*
         * So, if you want a list of the wrapper type from a primitive, you have to manually create them
         * 
         * Streams are the best way I've found
         */

        // For integers
        {
            ArrayList<Integer> al = new ArrayList<>(i.length);
            Arrays.stream( i ).boxed().forEach( in -> al.add(in) );
            System.out.println( al );
        }
        // You can also use a collector
        {
            List<Integer> list = Arrays.stream( i ).boxed().collect( Collectors.toList() );
            ArrayList<Integer> al = new ArrayList<>(list);
            System.out.println( al );
        }

        /*
         * For whatever fucking reason, there is a stream only for int, long, double. Not byte!
         */
        {
            // Can't do this!
            // Arrays.stream( b );

            // So either you just manually build a list/array, or use this horrible byte buffer stream thing
            ByteBuffer bb = ByteBuffer.wrap( b );
            List<Byte> l = Stream.generate( bb::get )
                .limit(bb.remaining()) // Not entirely sure why the remaining criterion is needed
                .collect( Collectors.toList() );
        }

        // The manual way
        {
            List<Byte> l = new ArrayList<Byte>(b.length);
            for( byte _b : b )
                l.add(_b);
        }


    }
}