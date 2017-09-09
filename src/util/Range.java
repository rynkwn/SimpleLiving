package util;

import java.util.Random;

// A wrapper class to represent a range.
public class Range {
	
	static Random rand = new Random();
	
	public int min;
	public int max;
	
	public Range(int l, int r) {
		min = l;
		max = r;
	}
	
	// Checks to see if a given number is inside the range.
	public boolean numberInRange(int val) {
		return min <= val && val <= max;
	}
	
	/* 
	 * If val is not in the range, returns the shortest distance between val and the range.
	 * If val is in the range, then we return -1;
	 */
	public int dist(int val) {
		if(!numberInRange(val))
			return Math.min(Math.abs(min - val), Math.abs(val - max));
		return -1;
	}
	
	public int getRandomNumberInRange() {
		int randValue = (max - min == 0) ? 0 : rand.nextInt(max - min);
		
		return randValue + min;
	}
	
	public String toString() {
		return "[" + min + "," + max + "]";
	}
}
