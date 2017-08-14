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
				Macronutrient deathNutr = spec.deathNutrition;
				
				if(spec.consumption.equalsIgnoreCase("photosynthetic")) {
					// TODO: Two big issues:
					// 1) Still no satisfying way to model plant population dynamics.
					// Things still aren't moving in a natural way, nor is the tile
					// being enriched.
					// 2) Animals, when they lose numbers, are not releasing biological
					// resources into their local tile.
					
					int carryingCapacity = (int) localNutrients.factor(nutr) * 2;
					int differential = (carryingCapacity - curNumber);
					differential = (int) (differential * reprodRate);
					
					System.out.println(differential);
					
					if(differential > 0) {
						for(String nutrient : Macronutrient.nutrientList()) {
							localNutrients.subtract(nutrient, differential * nutr.get(nutrient));
						}
					} else {
						for(String nutrient : Macronutrient.nutrientList()) {
							localNutrients.add(nutrient, (-1) * differential * deathNutr.get(nutrient));
						}
					}
					
					species.put(speciesName, curNumber + differential);
				} else {
					double desiredConsumption = nutr.nutrientSum() * curNumber;
					
					double unsatisfiedConsumption = eatOtherSpecies(speciesName, 
							spec.consumption,
							desiredConsumption,
							species.get(speciesName),
							spec.power);

					double consumptionModifier = (unsatisfiedConsumption / desiredConsumption);
					int differential = (int) (curNumber * (spec.reproductionRate - consumptionModifier));
					if (differential == 0) {
						differential = 1;
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
				double preyMass = wildSpec.deathNutrition.nutrientSum();
				
				int preyNumber = species.get(specName);
				int numDesired = (int) (massLeftToConsume / preyMass);
				int numEatable = power / wildSpec.power * curNumber;
				int maxEatable = Math.max(preyNumber - LOWER_HUNTING_BOUND, 0);
				
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
