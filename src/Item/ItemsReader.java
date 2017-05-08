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
			
			String name = scan.nextLine();
			String type = scan.nextLine();
			double base_weight = Double.parseDouble(scan.nextLine());
			double thirst_quenching = Double.parseDouble(scan.nextLine());
			double nitrogen = Double.parseDouble(scan.nextLine());
			double phosphorus = Double.parseDouble(scan.nextLine());
			double potassium = Double.parseDouble(scan.nextLine());
			double biomass = Double.parseDouble(scan.nextLine());
			
			items.put(name, new AbstractItem(name, type, base_weight, thirst_quenching, nitrogen, phosphorus, potassium, biomass));
			
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