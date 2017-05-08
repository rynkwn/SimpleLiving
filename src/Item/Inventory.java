package item;

import java.util.HashMap;
import java.util.List;

/*
 * Essentially a container class for a list of items.
 */

public class Inventory {
	public HashMap<String, Item> items;
	
	public Inventory() {
		items = new HashMap<String, Item>();
	}
	
	// Add a component to inventory.
	public void add(Item comp) {
		if(items.containsKey(comp.getName())) {
			items.get(comp.getName()).addQuantity(comp.getQuantity());
		} else {
			items.put(comp.getName(), comp);
		}
	}
	
	// Add a list of components to inventory.
	public void addList(List<Item> components) {
		for(Item comp : components) {
			add(comp);
		}
	}
	
	public void printDetailedContents() {
		for(Item c : items.values()) {
			System.out.println(c.toString());
		}
	}
}
