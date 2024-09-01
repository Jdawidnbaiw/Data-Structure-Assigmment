
import java.util.*;
import java.io.*;

public class KnapsackSolution {
	public static void main(String[] args) throws MissingStatus {
		PriorityQueue<Person> personQueue = new PriorityQueue<>();

		try {
			File infile = new File("PersonList");
			Scanner input = new Scanner(infile);

			while (input.hasNextLine()) {
				String line = input.nextLine();
				String[] parts = line.split(",");
				String name = parts[0];
				double weight = Double.parseDouble(parts[1]);
				int age = Integer.parseInt(parts[2]);
				String status = parts[3];

				switch (status) {
				case "d":
				case "D":
					personQueue.offer(new Disabled(name, weight, age));
					break;
				case "e":
				case "E":
					personQueue.offer(new Elder(name, weight, age));
					break;
				case "t":
				case "T":
					personQueue.offer(new Teenager(name, weight, age));
					break;
				case "a":
				case "A":
					personQueue.offer(new Adult(name, weight, age));
					break;
				default:
					throw new MissingStatus();
				}
			}

			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		BBMain elevator = new BBMain(500.0, personQueue);
        List<Person> bestPassengers = elevator.solve();

        double total_weight_for_branch_and_bound = 0.0;
        System.out.println("People in an elevator after a branch and bound solve");
        for (Person person : bestPassengers) {
            System.out.println("Name: " + person.getName() + ", Age: " + person.getAge() + ", Weight: " + person.getWeight());
            total_weight_for_branch_and_bound += person.getWeight();
            
        }
        System.out.println("Total Weight: " + total_weight_for_branch_and_bound);
        
        
    	System.out.println();
    	
    	double total_weight_for_brute_force = 0.0;
        System.out.println("People in an elevator after a brute force solve");
		Iterator<Person> i = BruteForceSolve(personQueue, 500.0).iterator();
		while(i.hasNext())
		{
			Person p = i.next();
			total_weight_for_brute_force += p.getWeight();
			System.out.println(p);
		}
		
		System.out.println("Total Weight: " + total_weight_for_brute_force);
	}

	public static PriorityQueue<Person> BruteForceSolve(PriorityQueue<Person> P_Queue, double ElevatorWeightCapacity) {

		List<List<Person>> combinations = getAllCombinations(P_Queue, 1);
		List<List<Person>> combinations_within_capacity = getAllCombinations(P_Queue, 1);
		List<Person> Answer = new ArrayList<Person>();
		

		for (int i = 2; i <= P_Queue.size(); i++) {
			List<List<Person>> Temp = getAllCombinations(P_Queue, i);
			Iterator<List<Person>> I = Temp.iterator();
			while (I.hasNext()) {
				combinations.add(I.next());
			}
		}

		double total_weight = 0.0;
		for(int i = 1;i < combinations.size();i++)
		{
			for(int j = 0;j < combinations.get(i).size();j++)
			{
				
				total_weight = total_weight + combinations.get(i).get(j).getWeight();
				
			}
			
			if(Double.compare(total_weight, ElevatorWeightCapacity) < 0 || Double.compare(total_weight, ElevatorWeightCapacity) == 0)
			{
				combinations_within_capacity.add(combinations.get(i));
			}
			
			total_weight = 0.0;
		}
		
		double max_value = 0;
		double total_value = 0;
		
		for(int i = 0;i < combinations_within_capacity.size();i++)
		{
			for(int j = 0; j < combinations_within_capacity.get(i).size();j++)
			{
				total_value = total_value + combinations_within_capacity.get(i).get(j).getPriority();
			}

			if(Double.compare(total_value, max_value) > 0)
			{
				max_value = total_value;
				Answer = combinations_within_capacity.get(i);
			}
			total_value = 0;
			
		}
		
		PriorityQueue<Person> Answer_Priority_Queue = new PriorityQueue<Person>();
		for(int i = 0;i < Answer.size();i++)
		{
			Answer_Priority_Queue.offer(Answer.get(i));
		}
		
		return Answer_Priority_Queue;
		
	}

	public static void generateCombinations(PriorityQueue<Person> priorityQueue, int length,
		List<Person> currentCombination, List<List<Person>> result) {
		if (length == 0) {
			result.add(new ArrayList<>(currentCombination));
			return;
		}
		PriorityQueue<Person> clonedQueue = new PriorityQueue<>(priorityQueue);
		while (!clonedQueue.isEmpty()) {
			currentCombination.add(clonedQueue.poll());
			generateCombinations(clonedQueue, length - 1, currentCombination, result);
			currentCombination.remove(currentCombination.size() - 1);
		}
	}

	public static List<List<Person>> getAllCombinations(PriorityQueue<Person> priorityQueue, int length) {
		List<List<Person>> result = new ArrayList<>();
		generateCombinations(priorityQueue, length, new ArrayList<>(), result);
		return result;
	}
}