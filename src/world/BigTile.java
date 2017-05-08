package world;

import java.util.*;

import organization.*;

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
	
	public String display() {
		String displayChar = residentGroups.size() > 0 ? residentGroups.size() + "" : " ";
		
		return "[" + displayChar + "]";
	}
}
