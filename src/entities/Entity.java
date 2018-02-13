package entities;

import java.util.*;

import data.LaborPool;
import data.SkillPool;
import item.*;
import organization.*;

public class Entity {
	
	//////////////////////////////////////
	//
	// Entity Attributes
	//
	
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public HashMap<String, Integer> progressTowardsBioProduct; // Progress towards bioProduct. Counting down.
	public Body body;
	
	public LaborPool labor;
	public SkillPool skills;
	
	public Group group;
	
	
	public Entity(String name, 
			String species, 
			int timeSinceLastBirth,
			HashMap<String, Integer> progressTowardsBioProduct,
			Body body,
			SkillPool sp,
			LaborPool lp
			) {
		this.name = name;
		this.species = species;
		this.timeSinceLastBirth = timeSinceLastBirth;
		this.progressTowardsBioProduct = progressTowardsBioProduct;
		this.body = body;
		
		skills = new SkillPool(sp);
		labor = new LaborPool(lp);		
	}
	
	/*
	 * Let this entity iterate through a normal turn.
	 */
	public void turn() {
		Species entitySpecies = SpeciesReader.getSpecies(species);
		
		// Increase the age of this entity by 1.
		body.age(1);
		
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
		if(canReproduce() && timeSinceLastBirth >= entitySpecies.gestationPeriod) {
			timeSinceLastBirth = 0;
			
			group.addMembers(entitySpecies.reproduce());
			
		} else {
			timeSinceLastBirth ++;
		}
		
		LaborPool speciesLabor = SpeciesReader.getSpecies(species).baseLabor;
		labor.updateLabor(speciesLabor, skills, calculateLaborModifier());
	}

	/*
	 * Return how much weight capacity this entity is able to contribute to its groups
	 * maximum inventory weight. Right now we'll work off a simple placeholder formula:
	 * .5 * size * manipulation.
	 */
	public double getMaximumWeightCapacity() {
		return .5 * body.size * body.manipulation;
	}

	/*
	 * The labor provided by this entity may be modified by other factors.
	 * Currently, this is just nutritional heatlh. Specifically, if entity
	 * nutritional health is below .5, they produce no labor.
	 * Labor modifier is tiered:
	 * .5 - .7: 50% labor effectiveness
	 * .7 - 1.0: 100% labor effectiveness
	 * 1.0 - 1.5: 110% labor effectiveness
	 */
	public double calculateLaborModifier() {
		double nutritionalHealth = body.nutritionalHealth;

		if(nutritionalHealth < .5) {
			return 0;
		} else if (nutritionalHealth < .7) {
			return .5;
		} else if (nutritionalHealth < 1.0) {
			return 1.0;
		} else {
			return 1.10;
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
		return body.isDead();
	}
	
	public boolean canReproduce() {
		return body.isAdult() &&
				! SpeciesReader.getSpecies(species).tags.contains("STERILE");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " - " + species + "\n");
		sb.append("Skills: " + skills.toString("\t") + "\n");
		sb.append("Labor:" + labor.toString("\t") + "\n");
		sb.append(body.toString());
		return sb.toString();
	}
	
	//////////////////////////////////////
	//
	// Static methods
	//
	
	// Return the amount of experience needed to advance a level
	public static long experienceToLevel(int curLevel) {
		return (long) Math.pow(10, curLevel);
	}
}
