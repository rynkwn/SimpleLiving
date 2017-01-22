package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * ComponentsReader reads in the Components data file and offers an interface to parse and
 * interact with the data.
 */
public class ComponentsReader {
	private static HashMap<String, AbstractComponent> components = new HashMap<String, AbstractComponent>();
	
	public static void readComponentData() {
		Scanner scan;
		
		try {
		scan = new Scanner(new File("components.txt"));
		
		String name = scan.nextLine();
		while(!name.equals("")) {
		
			double weight = scan.nextDouble();
			int numSpecialEffects = scan.nextInt();
			scan.nextLine(); // Clear the newline character.
		
			AbstractComponent abstractComp = new AbstractComponent(name, weight);
			
			for(int i = 0; i < numSpecialEffects; i++) {
				String specialEffect = scan.nextLine().substring(1);
				long effectValue = scan.nextLong();
				scan.nextLine();
				
				abstractComp.addSpecial(specialEffect, effectValue);
			}
			
			components.put(name, abstractComp);
			
			if(!scan.hasNextLine())
				name = "";			
			name = scan.nextLine();
		}
		
		scan.close();
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	// I want a list containing the abstract components available.
	
	// I want to check if an item matches a given AbstractComponent.
	public static boolean instanceOfAbstractComponent() {
		return true;
	}
	
	// I want to instantiate an item that matches a description.
	
}


/*
 * AbstractComponent stores the information held in the Components data file in an
 * easy way.
 */
class AbstractComponent {
	public String name;
	public double weight;
	public HashMap<String, Long> special;
	
	public AbstractComponent(String name, double weight) {
		this.name = name;
		this.weight = weight;
		special = new HashMap<String, Long>();
	}
	
	/*
	 * Adds a special. We expect these to be unique. So if our hashmap already
	 * contains the Special Effect, we return false without adding anything.
	 */
	public boolean addSpecial(String effect, long effectValue) {
		if(special.containsKey(effect)) {
			return false;
		}
		
		special.put(effect, effectValue);
		return true;
	}
	
}