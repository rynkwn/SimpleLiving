package world;

import java.util.*;

import data.Macronutrient;
import organization.*;
import util.*;

/*
 * A big region in the world. Basically a single "map" consisting of lots of smaller tiles.
 */

public class BigTile {
	
	public static double maxValue = 10000;
	public static double initValue = 3000;
	
	// Summary levels.
	public Macronutrient soilComposition;
	public int temperature;
	
	// Rates
	public double waterRate;
	
	// Also need weather.
	
	public HashSet<Group> residentGroups;
	
	// For initial tests.
	public BigTile() {
		soilComposition = new Macronutrient(initValue);
		residentGroups = new HashSet<Group>();
	}
	
	public BigTile(double waterRate, int temperature) {
		residentGroups = new HashSet<Group>();
		soilComposition = new Macronutrient(initValue);
		
		this.waterRate = waterRate;
		soilComposition.set("water", maxValue * waterRate);
		
		this.temperature = temperature;
	}
	
	public void addGroup(Group grp) {
		residentGroups.add(grp);
	}
	
	public void removeGroup(Group grp) {
		residentGroups.remove(grp);
	}
	
	public boolean containsGroup(Group grp) {
		return residentGroups.contains(grp);
	}
	
	/*
	 * Over time, this tile will gradually regenerate its properties.
	 */
	public void turn(int numTurnsPassed) {
		iterateSoil();
	}
	
	/*
	 * Update soil conditions. This involves accreting biological resources and
	 * also simulating erosion. This creates an effective cap on free resources
	 * in a tile.
	 */
	public void iterateSoil() {
		Random rand = new Random();
		
		ArrayList<String> nutrientsList = Macronutrient.nutrientList();
		
		// Remove any special biological resources that have their own rules.
		nutrientsList.remove("water");
		
		for (String nutrient : nutrientsList) {
			double curVal = soilComposition.get(nutrient);
			double differential = maxValue - curVal;
			differential /= 250;
			differential *= (1.5 * rand.nextDouble());
			
			soilComposition.set(nutrient, curVal + differential);
		}
		
		if(soilComposition.water < waterRate * maxValue) {
			double modifier = 1.5 * rand.nextDouble();
			soilComposition.add("water", (waterRate * maxValue * modifier) / 104);
		}
	}
	
	/*
	 * Returns the minimum ratio of ELEMENT / MAX_VALUE
	 */
	public double minRatio() {
		ArrayList<String> nutrients = Macronutrient.nutrientList();
		
		double[] ratios = new double[nutrients.size()];
		
		for(int i = 0; i < nutrients.size(); i++) {
			ratios[i] = soilComposition.get(nutrients.get(i)) / maxValue;
		}
		
		return MathUtils.min(ratios);
	}
	
	/*
	 * Tries to take some raw materials from the BigTile. If there are insufficient resources, we keep everything
	 * in ratio, and return the maximal amount under that ratio up to the amounts requested.
	 * 
	 * NOTE: DON'T LIKE THIS METHOD. YOU SHOULD TAKE AS MUCH AS YOU CAN. EVEN IF SOME THINGS ARE MISSING, YOU SHOULD
	 * "FILL UP" ON OTHER MATERIALS. HOWEVER. WE'LL DO THIS FOR NOW.
	 */
	public double takeMaterials(double H2O, 
								double C, 
								double N, 
								double K, 
								double Ca,
								double P,
								double NaCl) {
		
		double[] ratios = {
				Math.min(soilComposition.get("water") / H2O, 1),
				Math.min(soilComposition.get("carbon") / C, 1),
				Math.min(soilComposition.get("nitrogen") / N, 1),
				Math.min(soilComposition.get("potassium") / K, 1),
				Math.min(soilComposition.get("calcium") / Ca, 1),
				Math.min(soilComposition.get("phosphorus") / P, 1),
				Math.min(soilComposition.get("salt") / NaCl, 1),
		};
		
		double minRatio = MathUtils.min(ratios);
		
		soilComposition.subtract("water", H2O * minRatio);
		soilComposition.subtract("carbon", C * minRatio);
		soilComposition.subtract("nitrogen", N * minRatio);
		soilComposition.subtract("potassium", K * minRatio);
		soilComposition.subtract("calcium", Ca * minRatio);
		soilComposition.subtract("phosphorus", P * minRatio);
		soilComposition.subtract("salt", NaCl * minRatio);
		
		return minRatio;
	}
	
	public String toString() {
		return soilComposition.toString();
	}
	
	public String display() {
		int totalEntitySize = 0;
		for(Group grp : residentGroups) {
			totalEntitySize += grp.members.size();
		}
		
		String displayChar = totalEntitySize + "";
		
		int numLeftSpaces = 3 - displayChar.length();
		int numRightSpaces = 2;
		
		return "[" + StringUtils.repeat(" ", numLeftSpaces) + displayChar + StringUtils.repeat(" ", numRightSpaces) + "]"; 
		
	}
}
