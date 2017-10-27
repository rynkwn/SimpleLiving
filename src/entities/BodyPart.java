package entities;

import java.util.*;

/*
 * Models body parts/appendages.
 */
public class BodyPart {
	
	public String name;
	public double health;
	public double maxHealth;
	
	// The size and mass of the bodypart as it currently exists.
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
		
		health = maxHealth;
		mass = (long) size;
	}
	
	/*
	 * A clone/copy constructor.
	 * This constructor ALSO clones the contained organs!
	 */
	public BodyPart(BodyPart bp) {
		this.name = bp.name;
		
		this.health = bp.health;
		this.maxHealth = bp.maxHealth;
		
		this.size = bp.size;
		this.mass = bp.mass;
		
		this.moving = bp.moving;
		this.eating = bp.eating;
		this.talking = bp.talking;
		this.consciousness = bp.consciousness;
		this.sight = bp.sight;
		this.manipulation = bp.manipulation;
		this.breathing = bp.breathing;
		
		containedParts = new ArrayList<BodyPart>();
		
		for(BodyPart organ : bp.containedParts) {
			BodyPart org = new BodyPart(organ);
			containedParts.add(org);
		}
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
	
	/*
	 * Modify/update some data values, normally because
	 * we've read in a bodypart from JSON and
	 * need to infer some values (if any).
	 */
	public void processJSON() {
		// When we're reading in from JSON, bodypart may
		// not have any organs. In that case, let's just
		// initialize to an empty list to simplify
		// higher level code that loops through
		// body part organs.
		if(containedParts == null) {
			containedParts = new ArrayList<BodyPart>();
		}
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
