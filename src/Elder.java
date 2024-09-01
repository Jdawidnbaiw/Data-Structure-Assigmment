
public class Elder extends Person {
    private static final double PRIORITY_VALUE = 3.0;

    public Elder(String name, double weight, int age) {
        super(name, weight, age, PRIORITY_VALUE);
    }

 
    public String getStatus() {
        return "Elder";
    }
}