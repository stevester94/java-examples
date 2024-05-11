/*
 * - Enums must be public if declared at the top scope, but in a class they can be whatever
 * - They automatically get a toString method
 * - They automatically get a valueOf method (IE from string)
 * - Enums can have methods and members. They can get complicated
 */


// This class cannot be instantiated because of the private ctor
class A {
    public static enum cant_see_me {hehehe, lel};

    // You can get real damn complicated with enums
    public static enum Complicated { 
        ONE("jej"), TWO("kek");
        public String payload;
        Complicated( String s ) {
            this.payload = s;
        }
    }

    private A() {}
}

public class Main {
    public static void main( String[] args ) {
        A.cant_see_me e = A.cant_see_me.hehehe;

        System.out.println(e);
        
        var e_2 = A.cant_see_me.valueOf("lel");

        A.Complicated c_1 = A.Complicated.TWO;
        System.out.println( c_1.payload );


        switch (c_1) {
            case A.Complicated.ONE -> System.out.println( "This is a one" );
            case A.Complicated.TWO -> System.out.println( "This is a two" );
        }
    }
}
