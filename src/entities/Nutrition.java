package entities;

import item.*;
import util.*;

import java.util.*;

/*
 * Mostly an interface for nutrition.
 */
public class Nutrition {
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

	public String type = "ABSTRACT"; // values: ABSTRACT, ANIMAL, PLANT 
	
	public double metabolism; // A modifier to nutritional needs.
	
	// Needs
	public double water;
	
	// Animal Needs
	public double calories;
	public double vitaminC;
	
	// Plant Needs
	public double carbon; // UNSURE: Say 300 ppm
	public double nitrogen; // 210 ppm
	public double potassium; // 235 ppm
	public double calcium; // 200 ppm
	public double phosphorus; // 31 ppm
	public double salt; // UNSURE: Say 5 ppm
	
	public Nutrition(String type, double metabolism) {
		this.type = type;
		this.metabolism = metabolism;
	}
	
	// Explicitly defined Nutrition to be used by Food/Items.
	public Nutrition(double water, double calories, double vitaminC) {
		this.water = water;
		this.calories = calories;
		this.vitaminC = vitaminC;
	}
	
	// Explicitly defined nutrition for plant-related items.
	public Nutrition(double H2O, double C, double N, double K,
					 double Ca, double P, double NaCl) {
		water = H2O;
		carbon = C;
		nitrogen = N;
		potassium = K;
		calcium = Ca;
		phosphorus = P;
		salt = NaCl;
	}
	
	// Essentially a clone method.
	public Nutrition(Nutrition n) {
		this.type = n.type;
		this.metabolism = n.metabolism;
		this.water = n.water;
		this.calories = n.calories;
		this.vitaminC = n.vitaminC;
		
		this.nitrogen = n.nitrogen;
		this.phosphorus = n.phosphorus;
		this.potassium = n.potassium;
		this.carbon = n.carbon;
		this.nitrogen = n.nitrogen;
		this.potassium = n.potassium;
		this.calcium = n.calcium;
		this.phosphorus = n.phosphorus;
		this.salt = n.salt;
	}
	
	// Updates needs based on a new mass value.
	public void updateNeeds(double mass) {
		
		if(type.equals("ANIMAL")) {
			
			water = .1 * mass * metabolism;
			calories = .1 * mass * metabolism;
			vitaminC = .01 * mass * metabolism;
			
		} else if(type.equals("PLANT")) {
			
			water = .1 * mass * metabolism;
			carbon = .1 * (Nutrition.CARBON_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			nitrogen = .1 * (Nutrition.NITROGEN_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			potassium = .1 * (Nutrition.POTASSIUM_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			calcium = .1 * (Nutrition.CALCIUM_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			phosphorus = .1 * (Nutrition.PHOSPHORUS_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			salt = .1 * (Nutrition.SALT_PPM / Nutrition.PLANT_TOTAL_NUTRIENT_MASS) * mass * metabolism;
			
		}
	}
	
	public HashMap<String, Double> nutrition() {
		HashMap<String, Double> requirements = new HashMap<String, Double>();
		
		requirements.put("water", water);
		
		if(type.equals("ABSTRACT")) {
			
			requirements.put("calories", calories);
			requirements.put("vitaminC", vitaminC);
			
			requirements.put("carbon", carbon);
			requirements.put("nitrogen", nitrogen);
			requirements.put("potassium", potassium);
			requirements.put("calcium", calcium);
			requirements.put("phosphorus", phosphorus);
			requirements.put("salt", salt);

			
		} else if (type.equals("ANIMAL")) {
			
			requirements.put("calories", calories);
			requirements.put("vitaminC", vitaminC);
			
		} else if (type.equals("PLANT")) {
			
			requirements.put("carbon", carbon);
			requirements.put("nitrogen", nitrogen);
			requirements.put("potassium", potassium);
			requirements.put("calcium", calcium);
			requirements.put("phosphorus", phosphorus);
			requirements.put("salt", salt);
			
		}

		return requirements;
	}
	
	// pctEffectiveness is the efficiency of whatever part is mechanically performing the consumption.
	public double eat(double pctEffectiveness, ArrayList<Food> meal) {
		
		double[] needs = {
				water,
				calories,
				vitaminC
		};
		
		double[] satisfied = new double[needs.length];
		
		
		for(Food fd : meal) {
			satisfied[0] += fd.nutrition.water * pctEffectiveness * fd.quantity;
			satisfied[1] += fd.nutrition.calories * pctEffectiveness * fd.quantity;
			satisfied[2] += fd.nutrition.vitaminC * pctEffectiveness * fd.quantity;
		}
		
		for(int i = 0; i < needs.length; i++) {
			needs[i] = satisfied[i] / needs[i];
		}
		
		return MathUtils.min(needs);
	}
	
	public String toString() {
		return nutrition().toString();
	}
	
}
