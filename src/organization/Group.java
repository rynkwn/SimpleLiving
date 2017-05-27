package organization;

import java.awt.Window.Type;
import java.util.*;

import entities.*;
import item.*;
import world.*;

/*
 * A single organized clump of entities.
 */

public class Group {
	public String id;
	public String name;
	
	// Where is the group in the world?
	public World world;
	public BigTile residentTile;
	int x;
	int y;
	
	public ArrayList<Entity> members;
	public Inventory inventory;
	
	public Group(World world, int x, int y) {
		Random rand = new Random();
		
		id = "TEST_GROUP" + rand.nextInt(10000);
		name = id;
		
		this.world = world;
		this.x = x;
		this.y = y;
		residentTile = world.getBigTile(x, y);
		
		members = new ArrayList<Entity>();
		inventory = new Inventory();
	}
	
	public void addMember(Entity e) {
		members.add(e);
	}
	
	/*
	 * Group passes a turn.
	 */
	public void turn() {
		for(int i = 0; i < members.size(); i++) {
			Entity e = members.get(i);
			String nutritionType = e.body.nutrition.type;
			HashMap<String, Double> nutritionalNeeds = e.body.nutrition.nutrition();
			
			if(nutritionType.equals("ANIMAL")) {
				ArrayList<Food> meal = inventory.findThingsToEat(nutritionalNeeds);
				e.eat(meal);
			} else if(nutritionType.equals("PLANT")) {
				
				// TODO: PLANT SETUP IS PRETTY UNSATISFYING.
				
				double water = nutritionalNeeds.get("water");
				double nit = nutritionalNeeds.get("nitrogen");
				double phos = nutritionalNeeds.get("phosphorus");
				double potas = nutritionalNeeds.get("potassium");
				double bio = nutritionalNeeds.get("biomass");
				double satisfaction = residentTile.takeMaterials(water, nit, phos, potas, bio);
				
				e.absorb(satisfaction);
			}
			
			if(e.isDead()) {
				members.remove(i);
				i--;
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(id + " - " + name + "\n");
		sb.append("Members:\n________________________________\n");
		for(Entity e : members) {
			sb.append(e.toString() + "\n");
		}
		
		sb.append("\n\nItems:\n_____________________________\n");
		sb.append(inventory.toString());
		
		return sb.toString();
	}
	
}
