package world;

import java.util.*;

import organization.*;
import util.*;

/*
 * A big region in the world. Basically a single "map" consisting of lots of smaller tiles.
 */

public class BigTile {
	
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
		water = nitrogen = phosphorus = potassium = biomass = 70000;
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
	
	/*
	 * Over time, this tile will gradually regenerate its properties.
	 */
	public void turn(int numTurnsPassed) {
		water = 1.05 * water;
		nitrogen = 1.05 * nitrogen;
		phosphorus = 1.05 * phosphorus;
		biomass = 1.05 * biomass;
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
