package util;

import util.RandomUtils;

public class GradientNoise {
	public static final int GRADIENT_VERTICAL = 1;
	public static final int GRADIENT_HORIZONTAL = 2;
	
	/* Create an int[][] map with a gradient along gradientDirection, such that the high value occurs
	 * in the middle, while low values occur on the edges of the gradient.
	 * 
	 * tempVariance: Randomness in the temperature!
	 */
	public static int[][] gradientNoise(int length, int width, int seed, int low, int high, int tempVariance, int gradientDirection) {
		RandomUtils random = new RandomUtils(seed);
		int[][] temperatureMap = new int[length][width];
		
		double temperatureChangePerTile = high - low;
		if(gradientDirection == GRADIENT_VERTICAL) {
			temperatureChangePerTile /= length;
		} else {
			temperatureChangePerTile /= width;
		}
		
		// We want to peak halfway through.
		temperatureChangePerTile *= 2;
		
		if(gradientDirection == GRADIENT_VERTICAL) {
			for(int y = 0; y < width; y++) {
				for(int x = 0; x < length; x++) {
					
					if(x <= (length/2)) {
						temperatureMap[x][y] = (int) (low + (x * temperatureChangePerTile)) + random.getRandomInt(-tempVariance, tempVariance);
					} else {
						temperatureMap[x][y] = (int) (high - ((x - length/2) * temperatureChangePerTile)) + random.getRandomInt(-tempVariance, tempVariance);
					}
				}
			}
		} else {
			for(int x = 0; x < length; x++) {
				for(int y = 0; y < width; y++) {
					if(y < (width/2)) {
						temperatureMap[x][y] = (int) (low + (y * temperatureChangePerTile)) + random.getRandomInt(-tempVariance, tempVariance);						
					} else {
						temperatureMap[x][y] = (int) (high - ((y - length/2) * temperatureChangePerTile)) + random.getRandomInt(-tempVariance, tempVariance);
					}
				}
			}
		}
		
		return temperatureMap;
	}
}
