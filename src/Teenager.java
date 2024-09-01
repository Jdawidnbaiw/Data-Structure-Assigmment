
public class Teenager extends Person {
    private static final double PRIORITY_VALUE = 2.0;

    public Teenager(String name, double weight, int age) {
        super(name, weight, age, PRIORITY_VALUE);
    }

   
    public String getStatus() {
        return "Teenager";
    }
}