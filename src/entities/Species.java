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
		this.offspring = offspring;
		this.initialBodyStructure = initialBodyStructure;
		this.behaviorFile = behaviorFile;
		this.products = products;
		this.tags = tags;
	}
	
	public ArrayList<Entity> reproduce() {
		ArrayList<Entity> children = new ArrayList<Entity>();
		
		int numChildren = numberOffspringProduced.getRandomNumberInRange();
		for(int i = 0; i < numChildren; i++) {
			children.add(SpeciesReader.getSpecies(offspring.getRandomOutcome()).makeInstanceOf());
		}
		
		return children;
	}
	
	/*
	 * Makes an instance of this species.
	 */
	public Entity makeInstanceOf() {
		Random rand = new Random();
		
		HashMap<String, Integer> progressTowardsBioProducts = new HashMap<String, Integer>();
		
		for(BiologicalProduct bioProd : products.values()) {
			progressTowardsBioProducts.put(bioProd.name, bioProd.timeToProduce);
		}
		
		return new Entity("TEST" + rand.nextInt(10000), 
				name, 
				0,
				progressTowardsBioProducts,
				initialBodyStructure.copyStructure());
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