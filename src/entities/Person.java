package entities;

import item.Clothing;
import item.Item;

import java.util.ArrayList;
import java.util.Random;

public class Person {

	private String name;
	private String faction;
	private boolean gender; // T == Fe, F == M
	private double happiness; // -100 - 100
	private double strength; // 0 - 20
	private ArrayList<Appendage> appendages;
	private ArrayList<Item> inventory;
	private long ID = new Random().nextLong();
	private int labor = 0;
	
	/*
	 * Default constructor - intended to be an easy test person.
	 */
	public Person(){
		name = "Bitzy";
		faction = "Testers";
		gender = false;
		happiness = 100;
		strength = 20;
		
		appendages = new ArrayList<Appendage>();
		appendages.add(new Appendage("Body", 100));
		
		inventory = new ArrayList<Item>();
	}
	
	/*
	 * Methods to interact with items.
	 */
	public void pickUp(Item item) { inventory.add(item); }
	public Item drop(int index) { return inventory.remove(index);}
	public void destroy(String itemName) {
		for(int i = 0; i < inventory.size(); i++) {
			if(itemName.equals(inventory.get(i).name())) {
				inventory.remove(i);
				break;
			}
		} 
	}
	
	// A string description of the user's inventory.
	// If empty, states "Nothing is being carried!"
	public String inventory(){
		String output = "Inventory:\n";
		if(inventory.size() == 0){
			return "Empty";
		} else {
			for(int i=0; i < inventory.size(); i++){
				output += inventory.get(i).toString() + "\n";
			}
			return output;			
		}
	}
	
	// A String description of all the Person's body parts and the name of
	// the covering object.
	public String wearing(){
		String output = "Body:\n";
		for(int i=0; i < appendages.size(); i++){
			output += appendages.get(i).toString() + "\n";
		}
		return output;
	}
	
	public void wear(int appendageIndex, int inventoryIndex){
		Item item = inventory.get(inventoryIndex);
		Appendage appen = appendages.get(appendageIndex);
		
		if(item instanceof Clothing){
			if(appen.canWear((Clothing) item)){
				Clothing old = appen.wear((Clothing) item);
				if(old != null)
					inventory.add(old);
				inventory.remove(inventoryIndex);
			} else {
				System.out.println(item.name() + " won't fit there!");
			}
		} else {
			System.out.println(item.name() + " is not wearable!");
		}
		
	}
	
	/*
	 * Methods to interact with appendages.
	 */
	public Appendage getAppendage(int index){
		return appendages.get(index);
	}
	
	// Returns the first appendage that matches the passed in String.
	// Case insensitive.
	public Appendage getAppendage(String bodypart){
		for(int i = 0; i < appendages.size(); i++){
			if(appendages.get(i).name().equalsIgnoreCase(bodypart))
				return appendages.get(i);
		}
		return null;
	}
	
	public void act() {
		labor = (int) strength;
		
	}
	
	// Standard setters.
	public void setName(String name){ this.name = name; }
	public void setFaction(String faction){ this.faction = faction; }
	public void setGender(boolean gender){ this.gender = gender; }
	public void setHappiness(double happiness){ this.happiness = happiness; }
	public void setStrength(double strength){ this.strength = strength; }
	public void setAppendages(ArrayList<Appendage> appendages) { this.appendages = appendages; }
	
	// Standard getters.
	public String name(){ return name; }
	public String faction(){ return faction; }
	public boolean gender(){ return gender; }
	public double happiness(){ return happiness; }
	public double strength(){ return strength; }
	public ArrayList<Appendage> appendages(){ return appendages; }
	public long getID(){ return ID; }
	
}
