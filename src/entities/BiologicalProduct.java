package entities;

/*
 * A class that holds the triple: ITEM PRODUCED - % PRODUCED AS RATIO OF SIZE - TIME TO PRODUCE
 */
public class BiologicalProduct {
	public String name;
	public double productionRatio;
	public int timeToProduce;
	
	public BiologicalProduct(String name, double productionRatio, int timeToProduce) {
		this.name = name;
		this.productionRatio = productionRatio;
		this.timeToProduce = timeToProduce;
	}
	
	public String toString() {
		return name + " : " + productionRatio + " : " + timeToProduce;
	}

}
