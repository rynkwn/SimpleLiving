package util;

import java.util.*;

/*
 * A class that associates some unknown outcome T with a number of possibilities, and randomly selects 
 * among those outcomes.
 */
public class ChanceOutcomes<T> {
	Random rand = new Random();
	TreeMap<Double, T> events;
	
	// NOTE: If passed in TreeMap is not valid, then events is not set.
	public ChanceOutcomes(TreeMap<Double, T> probabilities) {
		if(checkValidEventSpace(probabilities))
			events = probabilities;
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
