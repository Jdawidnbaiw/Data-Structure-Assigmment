
public class Person implements Comparable<Person> {
    private String name;
    private double weight;
    private int age;
    private double priority;

    public Person(String name, double weight, int age, double priority) {
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Person other) {
    	if(Double.compare(this.priority, other.priority) < 0)
    	{
    		return 1;
    	}
    	else if(Double.compare(this.priority, other.priority) > 0)
    	{
    		return -1;
    	}
    	else
    	{
    		if(Double.compare(this.weight, other.getWeight()) < 0)
    		{
    			return -1;
    		}
    		else if(Double.compare(this.weight, other.getWeight()) > 0)
    		{
    			return 1;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	
    }

    @Override
    public String toString() {
    	
        return "Name: " + name + " Priority: " + priority + " Weight: " + weight;
    }
}
