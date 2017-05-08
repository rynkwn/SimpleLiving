package item;

/*
 * Holds information about a class of item.
 */
class AbstractItem {
	public String name;
	public String type;
	public double base_weight;
	public double thirst_quenching;
	public double nitrogen;
	public double phosphorus;
	public double potassium;
	public double biomass;
	
	public AbstractItem(String name,
			String type,
			double base_weight,
			double thirst_quenching,
			double nitrogen,
			double phosphorus,
			double potassium,
			double biomass) {
		
		this.name = name;
		this.type = type;
		this.base_weight = base_weight;
		this.thirst_quenching = thirst_quenching;
		this.nitrogen = nitrogen;
		this.phosphorus = phosphorus;
		this.potassium = potassium;
		this.biomass = biomass;
	}
	
	/*
	 * Makes a component that matches this pattern.
	 */
	public Item makeItem() {
		if(type == "FOOD") {
			return new Food(name, base_weight, 1, thirst_quenching, nitrogen, phosphorus, potassium, biomass);
		}
		
		return new Item();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("name: " + name + "\n");
		sb.append("type: " + type + "\n");
		sb.append("base_weight: " + base_weight + "\n");
		sb.append("thirst: " + thirst_quenching + "\n");
		sb.append("nitrogen: " + nitrogen + "\n");
		sb.append("phosphorus: " + phosphorus + "\n");
		sb.append("potassium: " + potassium + "\n");
		sb.append("biomass: " + biomass + "\n");
		
		
		return sb.toString();
	}
}
