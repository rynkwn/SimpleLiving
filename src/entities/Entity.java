package entities;

import java.util.*;

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
	
	public String toString() {
		return name + " - " + species;
	}
	
	public boolean isDead() {
		return false;
	}
	
	
}
