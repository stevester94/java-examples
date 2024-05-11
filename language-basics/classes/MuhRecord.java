/*
 * Records give you the following methods for free:
 * - toString
 * - equals
 * - hashCode
 * 
 * You can also implement your own methods
 */
public record MuhRecord(
    double lel,
    String fug
) {
    public int mySpecialMethod() {
        return 1337;
    }
}
