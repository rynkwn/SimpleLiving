package item;

import entities.*;

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
	private static HashMap<String, AbstractItem> items;
	
	public static void readItemData(String dir) {
		initializeReader();
		readAllItemFiles(dir);
	}
	
	public static void initializeReader() {
		items = new HashMap<String, AbstractItem>();
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
			
			HashMap<String, String> properties = new HashMap<String, String>();
			
			while(scan.hasNextLine()) {
				String[] property = scan.nextLine().trim().split(":");
				properties.put(property[0], property[1]);
			}
			
			if(properties.get("TYPE").equals("FOOD")) {
				double water = Double.parseDouble(properties.get("WATER"));
				double calories = Double.parseDouble(properties.get("CALORIES"));
				double vitaminC = Double.parseDouble(properties.get("VITAMIN_C"));
				Nutrition nutr = new Nutrition(water, calories, vitaminC);
				
				items.put(properties.get("NAME"), new AbstractItem(properties.get("NAME"), 
																	properties.get("TYPE"),
																	Double.parseDouble(properties.get("BASE_WEIGHT")),
																	nutr));
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// I want a list containing the abstract components available.
	public Set<String> getAbstractComponentNames() {
		return items.keySet();
	}
	
	// Creates an instance of a component.
	public static Item makeComponent(String name, int quantity) {
		Item comp = items.get(name).makeItem();
		comp.setQuantity(quantity);
		return comp;
	}
	
	// Dumps the structure of ItemsReader.
	public static String debugDump() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Beginning debugDump for ItemsReader..." + "\n\n");
		
		for(String itemName : items.keySet()) {
			sb.append(itemName + "\n" + "_______________________" + "\n");
			sb.append(items.get(itemName).toString());
		}
		
		return sb.toString();
	}
}