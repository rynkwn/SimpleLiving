package entities;

import java.util.*;

public class Species {
	public String name;
	public String description;
	public HashSet<String> tags;
	
	public int gestationPeriod;
	public double numberOffspringProduced; // This is the average.
	public long initialSize;
	public long finalSize;
	public int timeTillMaturation;
	public int timeTillElderly;
	public int averageLifespan;
	public HashMap<String, Double> offspring; // Species -> Probabillity offspring is that spec.
	
	public double baseLabor;
	public double educationAffinity;
	
	public Body initialBodyStructure;
	
	public String behaviorFile;
	
	public HashMap<String, BiologicalProduct> products;
	
	public Species(String name,
				   String description,
				   HashSet<String> tags,
				   
				   int gestationPeriod,
				   double numberOffspringProduced,
				   long initialSize,
				   long finalSize,
				   int timeTillMaturation,
				   int timeTillElderly,
				   int averageLifespan,
				   HashMap<String, Double> offspring,
				   
				   double baseLabor,
				   double educationAffinity,
				   
				   Body initialBodyStructure,
				   
				   String behaviorFile,
				   
				   HashMap<String, BiologicalProduct> products
				   ) {
		
		this.name = name;
		this.description = description;
		this.tags = tags;
		
		this.gestationPeriod = gestationPeriod;
		this.numberOffspringProduced = numberOffspringProduced;
		this.initialSize = initialSize;
		this.finalSize = finalSize;
		this.timeTillMaturation = timeTillMaturation;
		this.timeTillElderly = timeTillElderly;
		this.averageLifespan = averageLifespan;
		this.offspring = offspring;
		
		this.baseLabor = baseLabor;
		this.educationAffinity = educationAffinity;
		
		this.initialBodyStructure = initialBodyStructure;
		
		this.behaviorFile = behaviorFile;
		
		this.products = products;
	}
	
	/*
	 * On reading in the main data from a JSON file, updates certain attributes.
	 */
	public void processJSON() {		
		// Update the Body to update its own properties after being read in from JSON.
		initialBodyStructure.processJSON();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("description: " + description + "\n");
		sb.append("tags: " + tags.toString() + "\n");
		sb.append("gestationPeriod: " + gestationPeriod + "\n");
		sb.append("numberOffspringProduced: " + numberOffspringProduced + "\n");
		sb.append("initialSize: " + initialSize + "\n");
		sb.append("finalSize: " + finalSize + "\n");
		sb.append("offspring: " + offspring.toString() + "\n");
		sb.append("baseLabor: " + baseLabor + "\n");
		sb.append("educationAffinity: " + educationAffinity + "\n");
		sb.append("initialBodyStructure: " + initialBodyStructure.toString() + "\n");
		sb.append("behaviorFile: " + behaviorFile + "\n");
		sb.append("bio products: " + products.toString() + "\n");
		
		return sb.toString();
	}
}