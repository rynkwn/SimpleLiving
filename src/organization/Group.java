package organization;

import java.util.*;

import entities.*;
import item.*;
import world.*;

/*
 * A single organized clump of entities.
 */

public class Group {
	public ArrayList<Entity> members;
	public Inventory inventory;
	public BigTile residentTile;
	
	public Group(BigTile residentTile) {
		members = new ArrayList<Entity>();
		inventory = new Inventory();
		this.residentTile = residentTile;
	}
	
	
}
