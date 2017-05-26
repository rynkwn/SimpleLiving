package entities;

import java.util.*;

/*
 * A wrapper class for an entity's body.
 */
public class Body {
	
	public long mass;
	
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
	
	public void calcTraitsRecursively(BodyPart bp) {
		double pctEffective = bp.health / bp.maxHealth;
		
		moving += (bp.moving * pctEffective);
		eating = Math.max(eating, (bp.eating ? 1 : 0) * pctEffective);
		talking = Math.max(talking, (bp.talking ? 1 : 0) * pctEffective);
		consciousness = Math.max(consciousness, (bp.consciousness ? 1 : 0) * pctEffective);
		sight += (bp.sight * pctEffective);
		manipulation = Math.max(manipulation, (bp.manipulation ? 1 : 0) * pctEffective);
		breathing = Math.max(breathing, (bp.breathing ? 1 : 0) * pctEffective);
		mass += bp.size;
		
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
	
	public void updateTraits() {
		moving = eating = talking = consciousness = sight = manipulation = breathing = 0;
		calculateTraits();
		calculateCaloricNeeds();
	}
	
	public void addPart(BodyPart part) {		
		bodyparts.add(part);
		
		updateTraits();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Mass: " + mass + "\n");
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
		sb.append("Nutrition: " + nutrition.toString() + "\n");
		
		return sb.toString();
	}
	
	/*
	 * Copies the structure of the body.
	 * TODO: Need/want a better way of doing this. For lots of entities, this seems pretty excessive.
	 */
	public Body copyStructure() {
		
		Body copy = new Body(nutrition);
		
		for(BodyPart bp : bodyparts) {
			BodyPart b = new BodyPart(bp.name);
			
			for(BodyPart organ : b.containedParts) {
				b.addOrgan(new BodyPart(organ.name));
			}
		}
		
		return copy;
	}
}
