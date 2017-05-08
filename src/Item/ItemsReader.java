package item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * ComponentsReader reads in the Components data file and offers an interface to parse and
 * interact with the data.
 */
public class ItemsReader {
	private static HashMap<String, AbstractComponent> items;
	
	public static void readItemData(String dir) {
		initializeReader();
		readAllItemFiles(dir);
	}
	
	public static void initializeReader() {
		items = new HashMap<String, AbstractComponent>();
	}
	
	public static void readAllItemFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(".item")) {
				readItemFile(file);
			}
		}
	}
	
	public static void readItemFile(File file) {
		Scanner scan;
		
		try {
		scan = new Scanner(file);
		
		String name = scan.nextLine();
		while(scan.hasNextLine()) {
		
			double weight = scan.nextDouble();
			int numSpecialEffects = scan.nextInt();
			scan.nextLine(); // Clear the newline character.
		
			AbstractComponent abstractComp = new AbstractComponent(name, weight);
			
			for(int i = 0; i < numSpecialEffects; i++) {
				String specialEffect = scan.nextLine().substring(1);
				long effectValue = scan.nextLong();
				
				if(scan.hasNextLine())
					scan.nextLine();
				
				abstractComp.addSpecial(specialEffect, effectValue);
			}
			
			items.put(name, abstractComp);
			
			if(!scan.hasNextLine())
				break;	
			name = scan.nextLine();
		}
		
		scan.close();
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	// I want a list containing the abstract components available.
	public Set<String> getAbstractComponentNames() {
		return items.keySet();
	}
	
	// Creates an instance of a component.
	public static Item makeComponent(String name, int quantity) {
		Item comp = items.get(name).makeComponent();
		comp.setQuantity(quantity);
		return comp;
	}
	
	// Returns the special effects associated with a component.
	public static Set<String> getSpecialEffects(String name) {
		return items.get(name).getSpecialEffects();
	}
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
	
	/*
	 * Makes a component that matches this pattern.
	 */
	public Item makeComponent() {
		return new Item(name, weight);
	}
	
	public Set<String> getSpecialEffects() {
		return special.keySet();
	}
}