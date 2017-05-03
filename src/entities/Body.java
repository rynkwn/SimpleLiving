package entities;

import java.util.*;

/*
 * A wrapper class for an entity's body.
 */
public class Body {
	
	public long caloricNeeds; // Based on total mass.
	
	public long mass;
	
	public int moving; // Number of squares individual can move. 
	public double eating; // Between 0 - 1.
	public double talking;
	public double consciousness;
	public int sight; // 100 -> 20/20 vision.
	public double manipulation; // between 0 - 1.
	public double breathing; // between 0 - 1. Anything less than 20% is dangerous.
	
	ArrayList<BodyPart> bodyparts;
	
	public Body() {
		bodyparts = new ArrayList<BodyPart>();
	}
	
	public void calculateSpeed() {
		for (BodyPart bp : bodyparts) {
			moving += bp.moving;
		}
	}
	
	public void addPart(BodyPart part) {
		bodyparts.add(part);
	}
}
