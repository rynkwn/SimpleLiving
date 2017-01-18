package organization;


import java.util.HashMap;

import Item.*;

public class Factory {
	// A mechanism that translates Inputs -> Outputs
	
	public String name;
	public int time; // Time requirement?
	public int manpower; // How much work is required as input?
	
	public HashMap<String, Integer> inputs;
	public HashMap<String, Integer> outputs;
	
	// Need a description of Inputs
	// Need a description of Outputs
	// How do we construct a given item from the above maps?
	
	public Factory() {
		
	}
	
	// A class that handles describing a given object.
	// Used in the Factory class to specify inputs/outputs reasonably.
	class FactoryDescription {		
		public String itemClass; // Clothing, Consumable, Item, e.t.c.
		
		public String name;
		public double weight;
		public double condition;
		
		public String details;
		
		public FactoryDescription(boolean isOutput) {
		}
		
		// Determines if the specific Item matches
		// the itemClass and name.
		public boolean match(Item input) {
			return true;
		}
		
		// Produces the item described by the FactoryDescription.
		public Item produce() {
			return new Clothing();
		}
	}
}
