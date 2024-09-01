
public class Disabled extends Person {
    private static final double PRIORITY_VALUE = 4.0;

    public Disabled(String name, double weight, int age) {
        super(name, weight, age, PRIORITY_VALUE);
    }


    public String getStatus() {
        return "Disabled";
    }
}