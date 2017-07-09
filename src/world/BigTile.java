package world;

import java.util.*;

import organization.*;
import util.*;

/*
 * A big region in the world. Basically a single "map" consisting of lots of smaller tiles.
 */

public class BigTile {
	
	public static double maxValue = 50000;
	public static double initValue = 5000;
	
	// Summary levels.
	public double water; // Total liters of water present in the BigTile
	public double carbon;
	public double nitrogen; // Total nitrogen in kg.
	public double potassium;
	public double calcium;
	public double phosphorus; // Total phosphorus in kg
	public double salt;
	
	// Rates
	public double waterRate;
	
	// Also need weather.
	
	public HashSet<Group> residentGroups;
	
	// For initial tests.
	public BigTile() {
		water = carbon = nitrogen = potassium = calcium = phosphorus = salt = initValue;
		residentGroups = new HashSet<Group>();
	}
	
	public BigTile(double waterRate) {
		water = carbon = nitrogen = potassium = calcium = phosphorus = salt = initValue;
		residentGroups = new HashSet<Group>();
		
		this.waterRate = waterRate;
		water = maxValue * waterRate;
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
		
		double growthRate = .01;
		double rawIncrement = 10;
		Random rand = new Random();
		
		if(water < waterRate * maxValue) {
			double modifier = 1.1 * rand.nextDouble();
			water += (waterRate * maxValue * modifier) / 104;
		}
		
		carbon += (maxValue / 1000);
		nitrogen += (maxValue / 1000);
		potassium += (maxValue / 1000);
		calcium += (maxValue / 1000);
		phosphorus += (maxValue / 1000);
		salt += (maxValue / 1000);
		
		/*
		nitrogen += (growthRate * nitrogen) * (1 - nitrogen / maxValue) + rawIncrement;
		phosphorus += (growthRate * phosphorus) * (1 - phosphorus / maxValue) + rawIncrement;
		potassium += (growthRate * potassium) * (1 - potassium / maxValue) + rawIncrement;
		biomass += (growthRate * biomass) * (1 - biomass / maxValue) + rawIncrement;
		*/
		
	}
	
	/*
	 * Returns the minimum ratio of ELEMENT / MAX_VALUE
	 */
	public double minRatio() {
		double[] ratios = {
			water / maxValue,
			carbon / maxValue,
			nitrogen / maxValue,
			potassium / maxValue,
			calcium / maxValue,
			phosphorus / maxValue,
			salt / maxValue
		};
		
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
				Math.min(water / H2O, 1),
				Math.min(carbon / C, 1),
				Math.min(nitrogen / N, 1),
				Math.min(potassium / K, 1),
				Math.min(calcium / Ca, 1),
				Math.min(phosphorus / P, 1),
				Math.min(salt / NaCl, 1),
		};
		
		double minRatio = MathUtils.min(ratios);
		
		water -= H2O * minRatio;
		carbon -= C * minRatio;
		nitrogen -= N * minRatio;
		potassium -= K * minRatio;
		calcium -= Ca * minRatio;
		phosphorus -= P * minRatio;
		salt -= NaCl * minRatio;
		
		return minRatio;
	}
	
	public String toString() {
		return "water: " + water + 
				", carbon: " + carbon + 
				", nitrogen: " + nitrogen + 
				", potassium: " + potassium + 
				", calcium: " + calcium +
				", phosphorus: " + phosphorus +
				", salt: " + salt;
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
