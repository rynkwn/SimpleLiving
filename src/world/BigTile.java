package world;

import java.util.*;

import organization.*;

/*
 * A big region in the world. Basically a single "map" consisting of lots of smaller tiles.
 */

public class BigTile {
	
	// Summary levels.
	public long water; // Total liters of water present in the BigTile
	public long nitrogen; // Total nitrogen in kg.
	public long phosphorus; // Total phosphorus in kg
	public long potassium; // Total potassium in kg
	public long biomass; // Total biomass in kg. 
	
	// Also need weather.
	
	public ArrayList<Group> residentGroups;
	
	public BigTile(long w, long n, long p, long k, long bm) {
		water = w;
		nitrogen = n;
		phosphorus = p;
		potassium = k;
		biomass = bm;
		
		residentGroups = new ArrayList<Group>();
	}
	
	public String display() {
		String displayChar = residentGroups.size() > 0 ? residentGroups.size() + "" : " ";
		
		return "[" + displayChar + "]";
	}
}
