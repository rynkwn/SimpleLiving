package item;

/*
 * A class representing food and drink.
 */
public class Food extends Item {
	
	public double thirst;
	public double nitrogen;
	public double phosphorus;
	public double potassium;
	public double biomass;
	
	public Food(String name, 
			double weight, 
			int quantity, 
			double thirst, 
			double nitrogen,
			double phosphorus,
			double potassium,
			double biomass) {
		
		super(name, weight, quantity);
		this.thirst = thirst;
		this.nitrogen = nitrogen;
		this.phosphorus = phosphorus;
		this.potassium = potassium;
		this.biomass = biomass;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		
		sb.append("type: FOOD" + "\n");
		sb.append("thirst: " + thirst + "\n");
		sb.append("nitrogen: " + nitrogen + "\n");
		sb.append("phosphorus: " + phosphorus + "\n");
		sb.append("potassium: " + potassium + "\n");
		sb.append("biomass: " + biomass + "\n");
		
		return sb.toString();
	}
}
