package item;

import java.util.HashMap;

/*
 * Essentially a container class for a list of items.
 */

public class Inventory {
	public HashMap<String, Component> items;
	
	public Inventory() {
		items = new HashMap<String, Component>();
	}
	
	// Add a component to inventory.
	public void add(Component comp) {
		if(items.containsKey(comp.getName())) {
			items.get(comp.getName()).addQuantity(comp.getQuantity());
		} else {
			items.put(comp.getName(), comp);
		}
	}
	
	
}
