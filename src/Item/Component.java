package item;

import java.util.HashMap;

/*
 * The Component class is an actual physical object. It does not, however, store any special effects. Presumably, such
 * special effects are easily accessible through the ComponentsReader.
 */
public class Component {
	public String name;
	public double weight;
	public int quantity;
	
	public Component(String name, double weight) {
		this.name = name;
		this.weight = weight;
		quantity = 1;
	}
	
	public Component(String name, double weight, int quantity) {
		this.name = name;
		this.weight = weight;
		this.quantity = quantity;
	}
	
	public void setQuantity(int quant) {
		quantity = quant;
	}
	
}
