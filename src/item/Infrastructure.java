package item;

import java.util.HashMap;

import data.LaborPool;
import data.Macronutrient;

public class Infrastructure extends Item {
	
	// Name of the project it regenerates.
	// By regenerate, we mean that, whenever the project completes 
	// (or isn't on the list), we automatically re-add it as a project
	// at the bottom of the project queue.
	public String regenProjectName;

	// Below set of inputs are needed to re-create the Project
	// appropriately.
	public LaborPool laborRequirements;
	public HashMap<String, Integer> rawMaterials;
	public Macronutrient tileResources;

	public HashMap<String, Integer> products;

	/*
	 * Note: having multiple of an Infrastructure in your inventory will
	 * increase the size of the respective project by that multiple.
	 */
	public Infrastructure(String name, 
						  double weight, 
						  int quantity,
						  String projectName,
						  LaborPool lp,
						  HashMap<String, Integer> rawMats,
						  Macronutrient nutr) {
		super(name, ItemType.INFRASTRUCTURE, weight, quantity);
		
		regenProjectName = projectName;

		laborRequirements = new LaborPool(lp);
		rawMaterials = new HashMap<String, Integer>(rawMats);
		tileResources = new Macronutrient(nutr);
	}

}