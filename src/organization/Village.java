package organization;

import java.util.ArrayList;

import Item.Item;
import entities.Person;

public class Village {
	private String name;
	private String faction;
	
	private ArrayList<Person> inhabitants;
	private ArrayList<Item> market;
	private ArrayList<Workbloc> workplaces;
	
	public Village() {
		name = "Solace";
		faction = "Independent";
		
		inhabitants = new ArrayList<Person>();
		market = new ArrayList<Item>();
		
		inhabitants.add(new Person());
		inhabitants.add(new Person());
	}
	
	public void iterate() {
		
	}
}
