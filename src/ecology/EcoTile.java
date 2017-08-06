package ecology;

import data.Macronutrient;
import world.BigTile;

import java.util.HashMap;

/*
 * A class that simulates the ecology of a given tile.
 */

public class EcoTile {
	public static final int TURNS_BETWEEN_ITERATIONS = 5;
	public static final int LOWER_HUNTING_BOUND = 200; // A species can't eat another species to lower than this pop.
	
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
			species.put(name, 100);
	}
	
	public EcoTile(BigTile tile, HashMap<String, Integer> speciesMap) {
		localTile = tile;
		species = speciesMap;
	}
	
	public void iterateSpeciesPopulations() {
		Macronutrient localNutrients = localTile.soilComposition;
		
		for (String speciesName : species.keySet()) {
			WildSpecies spec = EcologyReader.getWildSpecies(speciesName);
			
			int curNumber = species.get(speciesName);
			
			if(curNumber == 0) {
				// If the species is extinct in this tile, remove them.
				species.remove(speciesName);
				
			} else {
				
				// Otherwise, we modify their population.
				double reprodRate = spec.reproductionRate;
				Macronutrient nutr = spec.nutrientRequirements;
				
				if(spec.consumption.equalsIgnoreCase("photosynthetic")) {					
					int differential = (int) (curNumber * reprodRate);
					
					int actualDifferential = Math.min(localNutrients.factor(nutr), differential);
					
					for(String nutrient : Macronutrient.nutrientList()) {
						localNutrients.subtract(nutrient, actualDifferential * nutr.get(nutrient));
					}
					
					species.put(speciesName, curNumber + actualDifferential);
				} else {
					double desiredConsumption = nutr.nutrientSum() * curNumber;
					
					double unsatisfiedConsumption = eatOtherSpecies(speciesName, 
							spec.consumption,
							desiredConsumption,
							species.get(speciesName),
							spec.power);
					
					// Note: This is totally bad. Rethink and REDO this.
					// This model just has monotonic growth.
					double consumptionModifier = (1 - unsatisfiedConsumption / desiredConsumption);
					int differential = (int) (curNumber * (spec.reproductionRate * consumptionModifier));
					species.put(speciesName, curNumber + differential);
				}
			}
		}
	}
	
	/*
	 * Look at all other species in the tile, and prey upon the weaker ones.
	 * We eat as many as we can, 
	 */
	public double eatOtherSpecies(String speciesName, 
			String consumptionType, 
			double consumptionGoal, 
			int curNumber, 
			int power
			) {
		String targetType = "";
		if(consumptionType.equals("Herbivorous")) {
			targetType = "Plant";
		}
		
		double massLeftToConsume = consumptionGoal;
		
		for(String specName : species.keySet()) {
			WildSpecies wildSpec = EcologyReader.getWildSpecies(specName);
			if(! specName.equals(speciesName) && wildSpec.type.equals(targetType)) {
				double preyMass = wildSpec.nutrientRequirements.nutrientSum();
				
				int preyNumber = species.get(specName);
				int numDesired = (int) (massLeftToConsume / preyMass);
				int numEatable = power / wildSpec.power * curNumber;
				int maxEatable = preyNumber - LOWER_HUNTING_BOUND;
				
				int numEaten = Math.min(numDesired, Math.min(numEatable, maxEatable));
				massLeftToConsume -= (preyMass * numEaten);
				species.put(specName, preyNumber - numEaten);
			}
		}
		
		return massLeftToConsume;
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
