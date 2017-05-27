package item;

import entities.*;

/*
 * Holds information about a class of item.
 */
class AbstractItem {
	public String name;
	public String type;
	public double base_weight;
	public Nutrition nutrition; // Nutritional Information
	
	public AbstractItem(String name,
			String type,
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
		if(type.equalsIgnoreCase("FOOD")) {
			return new Food(name, base_weight, quantity, new Nutrition(nutrition));
		}
		
		return new Item();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("type: " + type + "\n");
		sb.append("base_weight: " + base_weight + "\n");
		sb.append("nutrition: " + nutrition.toString() + "\n");
		
		return sb.toString();
	}
}
