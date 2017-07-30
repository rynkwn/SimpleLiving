package ecology;

import entities.Species;

import java.util.HashMap;

/*
 * A data class meant to hold information about species read in by the EcologyReader
 */

public class WildSpecies {
	
	public String name;
	public String captureResult;
	public HashMap<String, Integer> harvestResult;
	public String consumption;
	public double reproduction_rate;
	
	public double water;
	public double carbon;
	public double nitrogen;
	public double potassium;
	public double calcium;
	public double phosphorus;
	public double salt;

}