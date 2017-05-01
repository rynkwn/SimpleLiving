package organization;

import java.util.*;
import entities.*;

// Entity that represents an economic unit. (Family, individual, company, organization, e.t.c.)
// Allocates resources and labors
// Trades with other groups
// May be loyal to another group, which allows special interactions.
// Has group-specific relations, but also "general relations" representing prejudices and values
// May join another group under some conditions
// Owns everything. Has basic needs, will try to overproduce, and will try
	// to grab excess things seen as useful. Allocates to its members.

public class Group {
	public long id; // Let's make an ID to make it easier to abstractly grab what belongs to who.
	
	// Owns buildings, must be in same tile. Let's just unown all buildings once we leave.
	public HashMap<String, Building> claimedBuildings; // Maps building name to actual building.
	public ArrayList<Entity> members;
	
}
