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
 *
 * The project is associated
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


	// EXTRA INFORMATION KEYS
	public static final String NAT_COST_PER_UNIT = "naturalLaborCostPerUnit";

	
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
		valid = true;
		this.type = type;
		
		laborStore = new LaborPool(0);
		
		this.grp = grp;
		extraInformation = new HashMap<String, String>();
		products = new HashMap<AbstractItem, Integer>();
		
		// If relevant, a WildSpecies target.
		WildSpecies targ;
		
		switch(type) {
		case GATHER:
			// In this case, target should be a wild species.
			targ = EcologyReader.getWildSpecies(target);
			
			for(String harvest : targ.harvestResult.keySet()) {
				products.put(ItemsReader.getAbstractItem(harvest), targ.harvestResult.get(harvest) * number);
			}
			
			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			laborRequirements = new LaborPool(0);
			laborRequirements.set(LaborPool.TYPE_NATURALISM, 1.0 * targ.power * number);
			
			extraInformation.put(NAT_COST_PER_UNIT, "" + targ.power);
			
			this.target = target;
			this.number = number;
			
			break;
		case KILL:
			// Similar to GATHER. But twice as effective at removing local wildlife due to the lack
			// of any processing that needs to occur.
			targ = EcologyReader.getWildSpecies(target);
			
			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			laborRequirements = new LaborPool(0);
			laborRequirements.set(LaborPool.TYPE_NATURALISM, .5 * targ.power * number);
			
			extraInformation.put(NAT_COST_PER_UNIT, "" + (.5 * targ.power));
			
			this.target = target;
			this.number = number;
			break;
		default:
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
		
		HashMap<String, Integer> localSpecies;
		
		switch(type) {
		case GATHER:
			// We're extracting something from the natural environment.
			localSpecies = grp.ecoTile.localWildlife();
			
			if(localSpecies.containsKey(target)) {
				double natLabor = addedLabor.get(LaborPool.TYPE_NATURALISM);
				
				int natLaborPerUnit = Integer.parseInt(extraInformation.get(NAT_COST_PER_UNIT));
				
				int numCanHarvest = (int) (natLabor / natLaborPerUnit);
				int numAvailable = localSpecies.get(target);
				
				int numHarvested = Math.min(numCanHarvest, numAvailable);
				grp.ecoTile.subPop(target, numHarvested);
				
				addedLabor.set(LaborPool.TYPE_NATURALISM, numHarvested * natLaborPerUnit);
			}
			
			break;
		case KILL:
			// We're literally just hunting living things in the local environment and leaving the bodies
			// where they fall. You monster.
			localSpecies = grp.ecoTile.localWildlife();
			
			if(localSpecies.containsKey(target)) {
				double natLabor = addedLabor.get(LaborPool.TYPE_NATURALISM);
				
				int natLaborPerUnit = Integer.parseInt(extraInformation.get(NAT_COST_PER_UNIT));
				
				int numCanHarvest = (int) (natLabor / natLaborPerUnit);
				int numAvailable = localSpecies.get(target);
				
				int numHarvested = Math.min(numCanHarvest, numAvailable);

				grp.ecoTile.killPop(target, numHarvested);
				
				addedLabor.set(LaborPool.TYPE_NATURALISM, numHarvested * natLaborPerUnit);
			}
			break;
		default:
			break;
		}
		
		laborStore.add(addedLabor);
		
		if(laborStore.greaterThanOrEqual(laborRequirements)) {
			for(AbstractItem product : products.keySet()) {
				grp.addItem(product.makeItem(products.get(product)));
			}
			
			valid = false;
		}
	}
	
	/*
	 * Given the amount of available labor, return a number indicating the maximum amount producible
	 * with the specified labor.
	 */
	public static int maxAmountProducible(ProjectType type, String target, LaborPool availableLabor) {
		
		WildSpecies targ;
		LaborPool requiredLabor;
		
		switch(type) {
		case GATHER:
			// In this case, target should be a wild species.
			targ = EcologyReader.getWildSpecies(target);
			
			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			requiredLabor = new LaborPool(0);
			requiredLabor.set(LaborPool.TYPE_NATURALISM, 1.0 * targ.power);
			
			return availableLabor.factor(requiredLabor);
			
		case KILL:
			// Identical to GATHER, however, twice as effective as GATHER as you're
			// not processing anything.
			targ = EcologyReader.getWildSpecies(target);
			
			// Determine how much labor is needed to kill the specified number of
			// creatures/plants.
			requiredLabor = new LaborPool(0);
			requiredLabor.set(LaborPool.TYPE_NATURALISM, .5 * targ.power);
			
			return availableLabor.factor(requiredLabor);
			
		default:
			break;
		}
		
		return 0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Type: " + type + "\n");
		sb.append("Target: " + target + "\n");
		sb.append("Number: " + number + "\n");
		sb.append("Extra Information: " + extraInformation + "\n");
		sb.append("laborRequirements: " + laborRequirements + "\n");
		sb.append("laborStore: " + laborStore + "\n");
		
		return sb.toString();
	}
}