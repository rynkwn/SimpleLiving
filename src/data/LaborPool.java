package data;

import java.util.ArrayList;

/*
 * A representation of a group of labor, divided into types.
 * 
 * This could represent the aggregate labor available to a group, or the labor demands
 * of a particular project. In other words, it's the labor version of the MacroNutrient
 * class.
 */
public class LaborPool {
	public static final String TYPE_NATURALISM = "Naturalism";
	public static final String TYPE_ENGINEERING = "Engineering";
	
	// Labor related to getting biological products from the environment. (Gathering/hunting).
	public double naturalism;
	
	// Labor related to building anything.
	public double engineering;
	
	public LaborPool(double initialValue) {
		this.naturalism = initialValue;
		this.engineering = initialValue;
	}
	
	public LaborPool(double naturalism,
					 double engineering
					 ) {
		
		this.naturalism = naturalism;
		this.engineering = engineering;
	}
	
	public LaborPool(LaborPool lp) {
		this.naturalism = lp.naturalism;
		this.engineering = lp.engineering;
	}
	
	/*
	 * Get the value for a specific attribute.
	 */
	public double get(String type) {
		switch(type) {
		case TYPE_NATURALISM:
			return naturalism;
		case TYPE_ENGINEERING:
			return engineering;
		}
		
		return 0;
	}
	
	/*
	 * Set the value for a specific attribute.
	 */
	public void set(String type, Double value) {
		switch(type) {
		case TYPE_NATURALISM:
			naturalism = value;
			break;
		case TYPE_ENGINEERING:
			engineering = value;
			break;
		}
	}
	
	/*
	 * Set all values to 0.
	 */
	public void zero() {
		for(String laborType : laborList()) {
			set(laborType, 0.0);
		}
	}

	/*
	 * Returns a list containing all labor types.
	 */
	public static ArrayList<String> laborList() {
		ArrayList<String> laborTypes = new ArrayList<String>();
		
		laborTypes.add(TYPE_NATURALISM);
		laborTypes.add(TYPE_ENGINEERING);
		
		return laborTypes;
	}
	
	/*
	 * Return a String representation of this LaborPool.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(String laborType : laborList()) {
			sb.append(laborType + ": " + get(laborType) + "\n");
		}
		
		return sb.toString();
	}
}
