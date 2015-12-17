package Item;

public abstract class Item {

	protected String name;
	protected double value;
	protected double weight;
	protected double condition; // From 0-100. 0 is ruined. 100 is pristine
	
	public Item(){
		name = "TestItem";
		value = 10;
		weight = 1;
		condition = 100;
	}
	
	public Item(String name, double val, double weight, double cond){
		this.name = name;
		this.value = val;
		this.weight = weight;
		this.condition = cond;
	}
	
	public String toString(){
		String output = "";
		output += name + "\t" + value + "\t" + weight + "\t" + condition;
		return output;
	}
	
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
