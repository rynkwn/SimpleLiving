package entities;

import java.util.*;

import item.*;
import organization.*;

public class Entity {
	
	//////////////////////////////////////
	//
	// Skill Constants
	//
	
	// Combat-related skills.
	
	// These skills affect to-hit chances with
	// the relevant weapons. Dodging reduces an opponents
	// to-hit chance.
	public static final String SKILL_MELEE = "Melee";
	public static final String SKILL_RANGED = "Ranged";
	public static final String SKILL_DODGING = "Dodging";
	
	// Strength increases melee damage, and also affects
	// carrying capacity.
	public static final String SKILL_STRENGTH = "Strength";
	
	// Speed affects run speed in combat as well as overworld speed.
	public static final String SKILL_SPEED = "Speed";
	
	
	// Labor-related skills
	
	// Skills related to trapping, hunting, and gathering.
	public static final String SKILL_NATURALISM = "Naturalism";
	public static final String SKILL_ENGINEERING = "Engineering";
	
	//////////////////////////////////////
	//
	// Entity Attributes
	//
	
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public HashMap<String, Integer> progressTowardsBioProduct; // Progress towards bioProduct. Counting down.
	public Body body;
	
	public Group group;
	
	// Skills!
	
	// Experiment requirement for a skill increase
	// is 10^{skill level} (Or some other exponential increase).
	// When a skill is used, we increase experience, and then check
	// to see if that's sufficient for a level up. If it is, then
	// we reset experience for that skill.
	public HashMap<String, Integer> skills;
	public HashMap<String, Long> experience;
	
	
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
		if(canReproduce() && timeSinceLastBirth == entitySpecies.gestationPeriod) {
			timeSinceLastBirth = 0;
			
			group.addMembers(entitySpecies.reproduce());
			
		} else {
			timeSinceLastBirth ++;
		}
		
	}
	
	// Try to get the relevant skill value of this entity.
	public int getSkillValue(String skillName) {
		if(skills.containsKey(skillName)) {
			return skills.get(skillName);
		} else {
			return 0;
		}
	}
	
	// Adds experience for a specific skill to the entity.
	public void addExperience(String skillName, long exp) {
		long curExpValue = exp;
		int curLevel = 0;
		
		if(experience.containsKey(skillName)) {
			curLevel = skills.get(skillName);
			curExpValue += experience.get(skillName);
		} else {
			// If we have no experience for it, we assume
			// we also don't have the skill.
			skills.put(skillName, 0);
			experience.put(skillName, 0L);
		}
		
		long expNeededToLevel = Entity.experienceToLevel(curLevel);
		
		int levelsGained = 0;
		long expRemaining = curExpValue;
		
		while(expRemaining >= expNeededToLevel) {
			levelsGained++;
			expRemaining -= expNeededToLevel;
			expNeededToLevel = Entity.experienceToLevel(curLevel + levelsGained);
		}
		
		skills.put(skillName, curLevel + levelsGained);
		experience.put(skillName, expRemaining);
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
		sb.append(name + " - " + species);
		sb.append("\t" + body.toString());
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
