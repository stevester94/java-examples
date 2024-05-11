import java.util.List;
import java.util.ArrayList;

class MuhRandomSequence implements IntSequence {
    public boolean hasNext() {
        return true;
    }

    public int next() {
        return 1;
    }
}

class ListWrapper implements IntSequence {
    private int index = 0;
    private List<Integer> list;

    ListWrapper( List<Integer> list ) {
        this.list = list;
    }

    public boolean hasNext() {
        return index < list.size();
    }

    public int next() {
        var val = list.get(index);
        index++;
        return val;
    }
}

public class Main {
    public static void main(String[] args) {
        MuhRandomSequence muh = new MuhRandomSequence();

        ArrayList<Integer> l = new ArrayList<>(List.of(1,2));
        ListWrapper l2 = new ListWrapper(l);

        print10( muh );
        print10( l2 );
    }

    private static void print10( IntSequence seq ) {
        for( int i = 0; i < 5; i++ ) {
            if( seq.hasNext()) {
                System.out.println( seq.next() );
            } else {
                System.out.println("Exhausted");
                return;
            }
            
        }
    }
}
