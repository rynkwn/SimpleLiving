package util;

import java.util.Random;
import java.util.ArrayList;

// Based on this:
// http://devmag.org.za/2009/04/25/perlin-noise/
// Though note, not actually Perlin noise!
public class ValueNoise {

	public static double[][] generateWhiteNoise(int width, int height, int seed, double maxValue) {
		Random random = new Random(seed);
		
		double[][] noise = new double[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				noise[i][j] = random.nextDouble() * maxValue;
			}
		}
		
		return noise;
	}
	
	public static double interpolate(double x0, double x1, double alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}
	
	public static double[][] generateSmoothNoise(double[][] baseNoise, int octave) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		
		double[][] smoothNoise = new double[width][height];
		
		int samplePeriod = (int) Math.pow(2, octave);
		double sampleFrequency = 1.0 / samplePeriod;
		
		for(int i = 0; i < width; i++) {
			// Calculate horizontal sampling indices.
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width; // Wrap around
			double horizontal_blend = (i - sample_i0) * sampleFrequency;
			
			for(int j = 0; j < height; j++) {
				// Calculate vertical sampling indices.
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; // Wrap around
				double vertical_blend = (j - sample_j0) * sampleFrequency;
				
				// Blend the top two corners.
				double top = interpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);
				
				// Blend the bottom two corners
				double bottom = interpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);
				
				// Final blend
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}
		
		return smoothNoise;
	}
	
	/*
	 * A lower octaveCount generally seems to produce more extreme individual values, but also
	 * produces a less smooth distribution.
	 */
	public static double[][] generateValueNoise(int width, int height, int seed, int octaveCount, double maxValue) {
		
		double[][] baseNoise = generateWhiteNoise(width, height, seed, maxValue);
		
		ArrayList<double[][]> smoothNoise = new ArrayList<double[][]>();
		double persistence = 0.5;
		
		for(int i = 0; i < octaveCount; i++) {
			smoothNoise.add(ValueNoise.generateSmoothNoise(baseNoise, i));
		}
		
		double[][] valueNoise = new double[width][height];
		double amplitude = 1.0;
		double totalAmplitude = 0.0;
		
		// Blend noise together.
		for(int octave = octaveCount - 1; octave >= 0; octave--) {
			amplitude *= persistence;
			totalAmplitude += amplitude;
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					valueNoise[i][j] += smoothNoise.get(octave)[i][j] * amplitude;
				}
			}
		}
		
		// Normalization
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				valueNoise[i][j] /= totalAmplitude;
			}
		}
		
		return valueNoise;
	}
}
