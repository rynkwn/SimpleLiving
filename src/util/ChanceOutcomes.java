package util;

import java.util.*;

/*
 * A class that associates some unknown outcome T with a number of possibilities, and randomly selects 
 * among those outcomes.
 */
public class ChanceOutcomes<T> {
	Random rand;
	TreeMap<Double, T> events;
	
	// NOTE: If passed in TreeMap is not valid, then events is not set.
	public ChanceOutcomes(TreeMap<Double, T> probabilities) {
		rand = new Random();
		
		if(checkValidEventSpace(probabilities))
			events = probabilities;
	}
	
	/*
	 *  Refresh the Random attribute! This method was created to
	 *  refresh the Random seed that we're saving in the JSON-format
	 *  of some classes which use this class. (Such as Species).
	 */
	public void refreshRandomGenerator() {
		rand = new Random();
	}
	
	public HashMap<T, Double> getReverseMap() {
		HashMap<T, Double> reverseMap = new HashMap<T, Double>();
		
		for (Double chance : events.keySet()) {
			reverseMap.put(events.get(chance), chance);
		}
		
		return reverseMap;
	}
	
	// Checks if a given TreeMap is a valid event space.
	public boolean checkValidEventSpace(TreeMap<Double, T> probabilities) {
		double totalProb = 0;
		
		for(Double chance : probabilities.keySet()) {
			totalProb += chance;
		}
		
		if(totalProb == 1.0)
			return true;
		return false;
	}
	
	// Get a random outcome.
	public T getRandomOutcome(){
		double prob = rand.nextDouble();
		
		for(Double chance : events.keySet()) {
			prob -= chance;
			if(prob <= 0) {
				return events.get(chance);
			}
		}
		
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Double chance : events.keySet()) {
			sb.append(chance + " - " + events.get(chance) + "\n");
		}
		
		return sb.toString();
	}
}
