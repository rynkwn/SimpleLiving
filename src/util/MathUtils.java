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

}
