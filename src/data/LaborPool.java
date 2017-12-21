package data;

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
	
	public LaborPool(double naturalism,
					 double engineering
					 ) {
		
		this.naturalism = naturalism;
		this.engineering = engineering;
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

}
