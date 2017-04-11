package world;

import item.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import data.Constants;
import entities.Person;
import organization.Factory;

public class Tile {
	
	// Constants
	public static final int DEFAULT_LENGTH = 20;
	public static final int DEFAULT_WIDTH = 20;
	
	// We'll consider the square tiles as 0 - (length - 1) and similarly for width.
	public int length;
	public int width;
	public double fertility;
	public double mineral;
	public ArrayList<Factory> factories;
	public Inventory localItems;
	
	// HashSet<Minerals> mineralsPresent
	
	// Default constructor for tests.
	public Tile() {
		length = DEFAULT_LENGTH;
		width = DEFAULT_WIDTH;
		fertility = 1;
		mineral = 1;
		factories = new ArrayList<Factory>();
		localItems = new Inventory();
	}
	
	// Simulate a turn.
	public void turn() {
		for (Factory fact : factories) {
			localItems.addList(fact.turn());
		}
	}
	
	
	// Add a factory if possible.
	// Return a boolean signal notifying if action was completed.
	public boolean addFactory(int x, int y, Factory fact) {
		if(canFit(x, y, fact.length, fact.width)) {
			factories.add(fact);
			return true;
		}
		
		return false;
	}
	
	// Check to see if something of a given length and width can fit,
	// with its top-left corner in the coordinates (X, Y).
	public boolean canFit(int x, int y, int length, int width) {
		if(this.length - x + length > this.length)
			return false;
		
		if(this.length - x < 0)
			return false;
		
		if (this.width - y + width > this.width)
			return false;
		
		if(this.width - y < 0) 
			return false;
		
		return true;
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