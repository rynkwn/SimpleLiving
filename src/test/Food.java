package item;

import entities.*;

/*
 * A class representing food and drink.
 */
public class Food extends Item {
	
	public Nutrition nutrition;
	
	public Food(String name, 
			double weight, 
			int quantity, 
			Nutrition nutrition) {
		
		super(name, ItemType.FOOD, weight, quantity);
		this.nutrition = new Nutrition(nutrition);
	}
	
	public Food split(int quant) {
		if(removeQuantity(quant)) {
			return new Food(name, weight, quant, nutrition);
		}
		
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		
		sb.append("type: FOOD" + "\n");
		sb.append("nutrition: " + nutrition.toString() + "\n");
		
		return sb.toString();
	}
}
