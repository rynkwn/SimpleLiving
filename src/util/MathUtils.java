package util;

public class MathUtils {
	
	public static double min(double[] ratios) {
		double min = ratios[0];
		
		for(int i = 1; i < ratios.length; i++) {
			if(ratios[i] <= min) {
				min = ratios[i];
			}
		}
		
		return min;
	}
	
	/*
	 * A modified sigmoid formula.
	 */
	public static double sigmoid(double x, double bound) {
		return bound / (1 + Math.pow(Math.E, (-x)));
	}
	
	/*
	 * Slightly modified tan formula
	 */
	public static double tan(double x, double horizShift) {
		return Math.tan(x + horizShift);
	}

}
