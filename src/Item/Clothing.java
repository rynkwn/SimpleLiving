package Item;

public class Clothing extends Item {

	private String appendage; // Valid appendage(s)
	private double armor;
	private double warmth;
	
	public Clothing(){
		super("Nothing", 0, 0, 100);
		appendage = "";
		armor = 0;
		warmth = 1;
	}
	
	public Clothing(String name){
		super(name, 5, .1, 100);
		appendage = "Body";
		armor = 0;
		warmth = 1;
	}
	
	// Setters
	public void setAppendage(String appendage) { this.appendage = appendage; }
	public void setArmor(double armor) { this.armor = armor; }
	public void setWarmth(double warmth) { this.warmth = warmth; }
	
	// Getters
	public String appendage() { return appendage; }
	public double armor() { return armor; }
	public double warmth() { return warmth; }
}
