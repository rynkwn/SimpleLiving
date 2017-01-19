package Item;

public class Consumable extends Item {
	
	// What sort of consumables?
	// In the short term: hungry and thirst management.
	// In the longer term: drug cocktails for performance/otherwise.
	// Status effects! Drunknesses. Poisons. E.t.c.
	
	private double quenching; // Thirst
	private double satisfying; // Hunger

	private String effect; // Type of effect.
	private double intensity;
	
	// Setters
	public void setEffect(String effect) { this.effect = effect; }
	public void setIntensity(double intensity) { this.intensity = intensity; }
	
	// Getters 
	public String effect() { return effect; }
	public double intensity() { return intensity; }
}
