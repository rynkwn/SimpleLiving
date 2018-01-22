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
	 * Updates a labor pool based on the species baseLabor, and the skills
	 * of the entity at hand. Modifier is an additional modifier on labor (expressed
	 * as a percentage), and may be based on the physical state of the entity.
	 */
	public void updateLabor(LaborPool baseLabor, SkillPool skills, double modifier) {
		this.naturalism = baseLabor.get(TYPE_NATURALISM) * ((skills.get(SkillPool.SKILL_NATURALISM) * .1) + .5);
		this.engineering = baseLabor.get(TYPE_ENGINEERING) * ((skills.get(SkillPool.SKILL_ENGINEERING) * .1) + .5);
		
		for(String laborType : laborList()) {
			modify(laborType, modifier);
		}
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
	 * Set the value for a specific attribute.
	 */
	public void set(String type, int value) {
		set(type, (double) value);
	}
	
	/*
	 * Add another labor pool to this one.
	 */
	public void add(LaborPool lp) {
		for(String laborType : laborList()) {
			double addedValue = lp.get(laborType);
			double curValue = get(laborType);
			
			set(laborType, addedValue + curValue);
		}
	}
	
	/*
	 * Subtract some amount of labor from this labor pool, to a minimum of 0.
	 */
	public void subtract(LaborPool lp) {
		for(String laborType : laborList()) {
			double subValue = lp.get(laborType);
			double curValue = get(laborType);
			
			double finalVal = Math.max(0, curValue - subValue);
			set(laborType, finalVal);
		}
	}
	
	
	
	/*
	 * Return how many times the given labor pool can factor into this one.
	 */
	public int factor(LaborPool lp) {
		int maxFactor = 0;
		
		for(String laborType : laborList()) {
			if(lp.get(laborType) > 0)
				maxFactor = (int) Math.max(maxFactor, get(laborType) / lp.get(laborType));
		}
		
		return maxFactor;
	}
	
	/*
	 * Applies a modifier (expressed as a percentage) to the specified labor
	 * type.
	 */
	public void modify(String type, double modifier) { 
		set(type, get(type) * modifier);
	}
	
	/*
	 * Applies a modifier to all labor types.
	 */
	public void modify(double modifier) {
		for(String laborType : laborList()) {
			modify(laborType, modifier);
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
	 * Checks if this labor pool is strictly greater than or equal to another labor pool
	 * in all labor types.
	 */
	public boolean greaterThanOrEqual(LaborPool lp) {
		for(String laborType: laborList()) {
			if(get(laborType) < lp.get(laborType))
				return false;
		}
		
		return true;
	}
	
	/*
	 * Check to see if all values are 0.
	 */
	public boolean isZero() {
		for(String laborType: laborList()) {
			if(get(laborType) != 0)
				return false;
		}
		
		return true;
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
	public String toString(String prefix) {
		StringBuilder sb = new StringBuilder();
		
		for(String laborType : laborList()) {
			sb.append(prefix + laborType + ": " + get(laborType) + "\n");
		}
		
		return sb.toString();
	}
}
