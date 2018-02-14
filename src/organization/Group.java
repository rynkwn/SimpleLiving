package organization;

import java.util.*;

import data.LaborPool;
import ecology.EcoTile;
import entities.*;
import item.*;
import world.*;
import behavior.*;

/*
 * A single organized clump of entities.
 */

public class Group {	
	public String id;
	public String name;
	
	// Where is the group in the world?
	public World world;
	public BigTile residentTile;
	public EcoTile ecoTile;
	public int x;
	public int y;
	
	public ArrayList<Entity> members;

	// Inventory related attributes
	public Inventory inventory;
	public double maxWeight;

	// Group behaviors
	public GroupBehaviorType behavior;
	
	// Labor pool. Determined near the beginning of every turn
	// after entities pass their own turns. Then allocated to projects.
	public LaborPool availableLabor;
	
	// Active projects.
	public HashMap<Project, Double> projects;
	
	public Group(World world, int x, int y) {
		Random rand = new Random();
		
		id = "TEST_GROUP" + rand.nextInt(10000);
		name = id;
		
		this.world = world;
		this.x = x;
		this.y = y;
		residentTile = world.getBigTile(x, y);
		ecoTile = world.getEcoTile(x, y);
		
		members = new ArrayList<Entity>();
		inventory = new Inventory();
		maxWeight = 0.0;
		
		availableLabor = new LaborPool(0);
		projects = new HashMap<Project, Double>();
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
	 * Drop an item into the local BigTile.
	 */
	public void dropItem(String itemName, int quantity) {
		residentTile.receiveItem(inventory.remove(itemName, quantity));
	}

	/*
	 * Drop the specified item. Also removes it from our inventory.
	 */
	public void dropItem(Item item) {
		residentTile.receiveItem(inventory.remove(item));
	}

	/*
	 * Drop the specified list of items. Also removes them from our inventory.
	 */
	public void dropItems(List<Item> items) {
		residentTile.receiveItems(inventory.remove(items));
	}
	
	public void setBehavior(GroupBehaviorType behavior) {
		this.behavior = behavior;
	}
	
	/*
	 * Add a project to the queue of things worked on.
	 */
	public void addProject(Project p) {
		projects.put(p, 1.0);
	}
	
	/*
	 * Creates a new group in the same square, according to some proportion.
	 * Splits by members, and also adds that group to the world.
	 */
	public Group split(double pct) {
		int numRemoved = (int) Math.floor(members.size() * pct);
		
		Group grp = new Group(world, x, y);
		grp.setBehavior(behavior);
		
		for(int i = 0; i < numRemoved; i++) {
			grp.addMember(removeMember(0));
		}
		
		// TODO: Desirable? Feels really messy.
		// world.addGroup(grp.id, grp, x, y);
		return grp;
	}
	
	/*
	 * Tries to give a set of items to a specified group.
	 */
	public void give(Group grp, HashMap<String, Integer> itemsToGive) {
		ArrayList<Item> items = new ArrayList<Item>();
		
		for(String itemName : itemsToGive.keySet()) {
			items.add(inventory.remove(itemName, itemsToGive.get(itemName)));
		}
		
		grp.inventory.addList(items);
	}
	
	/*
	 * Returns the size of the group.
	 */
	public int size() {
		return members.size();
	}
	
	/*
	 * Tries to move in a specified direction.
	 */
	public boolean move(Direction d) {
		
		switch(d) {
		case NORTH:
			if(world.move(this, x, y, x, y - 1)) {
				y = y -1;
				residentTile = world.getBigTile(x, y);
				ecoTile = world.getEcoTile(x, y);
				return true;
			}
		case EAST:
			if(world.move(this, x, y, x + 1, y)) {
				x = x + 1;
				residentTile = world.getBigTile(x, y);
				ecoTile = world.getEcoTile(x, y);
				return true;
			}
		case WEST:
			if(world.move(this, x, y, x - 1, y)) {
				x = x - 1;
				residentTile = world.getBigTile(x, y);
				ecoTile = world.getEcoTile(x, y);
				return true;
			}
		case SOUTH:
			if(world.move(this, x, y, x, y + 1)) {
				y = y + 1;
				residentTile = world.getBigTile(x, y);
				ecoTile = world.getEcoTile(x, y);
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Randomly moves to an adjacent square.
	 */
	public void randomMove() {
		Random rand = new Random();
		
		boolean moved = false;
		int dir = rand.nextInt(4);
		
		while(!moved) {
			switch(dir) {
			case 0:
				moved = move(Direction.NORTH);
				break;
			case 1:
				moved = move(Direction.EAST);
				break;
			case 2:
				moved = move(Direction.SOUTH);
				break;
			case 3:
				moved = move(Direction.WEST);
				break;
			}
			
			dir = rand.nextInt(4);
		}
	}
	
	/*
	 * Try to feed a specified entity from the local tile (if applicable) or from
	 * the stocks of this group.
	 */
	public void feedEntity(Entity e) {
		String nutritionType = e.body.nutritionType();
		HashMap<String, Double> nutritionalNeeds = e.body.nutritionalNeeds();
		
		if(nutritionType.equals("ANIMAL")) {
			ArrayList<Food> meal = inventory.findThingsToEat(nutritionalNeeds);
			e.eat(meal);
			
		} else if(nutritionType.equals("PLANT")) {
			double satisfaction = residentTile.takeMaterials(e.body.nutrition.getMacronutrients());
			
			e.absorb(satisfaction);
		}
	}
	
	/*
	 * Group passes a turn.
	 */
	public void turn() {
		
		availableLabor.zero();
		maxWeight = 0.0;
		
		// For every entity, have it take a turn and feed itself.
		for(int i = 0; i < members.size(); i++) {
			Entity e = members.get(i);
			
			e.turn();
			feedEntity(e);
			
			// If the entity is dead, remove it from our list of members.
			if(e.isDead()) {
				members.remove(i);
				i--;
			} else {
				// The entity is alive, so we can add its labor to our labor pool.
				// We can also add any weight capacity the entity has to our maxWeight.
				availableLabor.add(e.labor);
				maxWeight += e.getMaximumWeightCapacity();
			}
		}
		
		// Execute the next step in its behavior chain.
		GroupBehavior.execute(this, behavior);
		
		// Do work on projects, if any. It is important that this is after we
		// execute group behavior, as that's when we'll initialize/instantiate projects for AI.
		for(Project proj : projects.keySet()) {
			if(!availableLabor.isZero())
				proj.work(availableLabor, projects.get(proj));
		}
		
		// Remove completed projects.
		ArrayList<Project> completedProjects = new ArrayList<Project>();
		for(Project proj : projects.keySet()) {
			if(!proj.valid) {
				completedProjects.add(proj);
			}
		}
		
		for(Project completed : completedProjects) {
			projects.remove(completed);
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
		
		sb.append("\n\nLocal Ecology:\n_____________________________\n");
		sb.append(ecoTile.toString());
		
		sb.append("\n\nItems:\n_____________________________\n");
		sb.append("Maximum Weight: " + maxWeight + "\n");
		sb.append(inventory.toString());

		sb.append("\n\nCollective Labor Pool:\n_____________________________\n");
		sb.append(availableLabor.toString());		
		
		sb.append("\n\nProjects:\n_____________________________\n");
		sb.append(projects.toString());
		
		return sb.toString();
	}
	
}
