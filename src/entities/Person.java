package entities;

public class Person {
	
	public static final double CALORIES_NEEDED_PER_DAY = 2000;

	private String name;
	private String faction;
	private boolean gender; // T == Fe, F == M
	private double happiness; // -100 - 100
	private double strength; // 0 - 20
	
	/*
	 * Default constructor - intended to be an easy test person.
	 */
	public Person(){
		name = "Bitzy";
		faction = "Testers";
		gender = false;
		happiness = 100;
		strength = 20;
	}
	
	// Standard setters.
	public void setName(String name){ this.name = name; }
	public void setFaction(String faction){ this.faction = faction; }
	public void setGender(boolean gender){ this.gender = gender; }
	public void setHappiness(double happiness){ this.happiness = happiness; }
	public void setStrength(double strength){ this.strength = strength; }
	
	// Standard getters.
	public String name(){ return name; }
	public String faction(){ return faction; }
	public boolean gender(){ return gender; }
	public double happiness(){ return happiness; }
	public double strength(){ return strength; }
	
	
}
