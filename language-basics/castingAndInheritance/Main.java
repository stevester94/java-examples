abstract class A {
    protected void superMethod() {
        System.out.println("A super method");
    }

    public abstract void doIt();
}

class B extends A {
    public void doIt() {
        System.out.println("B doing it");
        superMethod();
    }

    public void uniqueToB() {
        System.out.println("B's unique method");
    }
}

class C extends A {
    public void doIt() {
        System.out.println("C doing it");
    }
}

public class Main {
    public static void main(String[] args) {
        A actuallyB = new B();
        actuallyB.doIt();

        A actuallyC = new C();
        actuallyC.doIt();

        // Can't do it directly, must explicitly cast
        // B isB = actuallyB;
        B isB = (B) actuallyB;
        isB.doIt();

        // This throws the following exception (Interesting that the compiler doesn't catch this)
        try {
            C isC = (C) actuallyB;
            isC.doIt();
        } catch( java.lang.ClassCastException E ) {
            System.out.println( "Caught an exception" );
            System.out.println(E);
        }

        // We can do it safely using instanceof
        if (actuallyB instanceof B) {
            System.out.println("actuallyB is an instance of B");
        }

        // There's a fancy syntactic sugar that is basically declaring a locally scoped B reference for us to use in the if statement
        // This is called pattern matching
        if (actuallyB instanceof B asB) {
            asB.uniqueToB();
        }
    }

}
