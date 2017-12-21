package data;

/*
 * A representation of a group of labor, divided into types.
 * 
 * This could represent the aggregate labor available to a group, or the labor demands
 * of a particular project. In other words, it's the labor version of the MacroNutrient
 * class.
 */
public class LaborPool {
	
	// Labor related to getting biological products from the environment. (Gathering/hunting).
	public double naturalism;
	
	// Labor related to building anything.
	public double engineering;
	
	public LaborPool() {
		
	}

}
