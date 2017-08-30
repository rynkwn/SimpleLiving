package data;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * A wrapper class for macronutrients.
 */

public class Macronutrient {
	public static final double BAD_FLAG = -9999999;
	
	public double water;
	public double carbon;
	public double nitrogen;
	public double potassium;
	public double calcium;
	public double phosphorus;
	public double salt;
	
	public Macronutrient(double water,
			double carbon,
			double nitrogen,
			double potassium,
			double calcium,
			double phosphorus,
			double salt
			) {
		
		this.water = water;
		this.carbon = carbon;
		this.nitrogen = nitrogen;
		this.potassium = potassium;
		this.calcium = calcium;
		this.phosphorus = phosphorus;
		this.salt = salt;
	}
	
	// A cloning method.
	public Macronutrient(Macronutrient clone) {
		for(String nutrient: Macronutrient.nutrientList()) {
			set(nutrient, clone.get(nutrient));
		}
	}
	
	public Macronutrient(HashMap<String, Double> nutrients) {
		water = nutrients.get("water");
		carbon = nutrients.get("carbon");
		nitrogen = nutrients.get("nitrogen");
		potassium = nutrients.get("potassium");
		calcium = nutrients.get("calcium");
		phosphorus = nutrients.get("phosphorus");
		salt = nutrients.get("salt");
	}
	
	public Macronutrient(double initialValue) {
		water = carbon = nitrogen = potassium = calcium = phosphorus = salt = initialValue;
	}
	
	public double get(String key) {
		switch(key) {
		case "water": 
			return water;
		case "carbon":
			return carbon;
		case "nitrogen":
			return nitrogen;
		case "potassium":
			return potassium;
		case "calcium":
			return calcium;
		case "phosphorus":
			return phosphorus;
		case "salt":
			return salt;
		}
		
		return Macronutrient.BAD_FLAG;
	}
	
	public void set(String key, double value) {
		switch(key) {
		case "water": 
			water = value;
			break;
		case "carbon":
			carbon = value;
			break;
		case "nitrogen":
			nitrogen = value;
			break;
		case "potassium":
			potassium = value;
			break;
		case "calcium":
			calcium = value;
			break;
		case "phosphorus":
			phosphorus = value;
			break;
		case "salt":
			salt = value;
			break;
		}
	}
	
	public void add(String key, double value) {
		double curValue = get(key);
		set(key, curValue + value);
	}
	
	public void add(Macronutrient nutr) {
		for(String nutrient : nutrientList()) {
			add(nutrient, nutr.get(nutrient));
		}
	}
	
	public void add(Macronutrient nutr, int multiplier) {
		for(String nutrient : nutrientList()) {
			add(nutrient, multiplier * nutr.get(nutrient));
		}
	}
	
	public void subtract(String key, double value) {
		double curValue = get(key);
		set(key, curValue - value);
	}
	
	public double nutrientSum() {
		double sum = 0;
		for(String nutrient : Macronutrient.nutrientList()) {
			sum += get(nutrient);
		}
		
		return sum;
	}
	
	/*
	 * Return true if all nutrients contained in this Macronutrient are greater
	 * than or equal to the provided HashMap.
	 * 
	 * HashMap must contain extant nutrient.
	 */
	public boolean greaterThanOrEqualTo(HashMap<String, Double> nutrCost) {
		for(String nutrient : nutrCost.keySet()) {
			if(get(nutrient) < nutrCost.get(nutrient))
				return false;
		}
		return true;
	}
	
	/*
	 * Return how many times the given divisor can factor into
	 * this macronutrient.
	 */
	public double factor(Macronutrient divisor) {
		double fctr = Double.MAX_VALUE;
		
		for(String nutr : Macronutrient.nutrientList()) {
			fctr = Math.min(fctr, get(nutr) / divisor.get(nutr));
		}
		
		return fctr;
	}
	
	public String toString() {
		return "water: " + water + 
				", carbon: " + carbon + 
				", nitrogen: " + nitrogen + 
				", potassium: " + potassium + 
				", calcium: " + calcium +
				", phosphorus: " + phosphorus +
				", salt: " + salt;
	}
	
	/*
	 * Returns a list containing all nutrient names.
	 */
	public static ArrayList<String> nutrientList() {
		ArrayList<String> nutrients = new ArrayList<String>();
		
		nutrients.add("water");
		nutrients.add("carbon");
		nutrients.add("nitrogen");
		nutrients.add("potassium");
		nutrients.add("calcium");
		nutrients.add("phosphorus");
		nutrients.add("salt");
		
		return nutrients;
	}
}
