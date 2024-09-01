
public class Adult extends Person {
    private static final double PRIORITY_VALUE = 1.0;

    public Adult(String name, double weight, int age) {
        super(name, weight, age, PRIORITY_VALUE);
    }


    public String getStatus() {
        return "Adult";
    }
}