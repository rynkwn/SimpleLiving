package item;

import java.util.HashMap;

/*
 * The Item class is an actual physical object. It does not, however, store any special effects. Presumably, such
 * special effects are easily accessible through the ComponentsReader.
 */
public class Item {
	public String name;
	public String type;
	public double weight;
	public int quantity;
	
	public Item() {
		name = "TEST_ITEM";
		type = "DEBUG";
		weight = 1.0;
		quantity = 1;
	}
	
	public Item(String name, double weight) {
		this.name = name;
		type = "DEBUG";
		this.weight = weight;
		quantity = 1;
	}
	
	public Item(String name, double weight, int quantity) {
		this.name = name;
		type = "DEBUG";
		this.weight = weight;
		this.quantity = quantity;
	}
	
	public Item(String name, String type, double weight, int quantity) {
		this.name = name;
		this.type = type;
		this.weight = weight;
		this.quantity = quantity;
	}
	
	public void setQuantity(int quant) {
		quantity = quant;
	}
	
	public void addQuantity(int quant) {
		quantity += quant;
	}
	
	/*
	 * Removes some amount of the item if possible.
	 * Returns a boolean if process was successful or not.
	 */
	public boolean removeQuantity(int quant) {
		if(quantity >= quant) {
			quantity -= quant;
			return true;
		}
		
		return false;
	}
	
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	
	public String toString() {
		return name + "\n\t" + quantity + "\n\t" + weight;
	}
}
