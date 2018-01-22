package organization;

import java.util.ArrayList;
import java.util.HashMap;

import item.AbstractItem;
import item.Item;
import item.ItemsReader;
import data.LaborPool;
import ecology.EcoTile;
import ecology.EcologyReader;
import ecology.WildSpecies;

/*
 * A project is some economic activity in the local tile. This may be to build some number
 * of doohickies, to gather some amount of the local organic or mineral resources, or otherwise.
 */
public class Project {
	
	public boolean valid;
	
	public ProjectType type;
	public String target; // The sort of thing you're building/capturing.
	public int number; // If relevant. (Usually is.)
	public HashMap<String, String> extraInformation;
	public LaborPool laborRequirements;
	public LaborPool laborStore; 
	
	// The group performing the project.
	public Group grp;
	
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
	public Project(ProjectType type, Group grp, String target, int number) {
		this.grp = grp;
		valid = true;
		
		laborStore = new LaborPool(0);
		extraInformation = new HashMap<String, String>();
		
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
			
			extraInformation.put("laborCostPerUnit", "" + targ.power);
			
			this.target = target;
			this.number = number;
			
			break;
		case ENGINEERING:
			break;
		}
		
		laborStore = new LaborPool(0); 
	}
	
	/*
	 * Performs as much work as possible given the LaborPool * pct.
	 * 
	 * Also reduces the labor values in the passed in labor pool as a side effect.
	 * 
	 * For example, we can allocate 50% of the labor pool for this work, and any unused
	 * will be left for future projects (or for idling).
	 * 
	 * Depending on the type of the work, this may also affect the group's local tile.
	 */
	public void work(LaborPool lp, double pct) {
		lp.modify(pct);
		
		LaborPool addedLabor = new LaborPool(lp);
		addedLabor.modify(pct);
		
		// TODO: Eventually have excess labor go back into the labor pool for projects
		// in the same turn.
		
		switch(type) {
		case NATURALISM:
			// We're extracting something from the natural environment.
			HashMap<String, Integer> localSpecies = grp.ecoTile.localWildlife();
			
			if(localSpecies.containsKey(target)) {
				double natLabor = addedLabor.get(LaborPool.TYPE_NATURALISM);
				
				int natLaborPerUnit = Integer.parseInt(extraInformation.get("laborCostPerUnit"));
				
				int numCanHarvest = (int) (natLabor / natLaborPerUnit);
				int numAvailable = localSpecies.get(target);
				
				int numHarvested = Math.min(numCanHarvest, numAvailable);
				grp.ecoTile.subPop(target, numHarvested);
				
				addedLabor.set(LaborPool.TYPE_NATURALISM, numHarvested * natLaborPerUnit);
			}
			
			break;
		case ENGINEERING:
			break;
		}
		
		laborStore.add(addedLabor);
		
		if(laborStore.greaterThanOrEqual(laborRequirements)) {
			for(AbstractItem product : products.keySet()) {
				grp.addItem(product.makeItem(products.get(product)));
			}
		}
	}
	
	/*
	 * Given the amount of available labor, return a number indicating the maximum amount producible
	 * with the specified labor.
	 */
	public static int maxAmountProducible(ProjectType type, String target, LaborPool availableLabor) {
		switch(type) {
		case NATURALISM:
			// In this case, target should be a wild species.
			WildSpecies targ = EcologyReader.getWildSpecies(target);
			
			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			LaborPool requiredLabor = new LaborPool(0);
			requiredLabor.set(LaborPool.TYPE_NATURALISM, 1.0 * targ.power);
			
			return availableLabor.factor(requiredLabor);
			
		case ENGINEERING:
			break;
		}
		
		return 0;
	}
}