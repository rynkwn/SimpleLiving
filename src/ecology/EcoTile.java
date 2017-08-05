package ecology;

import data.Macronutrient;
import world.BigTile;

import java.util.HashMap;

/*
 * A class that simulates the ecology of a given tile.
 */

public class EcoTile {
	public static final int TURNS_BETWEEN_ITERATIONS = 5;
	
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
			WildSpecies spec = EcologyReader.getEcologyObject(speciesName);
			
			int curNumber = species.get(speciesName);
			
			if(curNumber == 0) {
				// If the species is extinct in this tile, remove them.
				species.remove(speciesName);
				
			} else {
				
				// Otherwise, we modify their population.
				
				double reprodRate = spec.reproductionRate;
				
				if(spec.consumption.equalsIgnoreCase("photosynthetic")) {
					Macronutrient nutr = spec.nutrientRequirements;
					
					int differential = (int) (curNumber * reprodRate);
					
					int actualDifferential = Math.min(localNutrients.factor(nutr), differential);
					
					for(String nutrient : Macronutrient.nutrientList()) {
						localNutrients.subtract(nutrient, actualDifferential * nutr.get(nutrient));
					}
					
					species.put(speciesName, curNumber + actualDifferential);
				} else if(spec.consumption.equalsIgnoreCase("herbivorous")) {
					
				}
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
