package util;

import java.util.Random;

public class RandomUtils {
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
		Random rand = new Random();
		return rand.nextInt(max + 1 - min) + min;
	}

}
