package entities;

import java.util.*;

import item.*;

public class Entity {
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public HashMap<String, Integer> progressTowardsBioProduct; // Progress towards bioProduct. Counting down.
	public Body body;
	
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
	
	public String toString() {
		return name + " - " + species;
	}
	
	public boolean isDead() {
		Species entitySpecies = SpeciesReader.getSpecies(species);
		
		// Simple starvation check. If mass drops to .7 of expected final size,
		// entity dies of starvation.
		if((double) body.mass / entitySpecies.finalSize <= .7) {
			return true;
		}
		
		return false;
	}
	
	
}
