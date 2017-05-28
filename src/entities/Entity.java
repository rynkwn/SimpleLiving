package entities;

import java.util.*;

import item.*;
import organization.*;

public class Entity {
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public HashMap<String, Integer> progressTowardsBioProduct; // Progress towards bioProduct. Counting down.
	public Body body;
	
	public Group group;
	
	public Entity(String name, 
			String species, 
			int timeSinceLastBirth, 
			HashMap<String, Integer> progressTowardsBioProduct, 
			Body body) {
		this.name = name;
		this.species = species;
		this.timeSinceLastBirth = timeSinceLastBirth;
		this.progressTowardsBioProduct = progressTowardsBioProduct;
		this.body = body;
	}
	
	public void turn() {
		Species entitySpecies = SpeciesReader.getSpecies(species);
		
		// Produce any biological products.
		for(String productName : progressTowardsBioProduct.keySet()) {
			int timeUntilCompletion = progressTowardsBioProduct.get(productName);
			
			if(timeUntilCompletion == 0) {
				BiologicalProduct bioprod = entitySpecies.products.get(productName);
				
				group.addItem(bioprod.produce(body.mass));
				timeUntilCompletion = bioprod.timeToProduce;
			}
			
			timeUntilCompletion --;
			progressTowardsBioProduct.put(productName, timeUntilCompletion);
		}
		
		
		// Reproduce
		if(canReproduce() && timeSinceLastBirth == entitySpecies.gestationPeriod) {
			timeSinceLastBirth = 0;
			
			group.addMembers(entitySpecies.reproduce());
			
		} else {
			timeSinceLastBirth ++;
		}
		
	}
	
	/*
	 * Consume a set of food items as an ANIMAL nutrition type.
	 */
	public void eat(ArrayList<Food> meal) {
		body.eat(meal);
	}
	
	/*
	 * Used with the PLANT nutrition type.
	 */
	public void absorb(double needsSatisfied) {
		body.grow(needsSatisfied);
	}
	
	/*
	 * Checks to see if this entity is dead.
	 */
	public boolean isDead() {
		Species entitySpecies = SpeciesReader.getSpecies(species);
		
		// Simple starvation check. If mass drops to .7 of expected final size,
		// entity dies of starvation.
		if((double) body.mass / entitySpecies.finalSize <= .7) {
			return true;
		}
		
		return false;
	}
	
	public boolean canReproduce() {
		return ! SpeciesReader.getSpecies(species).tags.contains("STERILE");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " - " + species);
		sb.append("\t" + body.toString());
		return sb.toString();
	}	
	
}
