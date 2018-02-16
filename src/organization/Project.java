package organization;

import java.util.ArrayList;
import java.util.HashMap;

import item.AbstractItem;
import item.Item;
import item.ItemsReader;
import data.LaborPool;
import data.Macronutrient;
import data.MineralType;
import ecology.EcoTile;
import ecology.EcologyReader;
import ecology.WildSpecies;
import world.BigTile;

/*
 * A project is some economic activity in the local tile. This may be to build some number
 * of doohickies, to gather some amount of the local organic or mineral resources, or otherwise.
 *
 * The project is associated
 */
public class Project {

	// Constants

	// Depending on the job, they may incur an inefficiency modifier as a
	// consequence of doing them manually.
	public static final double MANUAL_INEFFICIENCY_MODIFIER = 5.0;

	public static final double MINING_LABOR_PER_UNIT = 10.0;

	// What percentage of invested resources/inputs are kept after canceling a project?
	public static final double CANCEL_KEEP_PERCENTAGE = 1;

	// Extra information keys
	public static final String NAT_COST_PER_UNIT = "naturalLaborCostPerUnit";


	// Attributes

	public boolean valid;
	public boolean manual; // Are you doing this by hand?
	
	// Whether or not this project is supported by some piece of infrastructure
	// in the group's inventory. If this is False, and the project isn't Manual,
	// then the project is invalid (presumably because it was started by
	// some held piece of infrastructure, which was then dropped).
	public boolean infraSupported;

	public ProjectType type;
	public String target; // The sort of thing you're building/capturing.
	public int number; // How much of that thing you're building.
	public HashMap<String, String> extraInformation;

	// Project Inputs
	public LaborPool laborRequirements;
	public LaborPool laborStore;

	public HashMap<String, Integer> rawMatNeed;
	public Macronutrient tileResNeed;
	
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
		valid = true;
		manual = true;
		infraSupported = false;

		this.type = type;
		
		this.grp = grp;
		this.target = target;
		this.number = number;

		extraInformation = new HashMap<String, String>();
		products = new HashMap<AbstractItem, Integer>();
		
		laborStore = new LaborPool(0);
		rawMatNeed = new HashMap<String, Integer>();
		tileResNeed = new Macronutrient(0);

		setUpJob();
	}

	/*
	 * Set up the labor requirements for a job based on project information, and
	 * set up some additional properties of the job if relevant.
	 */
	public void setUpJob() {

		// First, get the labor needed per unit.
		// Then, based on project type, fill in any extra information.
		// Then, multiply by the number of times we expect to do it.
		LaborPool requiredLabor = Project.getLaborRequirementPerUnit(type, target, manual);

		WildSpecies targ;

		switch(type) {
			case GATHER:
				// In this case, target should be a wild species.
				targ = EcologyReader.getWildSpecies(target);

				for(String harvest : targ.harvestResult.keySet()) {
					products.put(ItemsReader.getAbstractItem(harvest), targ.harvestResult.get(harvest) * number);
				}

			case KILL:
				extraInformation.put(NAT_COST_PER_UNIT, "" + requiredLabor.get(LaborPool.TYPE_NATURALISM));
				break;

			case MINE:
				if(target.equalsIgnoreCase("COPPER")) {
					products.put(ItemsReader.getAbstractItem("Copper Ore"), (int) (number * grp.residentTile.getAccessibility(MineralType.COPPER)));
				} else if(target.equalsIgnoreCase("STONE")) {
					products.put(ItemsReader.getAbstractItem("Stone"), (int) (number * grp.residentTile.getAccessibility(MineralType.STONE)));
				}
				break;

			case MANUFACTURE:
				// Make any and all items!
				// TODO: Fill this out!

				break;
			default:
				break;
		}

		requiredLabor.modify(number);

		laborRequirements = requiredLabor;
	}

	/*
	 * Return the labor requirements per unit
	 */
	public static LaborPool getLaborRequirementPerUnit(ProjectType ptype, 
													   String targ,
													   boolean isManual
													   ) {
		WildSpecies specTarg;
		LaborPool requiredLabor = new LaborPool(0);
		
		switch(ptype) {
		case GATHER:
			// In this case, target should be a wild species.
			specTarg = EcologyReader.getWildSpecies(targ);

			// Determine how much labor is needed to capture the specified number of
			// creatures/plants.
			requiredLabor.set(LaborPool.TYPE_NATURALISM, 1.0 * specTarg.power);

			break;
		case KILL:
			// Identical to GATHER, however, twice as effective as GATHER as you're
			// not processing anything.
			specTarg = EcologyReader.getWildSpecies(targ);
			
			// Determine how much labor is needed to kill the specified number of
			// creatures/plants.
			requiredLabor.set(LaborPool.TYPE_NATURALISM, .5 * specTarg.power);
			
			break;
		case MINE:
			requiredLabor.set(LaborPool.TYPE_MINING, MINING_LABOR_PER_UNIT);
			
			break;

		case MANUFACTURE:
			// TODO: Figure out and set up per unit labor requirements!

			break;
		default:
			break;
		}

		if(isManual) {
			requiredLabor.modify(MANUAL_INEFFICIENCY_MODIFIER);
		}

		return requiredLabor;
	}

	/* 
	 * Cancels a project, and returns some percentage of invested resources/inputs
	 * back by dropping the items into the local tile.
	 */ 
	public void cancel() {
		BigTile localTile = grp.residentTile;

		// Drop any macronutrient usage into the local tile.
		tileResNeed.modify(CANCEL_KEEP_PERCENTAGE);
		localTile.addNutrients(tileResNeed);

		// Drop inputs into the local tile.
		for(String input : rawMatNeed.keySet()) {
			int keptInput = (int) (rawMatNeed.get(input) * CANCEL_KEEP_PERCENTAGE);
			localTile.receiveItem(ItemsReader.getAbstractItem(input).makeItem(keptInput));
		}

		valid = false;
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
				
				double natLaborPerUnit = Double.parseDouble(extraInformation.get(NAT_COST_PER_UNIT));
				
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
				
				double natLaborPerUnit = Double.parseDouble(extraInformation.get(NAT_COST_PER_UNIT));
				
				int numCanHarvest = (int) (natLabor / natLaborPerUnit);
				int numAvailable = localSpecies.get(target);
				
				int numHarvested = Math.min(numCanHarvest, numAvailable);

				grp.ecoTile.killPop(target, numHarvested);
				
				addedLabor.set(LaborPool.TYPE_NATURALISM, numHarvested * natLaborPerUnit);
			}
			break;

		case MINE:
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
	public static int maxAmountProducible(ProjectType type, String target, LaborPool availableLabor, boolean isManual) {
		
		LaborPool requiredLabor = getLaborRequirementPerUnit(type, target, isManual);
		
		if(!requiredLabor.isZero()) {
			return availableLabor.factor(requiredLabor);
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