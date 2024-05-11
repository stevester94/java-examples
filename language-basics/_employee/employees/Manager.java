package employees;

public class Manager extends Employee {
    public int bonus;
    
    public Manager( int salary, int bonus ) {
        super( salary );
        this.bonus = bonus;
    }
}
