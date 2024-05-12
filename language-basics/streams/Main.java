import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main( String[] args ) throws IOException {
        String contents = Files.readString(Path.of("Alice.txt"), StandardCharsets.UTF_8);
        List<String> words = List.of(contents.split("\\PL+"));
        

        basic(words);
        parallel();
        map( words );
        filter( words );
    }

    /*
     * Parallel really does work, right out the box
     */
    static void parallel() {
        // You can make an infinite stream
        Stream<Integer> s = Stream.generate( () -> 1 ).limit( (long)1e9 ); // Limit clamps you to a number of elements from a stream
        long count_2 = s.parallel().filter( w -> w>0 )
        .count();
        System.out.println( count_2 );
    }

    static void basic(List<String> words) {
        long count = words.stream()
        .filter( w -> w.length()>12 )
        .count();
        System.out.println( count );
    }

    static void map( List<String> words ) {
        List<String> l_1 = words.parallelStream().map( w -> w.toLowerCase() ).limit(10).toList();
        System.out.println( l_1 );

        Function<String, String> f = (w) -> w.toUpperCase();
        words.parallelStream().map( f ).limit(10).forEach( (w) -> System.out.println(w)); // Actually very based
    }

    static void filter( List<String> words ) {
        Predicate<String> p = w -> w.length()>12;
        words.parallelStream().filter(p).forEachOrdered( w->System.out.println(w) );
    }
}
