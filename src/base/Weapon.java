package base;

public class Weapon extends Item {

	private double damage;
	/*
	 * Unsure about the below. Combat is not nearly fleshed out enough.
	 * private double ap; // Armor piercing
	 * private double range; 
	 * private boolean large;
	 */
	
	// Setters
	public void setDamage(double dam) { this.damage = dam; }
	
	// Getters
	public double damage() { return damage; }
}
