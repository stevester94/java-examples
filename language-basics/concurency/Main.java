/*
 * There is plenty more to be said here, but one important detail is that the JVM/Compiler
 * is aggressive with hiding variables between threads. You really do need to mark something as volatile if you want them to stay consistent across threads
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;
import java.lang.Thread;

class A implements Runnable {
    String val;
    A( String val ) {
        this.val = val;
    }

    public void run() {
        for( int i = 0; i < 10; i++ ) {
            try {
                System.out.println( val + " " + i );
                Thread.sleep( 100 );
            } catch( InterruptedException e ) {

            }
        }
    }
}

public class Main {
    public static void main( String[] args ) {
        runnable();
        futures();
        callbacks();
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

    static void futures() {
        ExecutorService exec = Executors.newFixedThreadPool( 3 );

        Callable<String> c = () -> {
            Thread.sleep(1000);
            return "lel";
        };

        Future<String> f = exec.submit( c );

        try {
            System.out.println( f.get() );
        } catch( InterruptedException e ) {
            System.out.println( e );
        } catch ( ExecutionException e ) {
            System.out.println( e );
        }


        /*
         * The exception is nested in "ExecutionException"
         */
        Callable<String> willThrow_callable = () -> {
            throw new RuntimeException( "DEAD" );
        };
        Future<String> willThrow_future = exec.submit( willThrow_callable );

        try {
            System.out.println( willThrow_future.get() );
        } catch( InterruptedException e ) {
            System.out.println( e );
        } catch ( ExecutionException e ) {
            System.out.println( e );
        }


        // Clean up the executor when all are done
        try {
            exec.shutdown(); // Waits for all tasks
            exec.awaitTermination(30, TimeUnit.SECONDS );
        } catch( InterruptedException e ) {
            System.out.println( e );
        }
    }

    static void callbacks() {


        // Suppliers cannot have checked exceptions
        Supplier<String> s = () -> {
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                System.out.println( e );
            }
            return "its done";
        };

        // This uses a default executor. You can also supply your own
        CompletableFuture<String> f = CompletableFuture.supplyAsync( s );
        try {
            // You can do them twice no problem
            System.out.println( f.get() );
            System.out.println( f.get() );
        } catch( InterruptedException e ) {
            System.out.println( e );
        } catch( ExecutionException e ) {
            System.out.println( e );
        }

        /*
         * These both execute so it's not like its just one task. It's a reusable call
         */
        f.whenComplete( (res, throwable) -> {
            if( throwable == null ) {
                System.out.println( "There was no throwable and the return was: " + res );
            } else {
                System.out.println( "There was a throwable and it was: " + throwable );
            }
        });

        f.whenComplete( (res, throwable) -> {
            if( throwable == null ) {
                System.out.println( "SECOND There was no throwable and the return was: " + res );
            } else {
                System.out.println( "SECOND There was a throwable and it was: " + throwable );
            }
        });
    }
}
