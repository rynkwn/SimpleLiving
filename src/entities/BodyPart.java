package entities;

import java.util.*;

/*
 * Models body parts/appendages.
 */
public class BodyPart {
	
	public String name;
	public double health;
	public double maxHealth;
	
	public double adultSize;
	public double adultMass;
	
	public double size;
	public double mass;
	
	// The attributes this body part contributes to.
	public double moving = 0;
	public boolean eating = false;
	public boolean talking = false;
	public boolean consciousness = false;
	public double sight = 0;
	public boolean manipulation = false;
	public boolean breathing = false;
	
	
	ArrayList<BodyPart> containedParts; // E.g., a head contains a mouth and eyes.
	
	public BodyPart(String name) {
		
		this.name = name;
		containedParts = new ArrayList<BodyPart>();
		
		if(name.equals("Eye")) {
			maxHealth = 5;
			adultSize = 1;
			sight = 50;
		} else if (name.equals("Head")) {
			maxHealth = 20;
			adultSize = 8;
		} else if (name.equals("Mandible")) {
			maxHealth = 10;
			adultSize = 2;
			eating = true;
			talking = true;
		} else if (name.equals("Claw")) {
			maxHealth = 30;
			adultSize = 30;
			manipulation = true;
		} else if (name.equals("Thorax")) {
			maxHealth = 50;
			adultSize = 40;
		} else if (name.equals("Leg")) {
			maxHealth = 20;
			adultSize = 15;
			moving = 1;
		} else if (name.equals("Brain")) {
			maxHealth = 15;
			adultSize = 6;
		} else if (name.equals("Lungs")) {
			maxHealth = 10;
			adultSize = 8;
			breathing = true;
		} else if (name.equals("Segment")) {
			maxHealth = 40;
			adultSize = 50;
			moving = .5;
		}
		
		health = maxHealth;
		size = adultSize;
		mass = (long) size;
	}
	
	public void scale(double pct) {
		size = Math.max(size * pct, 1.0);
		mass = Math.max(mass * pct, 1);
		
		for(BodyPart organ : containedParts) {
			organ.scale(pct);
		}
	}
	
	public void addOrgans(ArrayList<BodyPart> organs) {
		for(BodyPart organ : organs) {
			containedParts.add(organ);
		}
	}
	
	public void addOrgan(BodyPart organ) {
		containedParts.add(organ);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + "{");
		for(BodyPart bp : containedParts) {
			sb.append(bp.toString());
		}
		
		sb.append("}, ");
		
		return sb.toString();
	}

}
