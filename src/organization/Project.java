package organization;

import item.AbstractItem;
import item.Item;
import data.LaborPool;

/*
 * A project is some economic activity in the local tile. This may be to build some number
 * of doohickies, to gather some amount of the local organic or mineral resources, or otherwise.
 */
public class Project {
	
	public String type;
	public int number; // If relevant. (Usually is.)
	public LaborPool laborRequirements;
	
	// What the project produces.
	public AbstractItem product;
	
}
