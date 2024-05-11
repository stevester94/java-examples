/*
 * Pretty cool. There are restrictions to what you can reflect acrosss different modules though
 */


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

class A {
    public int val;
    A( int val ) {
        this.val = val;
    }

    public void thing() {
        System.out.println("Thing");
    }
}


public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> cl = Class.forName( "A" );

        for ( Method m : cl.getMethods() ) {
            var sb = new StringBuilder();
            sb.append( m.getName() );
            sb.append( "(" );
            for ( Parameter p : m.getParameters() ) {
                sb.append(p.getType());
                sb.append(" ");
                sb.append( p.getName() );
                sb.append( "," );
            }
            sb.append( ") -> " ) ;
            sb.append( m.getReturnType() );
            System.out.println( sb.toString() );
        }
    }    
}
