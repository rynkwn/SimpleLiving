package organization;

import organization.Subpop;

import java.util.HashMap;
import java.util.ArrayList;

public class Population {
	
	// A map of tag -> Subpops. E.g., the Citizen subpopulation.
	public HashMap<String, Subpop> subPopulation;
	public HashMap<String, ArrayList<Subpop>> subPops;
}
