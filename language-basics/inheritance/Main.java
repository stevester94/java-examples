/*
 * - You can only go one up the inheritance hierarchy with `super`; You cannot go higher than that
 * - You can override member vars no problem
 * - Constructors are not inherited. You must invoke a parent's constructor explicitly w/ super( <ctor args> ) if it does not have a no arg ctor
 * - final methods cannot be overriden, BUT if you change the args they can
 * - final classes cannot be extended
 * - In Java, protected grants package-level access, and it only protects access from other packages.
 * - Sealed classes allow you to REQUIRE which classes subclass yours, and disallow any others
 */

class A {
    protected int val = 0;
    A( int val ) {
        this.val = val;
    }

    public void A_method() {
        System.out.println( "A_method" );
    }

    final public void final_method() {
        System.out.println("Can never be overriden" );
    }
}

class B extends A {
    B() {
        super( 0 );
    }
    public void A_method() {
        System.out.println( "B_method" );
    }
}

sealed class C extends B permits D {
    protected int val = 1;
    public void A_method() {
        System.out.println( "C_method, but then we call B's A_method" );
        super.A_method();
    }

    public void final_method( int a ) {
        System.out.println( "We can override this because the arg changed" );
    }
}

// We allow this to be subclassed via the non-sealed (jesus)
non-sealed class D extends C {

}

// This is allowed
class E extends D {

}

public class Main {
    public static void main(String[] args) {
        C c = new C();

        c.A_method();
    }
}
