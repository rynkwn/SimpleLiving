package ecology;

import data.Macronutrient;
import world.BigTile;

import java.util.HashMap;

/*
 * A class that simulates the ecology of a given tile.
 */

public class EcoTile {
	public BigTile localTile;
	
	public HashMap<String, Integer> species;
	
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
	
	public void turn() {
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
				Macronutrient nutr = spec.nutrientRequirements;
				
				int differential = (int) (curNumber * reprodRate);
				if(differential == 0) {
					differential = 1;
				}
				
				if (differential > 0) {
					
					// Calculate the cost the new species members produce.
					HashMap<String, Double> cost = new HashMap<String, Double>();
					for(String nutrient : Macronutrient.nutrientList()) {
						cost.put(nutrient, nutr.get(nutrient));
					}
					
					// Grow as much as the local tile will sustain, or up till the differential.
					int actualDifferential = Math.min(localNutrients.factor(cost), differential);
					
					for(String nutrient : Macronutrient.nutrientList()) {
						localNutrients.subtract(nutrient, actualDifferential * nutr.get(nutrient));
					}
					
					species.put(speciesName, curNumber + actualDifferential);
					
				} else {
					for(String nutrient : Macronutrient.nutrientList()) {
						localNutrients.add(nutrient, differential * nutr.get(nutrient));
					}
					
					species.put(speciesName, curNumber + differential);
				}
			}
		}
	}
	
	public String toString() {
		return species.toString();
	}
}
