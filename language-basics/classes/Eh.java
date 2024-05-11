/*
 * The no arg instructor gets defined automatically if no other c'tor is defined.
 * However, if another c'tor is defined, then you don't get that one automatically
 */

public class Eh {
    private Integer val = 1337;
    public Integer pub = 1337;
    public static String itsStatic;


    public Eh() {
        val = 0;
    }

    public Eh( Integer val ) {
        this.val = val;
    }

    // Static block is used to init static variables when the *CLASS* is loaded
    static {
        itsStatic = "Never gonna change";
    }
}