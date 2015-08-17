package base;

public abstract class Item {

	protected String name;
	protected double value;
	protected double weight;
	protected double condition; // From 0-100. 0 is ruined. 100 is pristine
	
	
	// Setters
	public void setName(String name) { this.name = name; }
	public void setValue(double value) { this.value = value; }
	public void setWeight(double weight) { this.weight = weight; }
	public void setCondition(double condition) { this.condition = condition; }
		
	// Getters
	public String name() { return name; } 
	public double value() { return value; }
	public double weight() { return weight; }
	public double condition() { return condition; }
}
