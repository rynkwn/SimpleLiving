package item;

import java.util.HashMap;
import java.util.List;
import java.util.*;

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
	
	/*
	 * Returns a list of all items of a specific type.
	 */
	public ArrayList<Item> getItemsWithType(String type) {
		ArrayList<Item> matches = new ArrayList<Item>();
		
		for(Item item : items.values()) {
			if(item.type.equals(type)) {
				matches.add(item);
			}
		}
		
		return matches;
	}
	
	/*
	 * Returns all food items.
	 */
	public ArrayList<Food> getFoods() {
		ArrayList<Food> matches = new ArrayList<Food>();
		
		for(Item item : items.values()) {
			if(item.type.equals("FOOD")) {
				matches.add((Food) item);
			}
		}
		
		return matches;
	}
	
	/*
	 * Returns food items needed to satisfy a set of needs.
	 */
	public ArrayList<Food> findThingsToEat(HashMap<String, Double> needs) {
		
		// What do I need to do here?
		// For each food item, we need to check if there's enough to satisfy the entity.
		// If so, we remove enough to satisfy.
		// Otherwise, we gobble it up, and then look for the next item.
		
		ArrayList<Food> meal = new ArrayList<Food>();
		
		double caloriesNeeded = needs.get("calories");
		double waterNeeded = needs.get("water");
		double vitaminCNeeded = needs.get("vitaminC");
		
		for(Food food : getFoods()) {
			
			int numNeeded = (int) Math.ceil(Math.max(food.nutrition.calories / caloriesNeeded,
										Math.max(food.nutrition.water / waterNeeded, 
												food.nutrition.vitaminC / vitaminCNeeded)));
			
			if(numNeeded <= food.getQuantity()) {
				meal.add(food.split(numNeeded));
			} else {
				meal.add((Food) remove(food.name, food.getQuantity()));
			}
		}
		
		return meal;
	}
	
	/*
	 * This is a bad implementation.
	 */
	public Item remove(String itemName, int quantity) {
		Item item = items.get(itemName);
		item.removeQuantity(quantity);
		
		if(item.quantity == 0) {
			items.remove(item.name);
		}
		
		return item;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Item c : items.values()) {
			sb.append(c.toString() + "\n");
		}
		
		return sb.toString();
	}
	
	public void printDetailedContents() {
		for(Item c : items.values()) {
			System.out.println(c.toString());
		}
	}
}
