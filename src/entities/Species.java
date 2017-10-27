package entities;

import java.util.*;

import util.Range;
import util.ChanceOutcomes;

public class Species {
	public String name;
	public int gestationPeriod;
	public Range numberOffspringProduced;
	public long initialSize;
	public long finalSize;
	public int timeTillMaturation;
	public ChanceOutcomes<String> offspring;
	public Body initialBodyStructure;
	public String behaviorFile;
	public HashMap<String, BiologicalProduct> products;
	public HashSet<String> tags;
	
	public Species(String name,
				   int gestationPeriod,
				   Range numberOffspringProduced,
				   long initialSize,
				   long finalSize,
				   int timeTillMaturation,
				   ChanceOutcomes<String> offspring,
				   Body initialBodyStructure,
				   String behaviorFile,
				   HashMap<String, BiologicalProduct> products,
				   HashSet<String> tags) {
		
		this.name = name;
		this.gestationPeriod = gestationPeriod;
		this.numberOffspringProduced = numberOffspringProduced;
		this.initialSize = initialSize;
		this.finalSize = finalSize;
		this.timeTillMaturation = timeTillMaturation;
		this.offspring = offspring;
		this.initialBodyStructure = initialBodyStructure;
		this.behaviorFile = behaviorFile;
		this.products = products;
		this.tags = tags;
	}
	
	/*
	 * On reading in the main data from a JSON file, updates certain attributes.
	 */
	public void processJSON() {
		// Need to set up:
		// Offspring seed. Should be random every time.
		// Maybe some bodyparts stuff? Will need to look at in more detail.
		
		// Refresh the offspring generator so we get a surprise... Though is
		// this necessary? Not sure how much having a consistent seed should
		// affect runs occuring at different times... but the seed
		// should determine outcomes. So let's do this.
		offspring.refreshRandomGenerator();
		
		// Update the Body to update its own properties after being read in from JSON.
		initialBodyStructure.processJSON();
	}
	
	public ArrayList<Entity> reproduce() {
		ArrayList<Entity> children = new ArrayList<Entity>();
		
		int numChildren = numberOffspringProduced.getRandomNumberInRange();
		for(int i = 0; i < numChildren; i++) {
			children.add(SpeciesReader.getSpecies(offspring.getRandomOutcome()).makeInstanceOf(0));
		}
		
		return children;
	}
	
	/*
	 * Makes an instance of this species.
	 */
	public Entity makeInstanceOf(int age) {
		Random rand = new Random();
		
		HashMap<String, Integer> progressTowardsBioProducts = new HashMap<String, Integer>();
		
		for(BiologicalProduct bioProd : products.values()) {
			progressTowardsBioProducts.put(bioProd.name, bioProd.timeToProduce);
		}
		
		return new Entity("TEST" + rand.nextInt(10000), 
				name, 
				0,
				progressTowardsBioProducts,
				initialBodyStructure.copyStructure(age));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("gestationPeriod: " + gestationPeriod + "\n");
		sb.append("numberOffspringProduced: " + numberOffspringProduced.toString() + "\n");
		sb.append("initialSize: " + initialSize + "\n");
		sb.append("finalSize: " + finalSize + "\n");
		sb.append("offspring: " + offspring.toString() + "\n");
		sb.append("initialBodyStructure: " + initialBodyStructure.toString() + "\n");
		sb.append("behaviorFile: " + behaviorFile + "\n");
		sb.append("bio products: " + products.toString() + "\n");
		sb.append("tags: " + tags.toString() + "\n");
		
		return sb.toString();
	}
}