import java.io.*;
import java.util.*;

public class BBMain {

    double maxWeight;
    PriorityQueue<Person> persons;

    public BBMain(double maxWeight, PriorityQueue<Person> persons) {
        this.maxWeight = maxWeight;
        this.persons = persons;
    }

    private double calculateTotalWeight(List<Person> passengers) {
        double totalWeight = 0;
        for (Person person : passengers) {
            totalWeight += person.getWeight();
        }
        return totalWeight;
    }

    private List<Person> branchAndBound(int index, List<Person> currentPassengers, List<Person> bestPassengers, double currentWeight, PriorityQueue<Person> remainingPersons) {
        if (index == persons.size() || remainingPersons.isEmpty()) {
            if (currentWeight <= maxWeight && currentPassengers.size() > bestPassengers.size()) {
                return new ArrayList<>(currentPassengers);
            } else {
                return bestPassengers;
            }
        }

        // Include the current person in the elevator
        Person currentPerson = remainingPersons.poll();
        currentPassengers.add(currentPerson);
        currentWeight += currentPerson.getWeight();

        if (currentWeight <= maxWeight) {
            bestPassengers = branchAndBound(index + 1, currentPassengers, bestPassengers, currentWeight, remainingPersons);
        }

        // Remove the current person from the elevator and explore without including them
        currentWeight -= currentPassengers.get(currentPassengers.size() - 1).getWeight();
        currentPassengers.remove(currentPassengers.size() - 1);

        bestPassengers = branchAndBound(index + 1, currentPassengers, bestPassengers, currentWeight, remainingPersons);

        // Restore the current person to the remainingPersons queue for further exploration
        remainingPersons.add(currentPerson);

        return bestPassengers;
    }

    public List<Person> solve() {
        List<Person> bestPassengers = new ArrayList<>();
        List<Person> currentPassengers = new ArrayList<>();
        double currentWeight = 0;

        // Make a copy of the priority queue
        PriorityQueue<Person> remainingPersons = new PriorityQueue<>(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                // Prioritize Disable, then Elder, then Teenager, then Adult
                if (p1 instanceof Disabled && !(p2 instanceof Disabled)) {
                    return -1;
                } else if (p1 instanceof Elder && !(p2 instanceof Disabled || p2 instanceof Elder)) {
                    return -1;
                } else if (p1 instanceof Teenager && !(p2 instanceof Disabled || p2 instanceof Elder || p2 instanceof Teenager)) {
                    return -1;
                } else if (!(p1 instanceof Adult)) {
                    return -1;
                }
                return 1;
            }
        });
        
        remainingPersons.addAll(persons);

        bestPassengers = branchAndBound(0, currentPassengers, bestPassengers, currentWeight, remainingPersons);

        return bestPassengers;
    }

    
}
