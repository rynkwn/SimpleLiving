package entities;

public class Entity {
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public Body body;
	
	public Entity(String speciesName) {
		
	}
	
	public boolean isDead() {
		return false;
	}
	
	
}
