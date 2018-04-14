package organization;

import entities.SpeciesReader;
import entities.Species;

import java.util.HashMap;

public class Subpop {
	public String species;
	
	// The contentment of the pop, from 0.0 to 200.0
	public double happiness;
	
	// Population distribution by age.
	public long child;
	public long juvenile;
	public long adult;
	public long elderly;
	
	public Subpop(String species, double happiness, 
			long childPop, long juvenilePop, long adultPop, long elderlyPop) {
		this.species = species;
		
		this.happiness = happiness;
		
		this.child = childPop;
		this.juvenile = juvenilePop;
		this.adult = adultPop;
		this.elderly = elderlyPop;
	}
	
	/*
	 * Return a list of needs for this population. Missing these needs will
	 * have a significant impact on population growth and satisfaction.
	 */
	public HashMap<String, Long> getNeeds() {
		HashMap<String, Long> needs = new HashMap<String, Long>();
		
		Species spec = SpeciesReader.getSpecies(species);
		
		
		
		return needs;
	}

}
