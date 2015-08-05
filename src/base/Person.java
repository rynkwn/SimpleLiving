package base;

public class Person {

	private String name;
	private String faction;
	private boolean gender; // T == Fe, F == M
	private double happiness;
	private double strength;
	
	
	
	public Person(){
		name = "Bitzy";
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String name(){
		return name;
	}
	
}
