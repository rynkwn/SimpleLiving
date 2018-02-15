package ecology;

import data.Macronutrient;
import util.RandomUtils;
import world.BigTile;
import world.World;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * A class that simulates the ecology of a given tile.
 */

public class EcoTile {
	public static final int TURNS_BETWEEN_ITERATIONS = 1;
	public static final int LOWER_HUNTING_BOUND = 50; // A species can't eat another species to lower than this pop.
	public static final int MAX_SPECIES_NUMBER = 50000; 
	
	// Being ill suited for the local weather always removes at least this many pops.
	public static final int BAD_TEMPERATURE_MODIFIER = -20;
	
	// Migration related constants.
	public static final double MIGRATION_CHANCE = .3;
	public static final double MIGRATION_POP_PERCENTAGE = .001;
	public static final int MIGRATION_NON_PLANT_MULTIPLIER = 100; // Non-plants should be more mobile.
	
	// Carrying capacity for predators are divided by this factor.
	public static final int PREDATION_INEFFICIENCY_FACTOR = 10;
	
	public World world; // The world we live in.
	public int xCoord; // Coordinates of the tile.
	public int yCoord;
	public BigTile localTile;
	
	public HashMap<String, Integer> species;
	
	public int turnsTillPopIteration = TURNS_BETWEEN_ITERATIONS;
	
	/*
	 * When first adding an EcoTile, I should add a sufficiently large population of every species,
	 * so that the world is properly populated.
	 */
	public EcoTile(World world, int x, int y, BigTile tile) {
		this.world = world;
		xCoord = x;
		yCoord = y;
		localTile = tile;
		
		species = new HashMap<String, Integer>();
		for(String name : EcologyReader.getAllSpeciesNames())
			species.put(name, 200 / EcologyReader.getWildSpecies(name).power);
		
	}
	
	public EcoTile(World world, int x, int y, BigTile tile, HashMap<String, Integer> speciesMap) {
		this.world = world;
		xCoord = x;
		yCoord = y;
		localTile = tile;
		species = speciesMap;
	}
	
	public void addPop(String specName, int pop) {
		if(species.containsKey(specName)) {
			int curPop = species.get(specName);
			updatePopulation(specName, curPop + pop);
		} else {
			species.put(specName, pop);
		}
	}
	
	// Add some members of a species to this tile.
	public void addPops(HashMap<String, Integer> pops) {
		for(String specName : pops.keySet()) {
			if(species.containsKey(specName)) {
				int curPop = species.get(specName);
				updatePopulation(specName, curPop + pops.get(specName));
			} else {
				species.put(specName, pops.get(specName));
			}
		}
	}
	
	/*
	 * Subtract some members of a local population. If we're subtracting more
	 * than currently exist, we remove the entire population.
	 */
	public void subPop(String specName, int pop) {
		if(species.containsKey(specName)) {
			int curPop = species.get(specName);
			int newPop = Math.max(0, curPop - pop);
			updatePopulation(specName, newPop);
		}
	}

	/*
	 * Remove as many members of a species as possible up till pop, 
	 * and add death nutrients back into the local tile.
	 */
	public void killPop(String specName, int pop) {
		if(species.containsKey(specName)) {
			int curPop = species.get(specName);
			int newPop = Math.max(0, curPop - pop);
			int diffPop = newPop - curPop;

			subtractFromEnvironment(EcologyReader.getWildSpecies(specName).deathNutrition, diffPop);
			updatePopulation(specName, newPop);
		}
	}
	
	// Subtract differential * nutrientModel from the local environment.
	public void subtractFromEnvironment(Macronutrient nutrientModel, int differential) {
		localTile.subtractNutrients(nutrientModel, differential);
	}
	
