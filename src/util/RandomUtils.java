package util;

import java.util.Random;

public class RandomUtils {
	public static Random clsRand = new Random();
	public Random rand;
	
	public RandomUtils(int seed) {
		rand = new Random(seed);
	}
	
	// Get a random number between min and max, inclusive.
	public int getRandomInt(int min, int max) {
		return rand.nextInt(max + 1 - min) + min;
	}
	
	// A static version of getRandomInt
	public static int getInt(int min, int max) {
		return clsRand.nextInt(max + 1 - min) + min;
	}
	
	/*
	 *  We get a random number between 0 and maxNum (exclusive). If the result is less than successCriteria,
	 *  then success.
	 */
	public static boolean checkProb(int maxNum, int successCriteria) {
		return clsRand.nextInt(maxNum) < successCriteria;
	}

}
