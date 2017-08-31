package ecology;

import data.Macronutrient;
import world.BigTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 * A class that simulates the ecology of a given tile.
 */

public class EcoTile {
	public static final int TURNS_BETWEEN_ITERATIONS = 1;
	public static final int LOWER_HUNTING_BOUND = 50; // A species can't eat another species to lower than this pop.
	public static final int MAX_SPECIES_NUMBER = 50000;
	
	public BigTile localTile;
	
	public HashMap<String, Integer> species;
	
	public int turnsTillPopIteration = TURNS_BETWEEN_ITERATIONS;
	
	/*
	 * When first adding an EcoTile, I should add a sufficiently large population of every species,
	 * so that the world is properly populated.
	 */
	public EcoTile(BigTile tile) {
		localTile = tile;
		
		species = new HashMap<String, Integer>();
		for(String name : EcologyReader.getAllSpeciesNames())
			species.put(name, 200 / EcologyReader.getWildSpecies(name).power);
	}
	
	public EcoTile(BigTile tile, HashMap<String, Integer> speciesMap) {
		localTile = tile;
		species = speciesMap;
	}
	
	// Subtract differential * nutrientModel from the local environment.
	public void subtractFromEnvironment(Macronutrient nutrientModel, int differential) {
		for(String nutrient : Macronutrient.nutrientList()) {
			localTile.soilComposition.subtract(nutrient, differential * nutrientModel.get(nutrient));
		}
	}
	
	public void iterateSpeciesPopulations() {
		Macronutrient localNutrients = localTile.soilComposition;
		ArrayList<String> deadSpecies = new ArrayList<String>(); 
		Random rand = new Random();
		
		for (String speciesName : species.keySet()) {
			WildSpecies spec = EcologyReader.getWildSpecies(speciesName);
			
			int curNumber = species.get(speciesName);
			
			if(curNumber <= 0) {
				// If the species is extinct in this tile, remove them.
				deadSpecies.add(speciesName);
				
			} else {
				
				// Otherwise, we modify their population.
				double reprodRate = spec.reproductionRate;
				Macronutrient nutr = spec.nutrientRequirements;
				Macronutrient turnNutr = spec.turnNutrition;
				Macronutrient deathNutr = spec.deathNutrition;
				
				// Every species passively adds some small number of nutrients
				// to the local tile.
				localNutrients.add(turnNutr, curNumber);
				
				if(spec.consumption.equalsIgnoreCase("photosynthetic")) {
					
					int carryingCapacity = (int) localNutrients.factor(nutr) * 3;
					int differential = (carryingCapacity - curNumber);
					differential = (int) (differential * reprodRate);
					
					int newSpeciesNumber = Math.min(curNumber + differential, MAX_SPECIES_NUMBER);
					
					if(differential < 0) {
						subtractFromEnvironment(deathNutr, differential);
					}
					
					species.put(speciesName, newSpeciesNumber);
				} else {
					double desiredConsumption = nutr.nutrientSum() * curNumber;
					
					int carryingCapacity = carryingCapacity(spec, speciesName, curNumber, desiredConsumption, spec.power);
					
					eatOtherSpecies(spec, 
							speciesName, 
							desiredConsumption, 
							curNumber, 
							spec.power);
					
					int differential = carryingCapacity - curNumber;
					differential = (int) (differential * reprodRate);
					
					// Help out the species a bit if it's not growing otherwise.
					if (differential == 0) {
						differential = 1;
					} else if (differential < 0) {
						
						// Return resources to tile.
						subtractFromEnvironment(deathNutr, differential);
					}
					species.put(speciesName, curNumber + differential);
					
				}
			}
		}
		
		for(String deadSpeciesName : deadSpecies) {
			species.remove(deadSpeciesName);
		}
	}
	
	/*
	 * Look at all the prey species in the tile, and calculate what the carrying capacity 
	 * of this particular species is.
	 */
	public int carryingCapacity(WildSpecies predator, 
			String speciesName, 
			int curNumber,
			double consumptionGoal,
			int power) {
		String targetType = predator.getPreySpeciesType();
		
		double totalPreyMass = 0;
		
		for(String specName : species.keySet()) {
			WildSpecies wildSpec = EcologyReader.getWildSpecies(specName);
			if(! specName.equals(speciesName) && wildSpec.type.equals(targetType)) {
				double preyMass = wildSpec.deathNutrition.nutrientSum();
				
				int preyNumber = species.get(specName);
				int numEatable = (power / wildSpec.power) * curNumber;
				int maxEatable = Math.max(preyNumber - LOWER_HUNTING_BOUND, 0);
				
				totalPreyMass += (Math.min(maxEatable, numEatable) * preyMass);
			}
		}
		return (int) (totalPreyMass / consumptionGoal) * curNumber;
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
	
	public String toString() {
		return species.toString();
	}
}