	// TODO: In some places, the differential is negative, so we're actually adding to
	// the local environmental bio resources. This is intentional, but it's also a bit unclear.
	// Let's just make an addToEnvironment method, and then take the absolute value of the differential
	// in those cases.
	public void iterateSpeciesPopulations() {
		Macronutrient localNutrients = localTile.soilComposition;
		ArrayList<String> deadSpecies = new ArrayList<String>(); 
		
		for (String speciesName : species.keySet()) {
			WildSpecies spec = EcologyReader.getWildSpecies(speciesName);
			
			int curNumber = species.get(speciesName);
			int finalPopNumber = curNumber;
			
			// Otherwise, we modify their population.
			double reprodRate = spec.reproductionRate;
			Macronutrient nutr = spec.nutrientRequirements;
			Macronutrient turnNutr = spec.turnNutrition;
			Macronutrient deathNutr = spec.deathNutrition;
			
			// Every species passively adds some small number of nutrients
			// to the local tile.
			localNutrients.add(turnNutr, curNumber);
			
			if(spec.consumption.equalsIgnoreCase("photosynthetic")) {

				// Calculate the final pop number as a consequence of this photosynthetic's turn.
				finalPopNumber = photosyntheticTurn(spec, localNutrients, deathNutr, nutr, curNumber, reprodRate);

			} else {
				
				// Calculate the final pop number as a consequence of this heterotroph's turn.
				finalPopNumber = heterotrophTurn(spec, spec.name, nutr, deathNutr, curNumber, reprodRate);
			}
			
			// Calculate whether or not some species members migrate.
			if(RandomUtils.checkProb(100, (int) (100 * MIGRATION_CHANCE))) {
				int migrationNumber = 0;
				
				// Non-plants should be more mobile.
				if(!spec.type.equals(WildSpecies.TYPE_PLANT)) {
					migrationNumber = (int) (MIGRATION_NON_PLANT_MULTIPLIER * finalPopNumber * MIGRATION_POP_PERCENTAGE);
				} else {
					migrationNumber = (int) (finalPopNumber * MIGRATION_POP_PERCENTAGE);
				}
				
				if(migrationNumber > 0) {
					finalPopNumber -= migrationNumber;
					migratePopulation(speciesName, migrationNumber);
				}
			}
			
			// If the species is extinct in this tile, remove them.
			if(finalPopNumber <= 0) {
				deadSpecies.add(speciesName);
			} else {
				updatePopulation(speciesName, finalPopNumber);
			}
		}
		
		for(String deadSpeciesName : deadSpecies) {
			species.remove(deadSpeciesName);
		}
	}
	
	/*
	 * Adds "pop" of "speciesName" to a random adjacent tile.
	 */
	public void migratePopulation(String speciesName, int pop) {
		int newX = RandomUtils.getInt(xCoord - 1, xCoord + 1);
		int newY = RandomUtils.getInt(yCoord - 1, yCoord + 1);
		
		boolean satisfied = (newX != xCoord || newY != yCoord) && world.inBounds(newX, newY);
		while(!satisfied) {
			newX = RandomUtils.getInt(xCoord - 1, xCoord + 1);
			newY = RandomUtils.getInt(yCoord - 1, yCoord + 1);
			
			satisfied = (newX != xCoord || newY != yCoord) && world.inBounds(newX, newY);
		}
		
		world.addWildSpeciesToTile(newX, newY, speciesName, pop);
	}

	/*
	 * Perform the normal turn activities of a photosynthetic entity.
	 * Returns the final population number as a consequence of this turn.
	 */
	private int photosyntheticTurn(WildSpecies spec,
								   Macronutrient localNutrients,
								   Macronutrient deathNutr,
								   Macronutrient nutrientRequirements,
								   int curNumber,
								   double reprodRate
								   ) {
		int carryingCapacity = (int) localNutrients.factor(nutrientRequirements);
		int differential = (carryingCapacity - curNumber);
		differential = (int) (differential * reprodRate);
		
		// If the species can't survive in the current temp... die out.
		if(!spec.temperatureTolerance.numberInRange(localTile.temperature)) {
			differential = -1 * curNumber;
		}

		if(differential < 0) {
			subtractFromEnvironment(deathNutr, differential);
		}
		
		return curNumber + differential;
	}

