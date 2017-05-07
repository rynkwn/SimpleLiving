package entities;

public class Entity {
	public String name;
	public String species;

	public int timeSinceLastBirth; // How long it's been since this entity has last reproduced.
	public Body body;
	
	public Entity(String name, String species, int timeSinceLastBirth, Body body) {
		this.name = name;
		this.species = species;
		this.timeSinceLastBirth = timeSinceLastBirth;
		this.body = body;
	}
	
	public boolean isDead() {
		return false;
	}
	
	
}
