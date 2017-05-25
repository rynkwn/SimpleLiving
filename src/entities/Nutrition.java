package entities;

import item.*;

import java.util.*;

/*
 * Mostly an interface for nutrition.
 */
public class Nutrition {
	String type = "ABSTRACT"; // values: ABSTRACT, ANIMAL, PLANT 
	
	// Needs
	public double water;
	
	// Animal Needs
	public double calories;
	public double vitaminC;
	
	// Plant Needs
	public double nitrogen;
	public double phosphorus;
	public double potassium;
	public double biomass;
	
	public Nutrition(String type) {
		this.type = type;
	}
	
	// Explicitly defined Nutrition to be used by Food/Items.
	public Nutrition(double water, double calories, double vitaminC) {
		this.water = water;
		this.calories = calories;
		this.vitaminC = vitaminC;
	}
	
	// Explicitly defined nutrition for plant-related items.
	public Nutrition(double N, double P, double K, double biomass) {
		nitrogen = N;
		phosphorus = P;
		potassium = K;
		this.biomass = biomass;
	}
	
	// Essentially a clone method.
	public Nutrition(Nutrition n) {
		this.type = n.type;
		this.water = n.water;
		this.calories = n.calories;
		this.vitaminC = n.vitaminC;
		
		this.nitrogen = n.nitrogen;
		this.phosphorus = n.phosphorus;
		this.potassium = n.potassium;
		this.biomass = n.biomass;
	}
	
	// Updates needs based on a new mass value.
	public void updateNeeds(long mass) {
		
		if(type.equals("ANIMAL")) {
			
			water = .1 * mass;
			calories = .1 * mass;
			vitaminC = .01 * mass;
			
		} else if(type.equals("PLANT")) {
			
			water = .1 * mass;
			nitrogen = .05 * mass;
			phosphorus = .01 * mass;
			potassium = .01 * mass;
			biomass = .1 * mass;
			
		}
	}
	
	public HashMap<String, Double> nutrition() {
		HashMap<String, Double> requirements = new HashMap<String, Double>();
		
		requirements.put("water", water);
		
		if(type.equals("ABSTRACT")) {
			
			requirements.put("calories", calories);
			requirements.put("vitaminC", vitaminC);
			
			requirements.put("nitrogen", nitrogen);
			requirements.put("phosphorus", potassium);
			requirements.put("potassium", phosphorus);
			requirements.put("biomass", biomass);
			
		} else if (type.equals("ANIMAL")) {
			
			requirements.put("calories", calories);
			requirements.put("vitaminC", vitaminC);
			
		} else if (type.equals("PLANT")) {
			
			requirements.put("nitrogen", nitrogen);
			requirements.put("phosphorus", potassium);
			requirements.put("potassium", phosphorus);
			requirements.put("biomass", biomass);
			
		}

		return requirements;
	}
	
	// pctEffectiveness is the efficiency of whatever part is mechanically performing the consumption.
	public double eat(double pctEffectiveness, Food fd) {
		return 0;
	}
	
	public String toString() {
		return nutrition().toString();
	}
	
}
