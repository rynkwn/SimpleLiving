package item;

import entities.*;

/*
 * Holds information about a class of item.
 */
public class AbstractItem {
	public String name;
	public ItemType type;
	public double base_weight;
	public Nutrition nutrition; // Nutritional Information
	
	public AbstractItem(String name,
			ItemType type,
			double baseWeight) {
		this.name = name;
		this.type = type;
		this.base_weight = baseWeight;
		nutrition = null;
	}
	
	public AbstractItem(String name,
			ItemType type,
			double base_weight,
			Nutrition nutrition) {
		
		this.name = name;
		this.type = type;
		this.base_weight = base_weight;
		this.nutrition = nutrition;
	}
	
	/*
	 * Makes a component that matches this pattern.
	 */
	public Item makeItem(int quantity) {
		switch(type) {
			case FOOD:
			return new Food(name, base_weight, quantity, nutrition);

			case MATERIAL:
			return new Item(name, type, base_weight, quantity);
		}
		
		return new Item();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("type: " + type + "\n");
		sb.append("base_weight: " + base_weight + "\n");
		
		if(nutrition != null)
			sb.append("nutrition: " + nutrition.toString() + "\n");
		
		return sb.toString();
	}
}
