import java.util.Comparator;
import java.util.Arrays;
import java.util.function.Function;

@FunctionalInterface
interface MyOwnLambda {
    void doIt( int A, String B );
}

public class Main {
    public static void main(String[] args) {
        // The built in function lambda interface must hav ea return value
        Function<String,String> muhLambda = (String s) -> s+1;
        System.out.println( muhLambda.apply("MuhArg") ); // No operator overloading :)

        // But you can easily define your own
        MyOwnLambda mine = (a,b) -> System.out.println( b+a );
        mine.doIt( 1337, "Bet you wont" );

        giveMeLambda( mine );




        // I don't fully understand how the Comparator is defined and how it can accept a lambda
        Comparator<String> comp = (first, second) -> first.length() - second.length();
        String[] ar = {"AAA", "A", "AA"};
        Arrays.sort( ar, comp );
        System.out.println( Arrays.asList(ar) );
    }

    static void giveMeLambda( MyOwnLambda l ) {
        l.doIt( 42, "Feed me lambdas" );
    }
    
}
