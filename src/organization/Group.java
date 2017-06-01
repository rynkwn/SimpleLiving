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
		e.group = this;
	}
	
	public void addMembers(List<Entity> entities) {
		for(Entity e : entities) {
			addMember(e);
		}
	}
	
	public Entity removeMember(int i) {
		return members.remove(i);
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	/*
	 * Creates a new group in the same square, according to some proportion.
	 * Splits by members.
	 */
	public Group split(double pct) {
		int numRemoved = (int) Math.floor(members.size() * pct);
		
		Group grp = new Group(world, x, y);
		
		for(int i = 0; i < numRemoved; i++) {
			grp.addMember(removeMember(0));
		}
		
		return grp;
	}
	
	/*
	 * Gives some items to a specified group.
	 */
	public void give(Group grp) {
		
	}
	
	/*
	 * Return the relationship towards a particular group.
	 */
	public int relations(Group grp) {
		return -100;
	}
	
	/*
	 * Tries to move in a specified direction.
	 */
	public boolean move(Direction d) {
		
		switch(d) {
		case NORTH:
			return world.move(this, x, y, x, y - 1);
		case EAST:
			return world.move(this, x, y, x + 1, y);
		case WEST:
			return world.move(this, x, y, x - 1, y);
		case SOUTH:
			return world.move(this, x, y, x, y + 1);
		}
		
		return false;
	}
	
	/*
	 * Group passes a turn.
	 */
	public void turn() {
		for(int i = 0; i < members.size(); i++) {
			Entity e = members.get(i);
			
			e.turn();
			
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
		
		sb.append("\n\nTile:\n_____________________________\n");
		sb.append(residentTile.toString());
		
		sb.append("\n\nItems:\n_____________________________\n");
		sb.append(inventory.toString());
		
		return sb.toString();
	}
	
}
