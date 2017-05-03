package entities;

import java.util.*;

/*
 * Models body parts/appendages.
 */
public class BodyPart {
	
	String name;
	int health;
	int maxHealth;
	
	int size;
	
	// The attributes this body part contributes to.
	int moving;
	boolean eating;
	boolean talking;
	boolean consciousness;
	int sight;
	boolean manipulation;
	boolean breathing;
	
	
	ArrayList<BodyPart> containedParts; // E.g., a head contains a mouth and eyes.
	
	public BodyPart(String name) {
		
		this.name = name;
		containedParts = new ArrayList<BodyPart>();
		
		if(name.equals("Eye")) {
			maxHealth = 5;
			size = 1;
			sight = 50;
		} else if (name.equals("Head")) {
			maxHealth = 20;
			size = 8;
		} else if (name.equals("Mandible")) {
			maxHealth = 10;
			size = 2;
			eating = true;
			talking = true;
		} else if (name.equals("Claw")) {
			maxHealth = 30;
			size = 30;
			manipulation = true;
		} else if (name.equals("Thorax")) {
			maxHealth = 50;
			size = 40;
		} else if (name.equals("Leg")) {
			maxHealth = 20;
			size = 15;
			moving = 1;
		} else if (name.equals("Brain")) {
			maxHealth = 15;
			size = 6;
		} else if (name.equals("Lungs")) {
			maxHealth = 10;
			size = 8;
			breathing = true;			
		}
		
		health = maxHealth;
	}
	
	public void addOrgan(BodyPart organ) {
		containedParts.add(organ);
	}

}
