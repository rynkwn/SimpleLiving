package base;

public class Consumable extends Item {

	private String effect; // Type of effect.
	private double intensity;
	
	// Setters
	public void setEffect(String effect) { this.effect = effect; }
	public void setIntensity(double intensity) { this.intensity = intensity; }
	
	// Getters 
	public String effect() { return effect; }
	public double intensity() { return intensity; }
}
