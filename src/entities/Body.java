package entities;

import item.*;
import util.*;

import java.util.*;

/*
 * A wrapper class for an entity's body.
 */
public class Body {
	
	Species species;
	
	public int age;
	
	public double nutritionalHealth = 1.0;
	
	public double mass;
	public double size;
	
	public double moving; // Number of squares individual can move. 
	public double eating; // Between 0 - 1.
	public double talking;
	public double consciousness;
	public double sight; // 100 -> 20/20 vision.
	public double manipulation; // between 0 - 1.
	public double breathing; // between 0 - 1. Anything less than 20% is dangerous.
	
	ArrayList<BodyPart> bodyparts;
	
	public Nutrition nutrition;
	
	public Body(String nutritionType, double metabolism) {
		
		bodyparts = new ArrayList<BodyPart>();
		mass = 0;
		moving = 0;
		eating = 0;
		talking = 0;
		consciousness = 0;
		sight = 0;
		manipulation = 0;
		breathing = 0;
		
		nutrition = new Nutrition(nutritionType, metabolism);
	}
	
	public Body(Nutrition nutr) {
		bodyparts = new ArrayList<BodyPart>();
		mass = 0;
		moving = 0;
		eating = 0;
		talking = 0;
		consciousness = 0;
		sight = 0;
		manipulation = 0;
		breathing = 0;
		
		nutrition = new Nutrition(nutr);
	}
	
	/*
	 * Checks to see if the body is dead.
	 */
	public boolean isDead() {
		return nutritionalHealth <= .7; // Checks to see if the entity has starved
	}
	
	public boolean isAdult() {
		return age >= species.timeTillMaturation;
	}
	
	public void age(int numTurnsPassed) {
		age += numTurnsPassed;
	}
	
	/*
	 * Eats a meal and updates mass.
	 */
	public void eat(ArrayList<Food> meal) {
		double needsSatisfied = nutrition.eat(eating, meal);
		grow(needsSatisfied);
	}
	
	public void grow(double pctNeedsSatisfied) {
		double factorGrow = growthFactor(pctNeedsSatisfied);
		nutritionalHealth = Math.min(nutritionalHealth * factorGrow, 1.4);
		
		if(age < species.timeTillMaturation) {
			double normalGrowth = growthDerivativeEstimate() * nutritionalHealth;
			double newSize = size + normalGrowth;
			
			growToSize(newSize);
		}
		 
		
		updateTraits();
	}
	
	// Returns the growth factor given what pct of needs were satisfied.
	public double growthFactor(double pctNeedsSatisfied) {
		return MathUtils.sigmoid(pctNeedsSatisfied, 1.0, .6, .5, 1);
	}
	
	public void calcTraitsRecursively(BodyPart bp) {
		double pctEffective = bp.health / bp.maxHealth;
		
		moving += (bp.moving * pctEffective);
		eating = Math.max(eating, (bp.eating ? 1 : 0) * pctEffective);
		talking = Math.max(talking, (bp.talking ? 1 : 0) * pctEffective);
		consciousness = Math.max(consciousness, (bp.consciousness ? 1 : 0) * pctEffective);
		sight += (bp.sight * pctEffective);
		manipulation = Math.max(manipulation, (bp.manipulation ? 1 : 0) * pctEffective);
		breathing = Math.max(breathing, (bp.breathing ? 1 : 0) * pctEffective);		
		mass += bp.mass;
		size += bp.size;
		
		for(BodyPart organ : bp.containedParts) {
			calcTraitsRecursively(organ);
		}
	}
	
	public void calculateTraits() {
		for (BodyPart bp : bodyparts) {
			calcTraitsRecursively(bp);
		}
	}
	
	public void calculateCaloricNeeds() {
		nutrition.updateNeeds(mass);
	}
	
	// Update sense/action traits.
	// Also calculate CaloricNeeds.
	public void updateTraits() {
		moving = eating = talking = consciousness = sight = manipulation = breathing = 0;
		mass = size = 0;
		calculateTraits();
		calculateCaloricNeeds();
	}
	
	public void addPart(BodyPart part) {		
		bodyparts.add(part);
		
		updateTraits();
	}
	
	public String nutritionType() { 
		return nutrition.type; 
	}
	
	public HashMap<String, Double> nutritionalNeeds() {
		
		double growthModifier = 1.0;
		
		if(age < species.timeTillMaturation) {
			growthModifier = 1 + (growthDerivativeEstimate() / species.finalSize);
		}
		
		HashMap<String, Double> nutritionNeeds = nutrition.nutrition();
		for(String need : nutritionNeeds.keySet()) {
			nutritionNeeds.put(need, nutritionNeeds.get(need) * growthModifier);
		}
		
		return nutritionNeeds;
	}
	
	/*
	 * Returns an estimate for the expected derivative of: growthFunction(now + 1) - growthFunction(now) 
	 */
	public double growthDerivativeEstimate() {
		return growthFunction(age + 1) - growthFunction(age);
	}
	
	/*
	 * Returns the expected size at a given age. 
	 */
	public double growthFunction(int age) {
		return (species.finalSize - species.initialSize) * Math.sin((age + (Math.PI/2)) / species.timeTillMaturation) + species.initialSize;
	}
	
	public void growToSize(double targetSize) {
		
		double ratioToScaleBy = targetSize / size;
		
		for(BodyPart bp : bodyparts) {
			bp.scale(ratioToScaleBy);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Age: " + age + "\n");
		sb.append("Maturity at: " + species.timeTillMaturation + "\n");
		sb.append("Mass: " + mass + "\n");
		sb.append("Nutritional Health: " + nutritionalHealth + "\n");
		sb.append("Moving: " + moving + "\n");
		sb.append("Eating: " + eating + "\n");
		sb.append("Talking: " + talking + "\n");
		sb.append("Consciousness: " + consciousness + "\n");
		sb.append("Sight: " + sight + "\n");
		sb.append("Manipulation: " + manipulation + "\n");
		sb.append("Breathing: " + breathing + "\n");
		sb.append("Bodyparts:\n");
		
		for(BodyPart bp : bodyparts) {
			sb.append(bp.toString());
		}
		
		sb.append("\n");
		sb.append("Nutrition: " + nutritionalNeeds().toString() + "\n");
		
		return sb.toString();
	}
	
	/*
	 * Copies the structure of the body.
	 * TODO: Need/want a better way of doing this. For lots of entities, this seems pretty excessive.
	 */
	public Body copyStructure(int age, Species species) {
		
		Body copy = new Body(nutrition);
		copy.age = age;
		copy.species = species;
		
		double scale = Math.min((double) age / species.timeTillMaturation, 1.0);
		if(age == 0) {
			scale = (double) species.initialSize / size; 
		} else {
			scale *= (double) species.finalSize / size;
		}
		
		for(BodyPart bp : bodyparts) {
			BodyPart b = new BodyPart(bp.name);
			b.scale(scale);
			
			for(BodyPart organ : bp.containedParts) {
				BodyPart org = new BodyPart(organ.name);
				org.scale(scale);
				b.addOrgan(org);
			}
			
			copy.addPart(b);
		}
		
		return copy;
	}
}
