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
	
	public void turn() {
		for(Entity e : members) {
			String nutritionType = e.body.nutrition.type;
			HashMap<String, Double> nutritionalNeeds = e.body.nutrition.nutrition();
			
			if(nutritionType.equals("ANIMAL")) {
				
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
