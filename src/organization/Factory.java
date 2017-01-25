package organization;


import item.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Factory {
	// A mechanism that translates Inputs -> Outputs
	
	public String name;
	public int time; // Time requirement?
	public int manpower; // How much work is required as input?
	
	public HashSet<FactoryDescription> inputs = new HashSet<FactoryDescription>();
	public ArrayList<FactoryDescription> outputs = new ArrayList<FactoryDescription>();
	
	// Need a description of Inputs
	// Need a description of Outputs
	// How do we construct a given item from the above maps?
	
	public Factory() {
		outputs.add(new FactoryDescription("Water", 2));
	}

	/*
	public ArrayList<Component> produce() {
		
	}
	*/
	
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
