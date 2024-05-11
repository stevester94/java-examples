/*
 * - Type erasure can be non-trivial. Case in point, you can't instantiate arrays of a generic type.
 * - The way this works under the hood is that the compiler is checking that the types all make sense, it then generates byte code that uses the most restrictive type
 *   a template specifies (`bound` if specified, `unbound` if unspecified). 
 *   - If unbound, the compiler literally treats the classes as `Object`, if bound it'll treat it  as that bound type.
 *   - When you have a return type (as seen in MyArrayList), the compiler basically inserts a cast from Object to type T.
 *     - This is elucidated a bit by the fact that before generics, you had to create an ArrayList which held Object, and then manually cast all over the place
 */

import java.util.ArrayList;

class lel<T> {

    // You cannot do this. You do not know what type of lel you have at runtime, so you cannot specialize
    // public <String> void doIt() {
    // }
    // public <Integer> void doIt() {
    // }

    private T[] array; // this is null, and it is non-trivial to initialize it
    // You can't do this either. You cannot instantiate objects of generic type
    // lel() {
    //     array = new T[0];
    // }

    private ArrayList<T> arrayList = new ArrayList<>();

    public T doIt(T a) {
        if( a instanceof String ) {
            System.out.println("We got a string");
        } else if( a instanceof Integer ) {
            System.out.println("We got an integer");
        }

        return a;
    }

    public void add() {
        // It's null, can't do this
        // System.out.println( array.getClass().getName() );

        // The array list does not have a specialization type at runtime, it's just an ArrayList
        System.out.println( arrayList.getClass().getName() );
    }
}


class MyArrayList<T> {
    private Object[] ar = new Object[10];

    public void addOne( T e ) {
        ar[0] = e;
    }

    public T getOne() {
        return (T) ar[0];
    }
}


public class Main {
    public static void main(String[] args) {
        lel<Integer> i = new lel<>();
        Integer ret = i.doIt(1);
        System.out.println(ret.getClass().getName());

        lel<String> s = new lel<>();
        s.doIt("l");

        i.add();

        MyArrayList<Integer> mine = new MyArrayList<>();
        mine.addOne(10);
        mine.getOne();
    }
}
