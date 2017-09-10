package ecology;

import data.Macronutrient;
import util.Range;
import entities.Species;

import java.util.HashMap;

/*
 * A data class meant to hold information about species read in by the EcologyReader
 */

public class WildSpecies {
	
	public static final String TYPE_PLANT = "Plant";

	public String name;
	public String type;
	public String captureResult;
	public HashMap<String, Integer> harvestResult;
	public String consumption;
	public double reproductionRate;
	public Range temperatureTolerance;

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
			Range temperatureTolerance,
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
		this.temperatureTolerance = temperatureTolerance;
		this.nutrientRequirements = nutrientRequirements;
		this.turnNutrition = turnNutrition;
		this.deathNutrition = deathNutrition;
		this.totalMass = totalMass;
		this.power = power;
	}
	
	public String getPreySpeciesType() {
		if(consumption.equals("Herbivorous")) {
			return TYPE_PLANT;
		}
		
		return "None";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("name: " + name + "\n");
		sb.append("captureResult: " + captureResult + "\n");
		sb.append("harvestResult: " + harvestResult + "\n");
		sb.append("consumption: " + consumption + "\n");
		sb.append("temperature tolerance: " + temperatureTolerance + "\n");
		sb.append("reproductionRate: " + reproductionRate + "\n");
		sb.append("totalMass: " + totalMass + "\n");
		sb.append("power: " + power + "\n");
		sb.append("nutrientRequirements: " + nutrientRequirements.toString() + "\n");
		sb.append("turn nutrients: " + turnNutrition.toString() + "\n");
		sb.append("death nutrients: " + deathNutrition.toString() + "\n");
		
		sb.append("\n\n");
		return sb.toString();
	}

}