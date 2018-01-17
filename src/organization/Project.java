package organization;

import java.util.ArrayList;
import java.util.HashMap;

import item.AbstractItem;
import item.Item;
import item.ItemsReader;
import data.LaborPool;
import ecology.EcologyReader;
import ecology.WildSpecies;

/*
 * A project is some economic activity in the local tile. This may be to build some number
 * of doohickies, to gather some amount of the local organic or mineral resources, or otherwise.
 */
public class Project {
	
	public ProjectType type;
	public String target; // The sort of thing you're building/capturing.
	public int number; // If relevant. (Usually is.)
	public HashMap<String, String> extraInformation;
	public LaborPool laborRequirements;
	public LaborPool laborStore; 
	
	// What the project produces. We determine this based on the above
	// project properties.
	public HashMap<AbstractItem, Integer> products;
	
	/*
	 * What are you doing when you set up a project? You're building something.
	 * Soooo... That thing may have a type. 
	 * 
	 * If it's engineering, it may be a more general type and you specify its
	 * properties.
	 * If it's naturalism, specify type=Naturalism. Then target=animal/plant you're
	 * harvesting. 
	 */
	public Project(ProjectType type, String target, int number) {
		switch(type) {
		case NATURALISM:
			// In this case, target should be a wild species.
			WildSpecies targ = EcologyReader.getWildSpecies(target);
			
			for(String harvest : targ.harvestResult.keySet()) {
				products.put(ItemsReader.getAbstractItem(harvest), targ.harvestResult.get(harvest) * number);
			}
			
			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			laborRequirements = new LaborPool(0);
			laborRequirements.set(LaborPool.TYPE_NATURALISM, 1.0 * targ.power * number);
			
			break;
		case ENGINEERING:
			break;
		}
		
		laborStore = new LaborPool(0); 
	}
}

enum ProjectType {
	NATURALISM, ENGINEERING
}