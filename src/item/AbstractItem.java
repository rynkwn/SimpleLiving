package item;

import java.util.HashMap;

import data.LaborPool;
import data.Macronutrient;
import entities.*;

/*
 * Holds information about a class of item.
 */
public class AbstractItem {
	public String name;
	public ItemType type;
	public double base_weight;

	public Nutrition nutrition; // Nutritional Information

	// Additional parameters for infrastructure
	public String projectName;
	public LaborPool laborRequirements;
	public HashMap<String, Integer> rawMaterials;
	public Macronutrient tileResources;

	public HashMap<String, Integer> products;
	
	public AbstractItem(String name,
			ItemType type,
			double baseWeight) {
		this.name = name;
		this.type = type;
		this.base_weight = baseWeight;
		nutrition = null;
	}
	
	// Food item constructors.
	public AbstractItem(String name,
			ItemType type,
			double base_weight,
			Nutrition nutrition) {
		
		this.name = name;
		this.type = type;
		this.base_weight = base_weight;
		this.nutrition = nutrition;

		projectName = null;

		laborRequirements = null;
		rawMaterials = null;
		tileResources = null;
		products = null;
	}

	public AbstractItem(String name,
						ItemType type,
						double base_weight,
						String projectName,
						LaborPool laborRequirements,
						HashMap<String, Integer> rawMaterials,
						Macronutrient tileResources,
						HashMap<String, Integer> products) {

		this.name = name;
		this.type = type;
		this.base_weight = base_weight;

		this.nutrition = null;

		this.projectName = projectName;
		this.laborRequirements = new LaborPool(laborRequirements);
		this.rawMaterials = new HashMap<String, Integer>(rawMaterials);
		this.tileResources = new Macronutrient(tileResources);
		this.products = new HashMap<String, Integer>(products);
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

			case INFRASTRUCTURE:
				return new Infrastructure(name, 
										  base_weight,
										  quantity,
										  projectName,
										  laborRequirements, 
										  rawMaterials, 
										  tileResources, 
										  products);
		}
		
		return new Item();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("type: " + type + "\n");
		sb.append("base_weight: " + base_weight + "\n");
		
		if(type == ItemType.FOOD) {
			sb.append("nutrition: " + nutrition.toString() + "\n");
		} else if(type == ItemType.INFRASTRUCTURE) {
			sb.append("project name: " + projectName + "\n");
			sb.append("laborRequirements: " + laborRequirements + "\n");
			sb.append("rawMaterials: " + rawMaterials + "\n");
			sb.append("tileResources: " + tileResources + "\n");
			sb.append("products: " + products + "\n");
		}
		
		return sb.toString();
	}
}
