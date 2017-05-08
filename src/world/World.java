package world;

import java.util.*;

import organization.*;

/*
 * The world!
 */
public class World {
	public BigTile[][] map;
	public int length;
	public int width;
	
	public HashMap<String, Group> groups;
	
	public World(int length, int width) {
		map = new BigTile[length][width];
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				map[i][j] = new BigTile();
			}
		}
		
		groups = new HashMap<String, Group>();
	}
	
	/*
	 * Adds a group at a specified location
	 */
	public void addGroup(String id, Group grp, int x, int y) {
		if(inBounds(x, y)) {
			groups.put(id, grp);
			map[x][y].addGroup(grp);
		}
	}
	
	public BigTile getBigTile(int x, int y) {
		if(inBounds(x, y)) {
			return map[x][y];
		}
		
		return null;
	}
	
	public boolean inBounds(int x, int y) {
		if(x >= 0 && x < length && y >= 0 && y < width) {
			return true;
		}
		
		return false;
	}
}
