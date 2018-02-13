package item;

import java.util.HashMap;
import java.util.List;
import java.util.*;

/*
 * Essentially a container class for a list of items.
 *
 * Note: Inventory should know only about the items inside and metadata relating to that. So it shouldn't have
 * attributes dependent on (for example) a group which has an inventory. In the case where a group wants to know
 * how many of something it can fit in its inventory, the inventory can have an interface to make that logic easier,
 * but ultimately it should be the group that determines that.
 */

public class Inventory {
	public HashMap<String, Item> items;
	public double weight;
	
	public Inventory() {
		items = new HashMap<String, Item>();
	}

	/*
	 * Removes as much of an item as possible, up to quantity.
	 */
	public Item remove(String itemName, int quantity) {
		Item item = items.get(itemName);
		Item removedItem = item.split(quantity);

		double lostWeight = removedItem.quantity * removedItem.weight;
		weight -= lostWeight;
		
		if(item.quantity == 0) {
			items.remove(item.name);
		}
		
		return removedItem;
	}
	
	// Add a component to inventory.
	public void add(Item comp) {
		double addedWeight = comp.quantity * comp.weight;
		weight += addedWeight;

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
	 * A simple getter method for the weight of the Inventory.
	 */
	public double getWeight() {
		return weight;
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
			if(item.type == ItemType.FOOD) {
				matches.add((Food) item);
			}
		}
		
		return matches;
	}
	
	/*
	 * Returns food items needed to satisfy a set of needs.
	 */
	public ArrayList<Food> findThingsToEat(HashMap<String, Double> needs) {
		
		ArrayList<Food> meal = new ArrayList<Food>();
		double calorieNeeds = needs.get("calories");
		double waterNeeds = needs.get("water");
		
		for(int i = 0; i < 2; i ++) {
			for(Food food : getFoods()) {
				int numDesired = 0;
				
				if(food.nutrition.calories > 0 && calorieNeeds > 0)
					numDesired = (int) Math.ceil((calorieNeeds / food.nutrition.calories));
				
				if(food.nutrition.water > 0 && waterNeeds > 0)
					numDesired = Math.min(numDesired, (int) Math.ceil((waterNeeds / food.nutrition.water)));
				
				int numQuantity = food.getQuantity();
				
				int numEaten = Math.min(numDesired, numQuantity);
				
				calorieNeeds -= (numEaten * food.nutrition.calories);
				waterNeeds -= (numEaten * food.nutrition.water);
				
				if(numDesired <= food.getQuantity()) {
					meal.add(food.split(numDesired));
				} else {
					meal.add((Food) remove(food.name, food.getQuantity()));
				}
			}
			
			// If we've already eaten all we need, break out of the loop.
			if(calorieNeeds <= 0 && waterNeeds <= 0) {
				break;
			}
		}
		
		return meal;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Total Weight: " + weight + "\n");
		for(Item c : items.values()) {
			sb.append(c.toString() + "\n");
		}
		
		return sb.toString();
	}
}
