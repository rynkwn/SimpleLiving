package data;

import java.util.ArrayList;

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
	
	public void subtract(String key, double value) {
		double curValue = get(key);
		set(key, curValue - value);
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
