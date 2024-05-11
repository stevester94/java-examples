/*
 * It's a bit different than C++. The compiler checks if the template is valid for any type that T could be,
 * whereas C++ would only check how the templated method is actually used.
 */

import java.util.ArrayList;
import java.util.List;

class A {
    public int ret() { return 1337; }
}

class Employee {
    public int salary;
    
    public Employee( int salary ) {
        this.salary = salary;
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append( salary );
        return sb.toString();
    }
}

class Manager extends Employee {
    public int bonus;
    
    public Manager( int salary, int bonus ) {
        super( salary );
        this.bonus = bonus;
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append( salary );
        sb.append( "," );
        sb.append( bonus );
        return sb.toString();
    }
}



public class Main {
    public static <T> int genericMeth( T a ) {
        return a.hashCode();
    }

    public static <T> void hmm( T a ) {
        // This is not valid. The compiler actually does an exhaustive check to see if the template works for whatever type T could be
        // a.ret();
    }

    public static <T extends A> void bounded( T a ) {
        // This is valid. We've enforced that T must be A or a subclass of A
        System.out.println( a.ret() );
    }


    public static void main(String[] args) {
        A a = new A();

        System.out.println(genericMeth(a));
        a.ret();
        hmm( a );
        bounded( a );

        covariance();
    }


    /*
     * So Impatient book is kvetching about the fact that arrays can do some destructive things due to 
     * "type covariance"
     */
    private static void covariance() {
        Manager[] managers = new Manager[1];
        managers[0] = new Manager( 1, 100 );

        try {
            employeeAdder(managers);
        } catch( ArrayStoreException e ) {
            System.out.println( "This throws, because we're trying to put an employee into an array that is actually Manager[]" );
        }

        ArrayList<Manager> managers_list = new ArrayList<Manager>(List.of(managers));
        managers_list.add( new Manager(10, 4000) );
        // You cannot do this. The ArrayList is `invariant` in that ArrayList<Manager> is not a subclass of ArrayList<Employee>
        // employeeAdder(managers_list);

        subtypeWildCard( managers_list );
    }

    private static void employeeAdder( Employee[] e ) {
        e[0] = new Employee(1);
    }

    private static void employeeAdder( ArrayList<Employee> e ) {
        e.add( new Employee(1) );
    }

    private static void subtypeWildCard( ArrayList<? extends Employee> e ) {
        System.out.println( e.get(0) );

        // Both of these fail. It has something to do with what that the above wild card *actually* means
        // e.add( new Employee(1) );
        // e.add( new Manager(10, 4000) );
    }


}
