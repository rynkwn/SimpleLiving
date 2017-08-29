package ecology;

import data.Macronutrient;
import entities.Species;

import java.util.HashMap;

/*
 * A data class meant to hold information about species read in by the EcologyReader
 */

public class WildSpecies {

	public String name;
	public String type;
	public String captureResult;
	public HashMap<String, Integer> harvestResult;
	public String consumption;
	public double reproductionRate;

	public Macronutrient nutrientRequirements; // Resources needed to spawn a new instance.
	public Macronutrient turnNutrition; // Passive addition to local tile resources.
	public Macronutrient deathNutrition; // Resources released on death.
	
	public double totalMass;
	public int power;
	
	public WildSpecies(String name,
			String type,
			String captureResult,
			HashMap<String, Integer> harvestResult,
			String consumption,
			double reproductionRate,
			Macronutrient nutrientRequirements,
			Macronutrient turnNutrition,
			Macronutrient deathNutrition,
			double totalMass,
			int power) {
		
		this.name = name;
		this.type = type;
		this.captureResult = captureResult;
		this.harvestResult = harvestResult;
		this.consumption = consumption;
		this.reproductionRate = reproductionRate;
		this.nutrientRequirements = nutrientRequirements;
		this.turnNutrition = turnNutrition;
		this.deathNutrition = deathNutrition;
		this.totalMass = totalMass;
		this.power = power;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("name: " + name + "\n");
		sb.append("captureResult: " + captureResult + "\n");
		sb.append("harvestResult: " + harvestResult + "\n");
		sb.append("consumption: " + consumption + "\n");
		sb.append("reproductionRate: " + reproductionRate + "\n");
		sb.append("totalMass: " + totalMass + "\n");
		sb.append("power: " + power + "\n");
		sb.append("nutrientRequirements: " + nutrientRequirements.toString() + "\n");

		return sb.toString();
	}

}