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
	
	public int getRandomNumberInRange() {
		return rand.nextInt(max - min) + min;
	}
	
	public String toString() {
		return "[" + min + "," + max + "]";
	}
}