package organization;


import item.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Building {
	// A mechanism that translates Inputs -> Outputs
	
	public String name;
	public int time; // Time requirement?
	public int timePassed;
	public int manpower; // How much work is required as input?
	public int manpowerStored;
	public int length;
	public int width;
	
	public HashSet<FactoryDescription> inputs = new HashSet<FactoryDescription>();
	public ArrayList<FactoryDescription> outputs = new ArrayList<FactoryDescription>();
	
	// Need a description of Inputs
	// Need a description of Outputs
	// How do we construct a given item from the above maps?
	
	public Building() {
		name = "Simple Well";
		time = 0;
		timePassed = 0;
		manpower = 0;
		manpowerStored = 0;
		outputs.add(new FactoryDescription("Water", 2));
	}
	
	// If sufficient inputs, then produce outputs.
	public ArrayList<Item> turn() {
		if(satisfied())
			return produce();
		return new ArrayList<Item>();
	}

	public ArrayList<Item> produce() {
		ArrayList<Item> components = new ArrayList<Item>();
		
		for(FactoryDescription fd : outputs) {
			components.add(ItemsReader.makeComponent(fd.name, fd.amount));
		}
		
		return components;
	}
	
	// Checks to see if the inputs are satisfied.
	public boolean satisfied() {
		for(FactoryDescription fd : inputs) {
			if(! fd.satisfied())
				return false;
		}
		
		return true;
	}
	
	// A class that handles describing a given object.
	// Used in the Factory class to specify inputs/outputs reasonably.
	class FactoryDescription {
		
		public String name;
		public int amount; // The amount we need to satisfy.
		public int storedAmount;
		
		public FactoryDescription(String name, int amount) {
			this.amount = amount;
			this.name = name;
		}
		
		// Determines if the specific Item matches
		// the itemClass and name.
		public boolean match(String name) {
			return this.name.equalsIgnoreCase(name);
		}
		
		// Checks to see if this input is satisfied.
		public boolean satisfied() {
			return amount == storedAmount;
		}
		
		// Adds an amount to storage, and if too much have been added in,
		// returns the leftover amount.
		public int add(int amnt) {
			int diff = amount - storedAmount;
			storedAmount += amnt;
			
			if(amnt > diff) {
				storedAmount = amount;
				return amnt - diff;
			}
			
			return 0;
		}
		
		// Empties the amount stored.
		public void empty() {
			storedAmount = 0;
		}
	}
}
