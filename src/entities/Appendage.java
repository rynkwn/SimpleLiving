package entities;

import Item.Clothing;

public class Appendage {
	private String name;
	private int health;
	private Clothing cover;
	
	public Appendage(){
		name = "test";
		health = 100;
		cover = new Clothing();
	}
	
	public Appendage(String bodypart){
		name = bodypart;
		health = 100;
		cover = new Clothing();
	}
	
	public Appendage(String bodypart, int health){
		name = bodypart;
		this.health = health;
		cover = new Clothing();
	}
	
	public Appendage(String bodypart, int health, Clothing cover){
		name = bodypart;
		this.health = health;
		this.cover = cover;
	}
	
	// Interacting with Clothing.
	public Clothing wear(Clothing cover) { 
		if(cover.appendage().equals(name)) {
			if(!this.cover.name().equals("Nothing")) { // If already wearing something.
				Clothing temp = this.cover;
				this.cover = cover;
				return temp;
			} else {
				this.cover = cover;
				return null;
			}
		} else {
			return null;
		}
	}
	
	public boolean canWear(Clothing cover){
		if(cover.appendage().equalsIgnoreCase(name))
			return true;
		return false;
	}
	
	// Returns a brief one line description of the appendage.
	public String toString(){
		String output = "";
		output += name + "\t" + health + "\t" + cover.name();
		return output;
	}
	
	// Setters
	public void setName(String name) { this.name = name; }
	public void setHealth(int health) { this.health = health; }
	
	// Getters
	public String name() { return name; } 
	public int health() { return health; }
	public Clothing getCover() { return cover; }
}
