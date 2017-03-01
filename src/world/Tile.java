package world;

import item.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import organization.Factory;

public class Tile {
	
	int length;
	int width;
	double fertility;
	double mineral;
	ArrayList<Factory> factories;
	Inventory localItems;
	
	// HashSet<Minerals> mineralsPresent
	
	public Tile() {
		
	}
}

/*
Should be able to hold items.
Will also hold factories/production buildings.
Will ultimately contain a map. I.e., production buildings will have an actual location.
Unless in a fight, abstractly the tile is the location where most play-time will occur. I want gameplay roughly similar to Dwarf Fortress/Rimworld, in that the main focus is managing your disparate gaggle of individuals. However, I'm currently viewing this as fairly abstract. I.e., you don't have to worry about individuals actually moving from place to place and packing things. You have labor and e.t.c. you can allocate, as well as laws/social rules to set up. When in a fight, the map will be used.
Should reflect the local fertility/mineral wealth, and the presence/absence of certain materials/minerals.
Will also have a location, but this can be stored one level higher. I.e., on a World object.
The above implies these structures:

HashMap for name -> List of Persons
An int length
An int width
A double for fertility. (Possibly to be used as a straight-up scalar.)
A double for mineral wealth.
A HashSet for minerals/materials present. // Don't implement yet. Not sufficiently fleshed out.
A list of factories.
A list of items. (Maybe I should make an inventory class. To make a lot of these things easier and to share code. A large number of things have an inventory. Inventory can be "open" or "closed", can aggregate items together. e.t.c.)
What sorts of methods would be interesting?

canFit(x, y, length, width): Whether the given structure can fit, when the structure has dimensions length x width, and the top left is at corner x, y.
turn(): Perhaps? Essentially, let all entities within the tile perform their action when not in a battle. Currently envisioning this to be a full week. So entities should be able to do multiple things.
*/