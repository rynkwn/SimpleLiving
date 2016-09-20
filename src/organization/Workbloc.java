package organization;

import java.util.ArrayList;
import java.util.Hashtable;

public class Workbloc {
	private ArrayList<Long> workers; // Identified by ID
	private FactoryFunction factory;
	
	public Workbloc(ArrayList<Long> workerIDs, Hashtable<String, Long> inputs, Hashtable<String, Long> outputs) {
		this.workers = workerIDs;
		this.factory = new FactoryFunction(inputs, outputs);
	}
}

/*
 * A class to handle the input, storage, and output mechanics of the Workbloc.
 */
class FactoryFunction {
	private Hashtable<String, Long> stored;
	private Hashtable<String, Long> inputs;
	private Hashtable<String, Long> outputs;
	
	public FactoryFunction(Hashtable<String, Long> inputs, Hashtable<String, Long> outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.stored = new Hashtable<String, Long>();
		
		for(String resource : inputs.keySet()) {
			this.stored.put(resource, (long) 0);
		}
	}
	
	// Input resources.
	public boolean input(Hashtable<String, Long> inputResources) {
		for(String resource : inputResources.keySet()) {
			long amStored = stored.get(resource);
			long amInput= inputResources.get(resource);
			
			stored.put(resource, amStored + amInput);
		}
		return true;
	}
	
	// Checks to see if stored resources >= input resources.
	public boolean satisfied() {
		for(String resource : inputs.keySet()) {
			if (stored.get(resource) < inputs.get(resource)) {
				return false;
			}
		}
		return true;
	}
}
