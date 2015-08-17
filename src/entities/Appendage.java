package entities;

public class Appendage {
	private String name;
	private int health;
	
	public Appendage(){
		name = "test";
		health = 100;
	}
	
	// Setters
	public void setName(String name) { this.name = name; }
	public void setHealth(int health) { this.health = health; }
	
	// Getters
	public String name() { return name; } 
	public int health() { return health; }
}
