import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class A implements Runnable {
    String val;
    A( String val ) {
        this.val = val;
    }

    public void run() {
        for( int i = 0; i < 10; i++ ) {
            try {
                System.out.println( val + " " + i );
                java.lang.Thread.sleep( 100 );
            } catch( InterruptedException e ) {

            }
        }
    }
}

public class Main {
    public static void main( String[] args ) {
        runnable();
        futures();
    }

    
    /*
     * Runnables do not return values
     */
    static void runnable() {
        A first = new A( "first" );
        A second = new A( "second" );

        // Actually extremely based
        Runnable third = () -> {
            for( int i = 0; i < 10; i++ ) {
                System.out.println(i);
            }
        };

        ExecutorService exec = Executors.newFixedThreadPool( 3 );
        exec.execute( first );
        exec.execute( second );
        exec.execute( third );
        try {
            exec.shutdown(); // Waits for all tasks
            exec.awaitTermination(30, TimeUnit.SECONDS );
        } catch( InterruptedException e ) {
            System.out.println( e );
        }
        
    }
}
