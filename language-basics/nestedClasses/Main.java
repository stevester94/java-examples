public class Main {
    public static void main(String[] args) {
        Nested n = new Nested();

        Holder h = new Holder();

        Holder.Inner i1 = h.getAnInner();
        Holder.Inner i2 = h.getAnInner();

        i1.increment();
        i2.increment();

        System.out.println( h.getCounter() );

        // Inner classes must be constructed by the outer class (can't be naked!)
        // Holder.Inner noOuter = new Holder.Inner();

    }
    
    // This could be made private as well
    // There is nothing too itneresting about this. Same exact mechanism as C++ and Python
    public static class Nested {
        private int val = 1337;
    }
}


/*
 * Yeah, inner classes are kinda weird. The inner class "remembers" the class that built it, so that the inners can access the outers members (even if private)
 */
class Holder {
    private int counter = 0;

    public int getCounter()  {
        return counter;
    }

    public Inner getAnInner() {
        return new Inner();
    }

    public class Inner {
        public void increment() {
            counter += 1;
        }
    }
}