package item;

import java.util.HashMap;

/*
 * The Item class is an actual physical object. It does not, however, store any special effects. Presumably, such
 * special effects are easily accessible through the ComponentsReader.
 */
public class Item {
	public String name;
	public double weight;
	public int quantity;
	
	public Item(String name, double weight) {
		this.name = name;
		this.weight = weight;
		quantity = 1;
	}
	
	public Item(String name, double weight, int quantity) {
		this.name = name;
		this.weight = weight;
		this.quantity = quantity;
	}
	
	public void setQuantity(int quant) {
		quantity = quant;
	}
	
	public void addQuantity(int quant) {
		quantity += quant;
	}
	
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	
	public String toString() {
		return name + "\n\t" + quantity + "\n\t" + weight;
	}
}
