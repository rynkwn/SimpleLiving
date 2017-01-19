package organization;


import java.util.HashMap;
import java.util.ArrayList;

import Item.*;

public class Factory {
	// A mechanism that translates Inputs -> Outputs
	
	public String name;
	public int time; // Time requirement?
	public int manpower; // How much work is required as input?
	
	public ArrayList<FactoryDescription> inputs;
	public ArrayList<FactoryDescription> outputs;
	
	// Need a description of Inputs
	// Need a description of Outputs
	// How do we construct a given item from the above maps?
	
	public Factory() {
		
	}
	
	// A class that handles describing a given object.
	// Used in the Factory class to specify inputs/outputs reasonably.
	class FactoryDescription {		
		public String itemClass; // Clothing, Consumable, Item, e.t.c.
		
		public long amount;
		public long storedAmount;
		public String name;
		public double weight;
		public double condition;
		
		public String details;
		
		public FactoryDescription(String itemClass, long amount, String name) {
			this.itemClass = itemClass;
			this.amount = amount;
			this.name = name;
		}
		
		// Determines if the specific Item matches
		// the itemClass and name.
		public boolean match(Item input) {
			// Match is mostly going to be used when seeing if an input item is correct.
			// Therefore, let's try to simply match on name and itemClass.
			
			// TODO: MATCH ON CLASS? FOR SIMPLICITY SAKE FOR RIGHT NOW, LET'S JUST MATCH ON NAME.
			
			return input.name().equalsIgnoreCase(name);
		}
		
		// Checks to see if this input is satisfied.
		public boolean satisfied() {
			return amount == storedAmount;
		}
		
		// Produces the item described by the FactoryDescription.
		public Item produce() {
			return new Clothing();
		}
	}
}
