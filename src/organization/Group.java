package organization;

import java.util.*;

import entities.*;
import item.*;
import world.*;

/*
 * A single organized clump of entities.
 */

public class Group {
	String id;
	String name;
	
	// Where is the group in the world?
	World world;
	BigTile residentTile;
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
	
}
