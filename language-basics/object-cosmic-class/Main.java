/*
 * The Object class is the super for all classes
 * - Hashcode and equals must be compatible; IE hashcodes will be == if .equals is true
 */


class A implements Cloneable {
    private int val = 3;


    /*
     * This is the boilerplate for shallow copying
     * This rigamrole is here basically to support further inheritance.
     * We enumerator the CloneNotSupportedException because subclasses may not support it, and
     * we must indicate that as the super this is possible
     */
    public A clone() throws CloneNotSupportedException {
        return (A) super.clone();
    }
}

class B extends A {
}

public class Main {
    public static void main(String[] args) {
        A a_1 = new A();
        A a_2 = new A();
        B b_1 = new B();

        // None of these are true:
        // == tests memory addresses for objects, and value for primitives.
        // Out of the box, equals() does the same thing.
        // It only gets interesting if you override equals
        System.out.println( a_1 == b_1 );
        System.out.println( a_1.equals(b_1) );
        System.out.println( a_1.equals(a_2) );

        // TODO more on equality, there are some pitfalls here
        
        // We get a magically generated VM hashcode (probably based on address). The below are different
        System.out.println( a_1.hashCode() );
        System.out.println( a_2.hashCode() );

        // TODO clone
        A a_3;
        try {
            a_3 = a_1.clone();
        } catch( CloneNotSupportedException e ) {
            System.err.println( "This won't get triggered" );
        }
        

        if( a_1 instanceof Object ) {
            System.out.println( "All objects are subclasses of Object" );
        }



    }
}