	/*
	 * Pass a turn for an entity that eats either plants or animals.
	 */
	private int heterotrophTurn(WildSpecies spec,
								String speciesName,
								Macronutrient nutrientRequirements,
								Macronutrient deathNutr,
								int curNumber,
								double reprodRate
								) {
		double consumptionPerCreature = nutrientRequirements.nutrientSum();
		double desiredConsumption = consumptionPerCreature * curNumber;
		
		// Calculate carrying capacity, 
		int carryingCapacity = carryingCapacity(spec, speciesName, curNumber, consumptionPerCreature, spec.power);
		carryingCapacity /= PREDATION_INEFFICIENCY_FACTOR;
		
		eatOtherSpecies(spec, 
				speciesName, 
				desiredConsumption, 
				curNumber, 
				spec.power);
		
		int differential = carryingCapacity - curNumber;
		
		// If the species can't survive in the current temp, rapidly reduce in number..
		if(!spec.temperatureTolerance.numberInRange(localTile.temperature)) {
			differential = (-1 * curNumber / 2) + BAD_TEMPERATURE_MODIFIER;
		}
		
		// If we're growing, we should grow slowly. We fall quickly.
		if(differential > 0)
			differential = (int) (differential * reprodRate);
		
		// Help out the species a bit if it's not growing otherwise.
		if (differential == 0) {
			differential = 1;
		} else if (differential < 0) {
			
			// Return resources to tile.
			subtractFromEnvironment(deathNutr, differential);
		}
		
		return curNumber + differential;
	}
	
	// Update the population up to our maximum allowed number.
	public void updatePopulation(String speciesName, int newPopNumber) {
		species.put(speciesName, Math.min(newPopNumber, MAX_SPECIES_NUMBER));
	}
	
	/*
	 * Look at all the prey species in the tile, and calculate what the carrying capacity 
	 * of this particular species is. Only meant for non-photosynthetic creatures.
	 */
	public int carryingCapacity(WildSpecies predator, 
			String speciesName, 
			int curNumber,
			double consumptionPerCreature,
			int power) {
		String targetType = predator.getPreySpeciesType();
		
		double totalPreyMass = 0;
		
		for(String specName : species.keySet()) {
			WildSpecies wildSpec = EcologyReader.getWildSpecies(specName);
			if(! specName.equals(speciesName) && wildSpec.type.equals(targetType)) {
				double preyMass = wildSpec.deathNutrition.nutrientSum();
				
				int preyNumber = species.get(specName);
				//int numEatable = (power / wildSpec.power) * curNumber;
				int numEatable = preyNumber;
				int maxEatable = Math.max(preyNumber - LOWER_HUNTING_BOUND, 0);
				
				totalPreyMass += (Math.min(maxEatable, numEatable) * preyMass);
			}
		}
		
		//System.out.println(totalPreyMass + " - " + consumptionPerCreature + " - " + (totalPreyMass/consumptionPerCreature));
		return (int) (totalPreyMass / consumptionPerCreature);
	}
	
	/*
	 * Go through all prey species and eat what we can, up to what we want.
	 */
	public void eatOtherSpecies(WildSpecies predator,
			String speciesName, 
			double consumptionGoal, 
			int curNumber, 
			int power
			) {
		String targetType = predator.getPreySpeciesType();
		
		double massLeftToConsume = consumptionGoal;
		
		for(String specName : species.keySet()) {
			WildSpecies wildSpec = EcologyReader.getWildSpecies(specName);
			if(! specName.equals(speciesName) && wildSpec.type.equals(targetType)) {
				double preyMass = wildSpec.deathNutrition.nutrientSum();
				
				int preyNumber = species.get(specName);
				int numDesired = (int) (massLeftToConsume / preyMass);
				int numEatable = power / wildSpec.power * curNumber;
				int maxEatable = Math.max(preyNumber - LOWER_HUNTING_BOUND, 0);
				
				int numEaten = Math.min(numDesired, Math.min(numEatable, maxEatable));
				species.put(specName, preyNumber - numEaten);
			}
		}
	}
	
	public void turn() {
		if(turnsTillPopIteration == 0) {
			turnsTillPopIteration = TURNS_BETWEEN_ITERATIONS;
			iterateSpeciesPopulations();
		} else {
			turnsTillPopIteration --;
		}
	}
	
	/*
	 * Returns a HashMap representation of local wildlife.
	 */
	public HashMap<String, Integer> localWildlife() {
		return species;
	}
	
	public String toString() {
		return species.toString();
	}
}
