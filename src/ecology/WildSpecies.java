package ecology;

import data.Macronutrient;
import entities.Species;

import java.util.HashMap;

/*
 * A data class meant to hold information about species read in by the EcologyReader
 */

public class WildSpecies {

	public String name;
	public String captureResult;
	public HashMap<String, Integer> harvestResult;
	public String consumption;
	public double reproductionRate;

	public Macronutrient nutrientRequirements;
	
	public WildSpecies(String name,
			String captureResult,
			HashMap<String, Integer> harvestResult,
			String consumption,
			double reproductionRate,
			Macronutrient nutrientRequirements) {
		
		this.name = name;
		this.captureResult = captureResult;
		this.harvestResult = harvestResult;
		this.consumption = consumption;
		this.reproductionRate = reproductionRate;
		this.nutrientRequirements = nutrientRequirements;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("name: " + name + "\n");
		sb.append("captureResult: " + captureResult + "\n");
		sb.append("harvestResult: " + harvestResult + "\n");
		sb.append("consumption: " + consumption + "\n");
		sb.append("reproductionRate: " + reproductionRate + "\n");
		sb.append("nutrientRequirements: " + nutrientRequirements.toString() + "\n");

		return sb.toString();
	}

}