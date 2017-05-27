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
		int randValue = (max - min == 0) ? 0 : rand.nextInt(max - min);
		
		return randValue + min;
	}
	
	public String toString() {
		return "[" + min + "," + max + "]";
	}
}
