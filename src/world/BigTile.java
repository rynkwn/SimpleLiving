package world;

import java.util.*;

import organization.*;
import util.*;

/*
 * A big region in the world. Basically a single "map" consisting of lots of smaller tiles.
 */

public class BigTile {
	
	public static double maxValue = 10000;
	
	// Summary levels.
	public double water; // Total liters of water present in the BigTile
	public double nitrogen; // Total nitrogen in kg.
	public double phosphorus; // Total phosphorus in kg
	public double potassium; // Total potassium in kg
	public double biomass; // Total biomass in kg. 
	
	// Also need weather.
	
	public HashSet<Group> residentGroups;
	
	// For initial tests.
	public BigTile() {
		water = nitrogen = phosphorus = potassium = biomass = 5000;
		residentGroups = new HashSet<Group>();
	}
	
	public BigTile(double w, double n, double p, double k, double bm) {
		water = w;
		nitrogen = n;
		phosphorus = p;
		potassium = k;
		biomass = bm;
		
		residentGroups = new HashSet<Group>();
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
		
		water += (growthRate * water) * (1 - water / maxValue);
		nitrogen += (growthRate * nitrogen) * (1 - nitrogen / maxValue);
		phosphorus += (growthRate * phosphorus) * (1 - phosphorus / maxValue);
		potassium += (growthRate * potassium) * (1 - potassium / maxValue);
		biomass += (growthRate * biomass) * (1 - biomass / maxValue);
		
	}
	
	/*
	 * Returns the minimum ratio of ELEMENT / MAX_VALUE
	 */
	public double minRatio() {
		double[] ratios = {
			water / maxValue,
			nitrogen / maxValue,
			phosphorus / maxValue,
			potassium / maxValue,
			biomass / maxValue
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
	public double takeMaterials(double wat, double nit, double phos, double potas, double bio) {
		
		double[] ratios = {
				Math.min(water / wat, 1),
				Math.min(nitrogen / nit, 1),
				Math.min(phosphorus / phos, 1),
				Math.min(potassium / potas, 1),
				Math.min(biomass / bio, 1)
		};
		
		double minRatio = MathUtils.min(ratios);
		
		water -= wat * minRatio;
		nitrogen -= nit * minRatio;
		phosphorus -= phos * minRatio;
		potassium -= potas * minRatio;
		biomass -= bio * minRatio;
		
		return minRatio;
	}
	
	public String toString() {
		return "water: " + water + ", nitrogen: " + nitrogen + ", phosphorus: " + phosphorus + ", potassium: " + potassium + ", biomass: " + biomass;
	}
	
	public String display() {
		int totalEntitySize = 0;
		for(Group grp : residentGroups) {
			totalEntitySize += grp.members.size();
		}
		
		String displayChar = totalEntitySize + "";
		
		return "[" + displayChar + "]";
	}
}
