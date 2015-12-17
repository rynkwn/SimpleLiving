package entities;

import java.util.ArrayList;

import Item.Clothing;
import Item.Item;

public class Person {
	
	public static final double CALORIES_NEEDED_PER_DAY = 2000;

	private String name;
	private String faction;
	private boolean gender; // T == Fe, F == M
	private double happiness; // -100 - 100
	private double strength; // 0 - 20
	private ArrayList<Appendage> appendages;
	private ArrayList<Item> inventory;
	
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
	
	// Methods to interact with Items
	public void pickUp(Item item) { inventory.add(item); }
	public Item drop(int index) { return inventory.remove(index);}
	
	public String inventory(){
		String output = "";
		for(int i=0; i < inventory.size(); i++){
			output += inventory.get(i).name() + "\n";
		}
		return output;
	}
	
	public String wearing(){
		String output = "";
		for(int i=0; i < appendages.size(); i++){
			output += appendages.get(i).toString() + "\n";
		}
		return output;
	}
	
	public Clothing wear(Appendage bodypart, Clothing cloth){
		return bodypart.wear(cloth);
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
	
}
