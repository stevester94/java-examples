import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        sets();
        lists();
        iterators();
        maps();
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

    
}

