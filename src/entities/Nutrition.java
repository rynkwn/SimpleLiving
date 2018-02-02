package entities;

import item.*;
import util.*;

import java.util.*;

import data.Macronutrient;

/*
 * An interface for nutrition. Essentially, this details nutritional REQUIREMENTS for a given
 * organism, species, or some other entity that might require nutritional requirements.
 */
public class Nutrition {
	public static final String PLANT_TYPE = "PLANT";
	public static final String ABSTRACT_TYPE = "ABSTRACT";
	public static final String ANIMAL_TYPE = "ANIMAL";
	
	// PLANT CONSTANTS
	// Values from: https://en.wikipedia.org/wiki/Hoagland_solution
	public static final double CARBON_PPM = 300;
	public static final double NITROGEN_PPM = 210;
	public static final double POTASSIUM_PPM = 235;
	public static final double CALCIUM_PPM = 200;
	public static final double PHOSPHORUS_PPM = 31;
	public static final double SALT_PPM = 5;
	
	public static final double PLANT_TOTAL_NUTRIENT_MASS = CARBON_PPM + 
											NITROGEN_PPM + POTASSIUM_PPM +
											CALCIUM_PPM + PHOSPHORUS_PPM + SALT_PPM;

	public String type = ABSTRACT_TYPE; // values: ABSTRACT, ANIMAL, PLANT 
	
	public double metabolism; // A modifier to nutritional needs.
	
	// Needs
	public double water;
	
	// Animal Needs
	public double calories;
	
	public Macronutrient plantNutrition;
	
	public Nutrition(String type, double metabolism) {
		this.type = type;
		this.metabolism = metabolism;
		
		plantNutrition = new Macronutrient(0);
	}
	
	// Explicitly defined Nutrition to be used by Food/Items.
	public Nutrition(double water, double calories) {
		this.water = water;
		this.calories = calories;
	}
	
	// Explicitly defined nutrition for plant-related items.
	public Nutrition(double H2O, double C, double N, double K,
					 double Ca, double P, double NaCl) {
		
		plantNutrition = new Macronutrient(H2O, C, N, K, Ca, P, NaCl);
	}
	
	// Essentially a clone method.
	public Nutrition(Nutrition n) {
		this.type = n.type;
		this.metabolism = n.metabolism;
		this.water = n.water;
		this.calories = n.calories;
		
		if(n.plantNutrition != null)
			plantNutrition = new Macronutrient(n.plantNutrition);
	}
	
	// Updates needs based on a new mass value.
	public void updateNeeds(double mass) {
		
		if(type.equals(ANIMAL_TYPE)) {
			
			water = .1 * mass * metabolism;
			calories = .1 * mass * metabolism;
			
		} else if(type.equals(PLANT_TYPE)) {
			
			if(plantNutrition == null) {
				plantNutrition = new Macronutrient(0);
			}
			
			plantNutrition.set("water", .1 * mass * metabolism);
			plantNutrition.set("carbon", .1 * (Nutrition.CARBON_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			plantNutrition.set("nitrogen", .1 * (Nutrition.NITROGEN_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			plantNutrition.set("potassium", .1 * (Nutrition.POTASSIUM_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			plantNutrition.set("calcium", .1 * (Nutrition.CALCIUM_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			plantNutrition.set("phosphorus", .1 * (Nutrition.PHOSPHORUS_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			plantNutrition.set("salt", .1 * (Nutrition.SALT_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism);
			
		}
	}
	
	public HashMap<String, Double> nutrition() {
		HashMap<String, Double> requirements = new HashMap<String, Double>();
		
		requirements.put("water", water);
		
		if(type.equals("ABSTRACT")) {
			
			requirements.put("calories", calories);
			
			if(plantNutrition != null) {
				for(String nutrient : Macronutrient.nutrientList()) {
					requirements.put(nutrient, plantNutrition.get(nutrient));
				}
			}
			
		} else if (type.equals("ANIMAL")) {
			
			requirements.put("calories", calories);
			
		} else if (type.equals("PLANT")) {
			
			for(String nutrient : Macronutrient.nutrientList()) {
				requirements.put(nutrient, plantNutrition.get(nutrient));
			}
			
		}

		return requirements;
	}
	
	// pctEffectiveness is the efficiency of whatever part is mechanically performing the consumption.
	public double eat(double pctEffectiveness, ArrayList<Food> meal) {
		
		double[] needs = {
				water,
				calories,
		};
		
		double[] satisfied = new double[needs.length];
		
		
		for(Food fd : meal) {
			satisfied[0] += fd.nutrition.water * pctEffectiveness * fd.quantity;
			satisfied[1] += fd.nutrition.calories * pctEffectiveness * fd.quantity;
		}
		
		for(int i = 0; i < needs.length; i++) {
			needs[i] = satisfied[i] / needs[i];
		}
		
		return MathUtils.min(needs);
	}
	
	/*
	 * Return a Macronutrient representation of the nutritional needs.
	 * 
	 * Only viable if the nutrition type is a plant.
	 */
	public Macronutrient getMacronutrients() {
		return plantNutrition;
	}
	
	public String toString() {
		return nutrition().toString();
	}
	
}
