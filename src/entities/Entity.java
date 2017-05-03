package entities;

public class Entity {
	public String name;
	public String species;
	
	public long caloricNeeds; // Based on total mass.
	
	public int moving; // Number of squares individual can move. 
	public double eating; // Between 0 - 1.
	public double talking;
	public double consciousness;
	public int sight; // 100 -> 20/20 vision.
	public double manipulation; // between 0 - 1.
	public double breathing; // between 0 - 1. Anything less than 20% is dangerous.
	
	public boolean isDead() {
		return false;
	}
	
	
}
