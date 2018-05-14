package data;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * A wrapper class for macronutrients, which are for plants.
 */

public class Macronutrient {
	public static final double BAD_FLAG = -9999999;
	
	public double water;
	public double fertility;
	
	public Macronutrient(double water,
			double fertility
			) {
		
		this.water = water;
		this.fertility = fertility;
	}
	
	// A cloning method.
	public Macronutrient(Macronutrient clone) {
		for(String nutrient: Macronutrient.nutrientList()) {
			set(nutrient, clone.get(nutrient));
		}
	}
	
	public Macronutrient(HashMap<String, Double> nutrients) {
		water = nutrients.get("water");
		fertility = nutrients.get("fertility");
	}
	
	public Macronutrient(double initialValue) {
		water = fertility = initialValue;
	}
	
	public double get(String key) {
		switch(key) {
		case "water": 
			return water;
		case "fertility":
			return fertility;
		}
		
		return Macronutrient.BAD_FLAG;
	}
	
	public void set(String key, double value) {
		switch(key) {
		case "water": 
			water = value;
			break;
		case "fertility":
			fertility = value;
			break;
		}
	}
	
	public void add(String key, double value) {
		double curValue = get(key);
		set(key, curValue + value);
	}
	
	/*
	 * Add another macronutrient to this one.
	 */
	public void add(Macronutrient nutr) {
		for(String nutrient : nutrientList()) {
			add(nutrient, nutr.get(nutrient));
		}
	}
	
	/*
	 * Add a macronutrient with a multiplier.
	 */
	public void add(Macronutrient nutr, int multiplier) {
		for(String nutrient : nutrientList()) {
			add(nutrient, multiplier * nutr.get(nutrient));
		}
	}
	
	/*
	 * Subtract a specified macronutrient (with associated value) from this macronutrient.
	 */
	public void subtract(String key, double value) {
		double curValue = get(key);
		set(key, curValue - value);
	}
	
	/*
	 * Subtract another Macronutrient from this one.
	 */
	public void subtract(Macronutrient nutr) {
		for(String nutrient : nutrientList()) {
			subtract(nutrient, nutr.get(nutrient));
		}
	}

	/*
	 * Subtract another Macronutrient from this one, with an integer multiplier.
	 */
	public void subtract(Macronutrient nutr, int multiplier) {
		for(String nutrient : nutrientList()) {
			subtract(nutrient, multiplier * nutr.get(nutrient));
		}
	}

	/*
	 * Subtract another Macronutrient from this one, with a double multiplier.
	 */
	public void subtract(Macronutrient nutr, double multiplier) {
		for(String nutrient : nutrientList()) {
			subtract(nutrient, multiplier * nutr.get(nutrient));
		}
	}

	/*
	 * Apply a modifier to every nutrient in this Macronutrient.
	 * Modifier is multiplied to the value of every nutrient.
	 */
	public void modify(double modifier) {
		for(String nutrient : nutrientList()) {
			set(nutrient, get(nutrient) * modifier);
		}
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
				", fertility: " + fertility;
	}
	
	/*
	 * Returns a list containing all nutrient names.
	 */
	public static ArrayList<String> nutrientList() {
		ArrayList<String> nutrients = new ArrayList<String>();
		
		nutrients.add("water");
		nutrients.add("fertility");
		
		return nutrients;
	}
}
