package world;

import java.util.*;

import organization.*;
import util.*;
import ecology.EcoTile;

/*
 * The world!
 */
public class World {
	public static final int MIN_TEMP = 5;
	public static final int MAX_TEMP = 40;
	public static final int TEMP_VARIANCE = 2;
	
	public BigTile[][] map;
	public EcoTile[][] ecology;
	public int length;
	public int width;
	
	public HashMap<String, Group> groups;
	
	public World(int length, int width, int seed) {
		map = new BigTile[length][width];
		ecology = new EcoTile[length][width];
		
		this.length = length;
		this.width = width;
		
		double[][] waterRates = ValueNoise.generateValueNoise(length, width, seed, 3, .5);
		int[][] tempDistribution = GradientNoise.gradientNoise(length, width, seed, 
				MIN_TEMP, MAX_TEMP, TEMP_VARIANCE, GradientNoise.GRADIENT_VERTICAL
				);
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				map[i][j] = new BigTile(waterRates[i][j], tempDistribution[i][j]);
				ecology[i][j] = new EcoTile(this, i, j, map[i][j]);
			}
		}
		
		groups = new HashMap<String, Group>();
	}
	
	public void turn() {
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				map[i][j].turn(1);
				ecology[i][j].turn();
			}
		}
		
		for(String id : groups.keySet()) {
			groups.get(id).turn();
		}
	}
	
	public String display() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				sb.append(map[i][j].display());
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public String dumpGroups() {
		StringBuilder sb = new StringBuilder();
		
		for(String id : groups.keySet()) {
			sb.append(groups.get(id).toString() + "\n");
		}
		
		return sb.toString();
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
	
	// Add some species + numbers to a certain tile.
	public void addWildSpeciesToTile(int x, int y, HashMap<String, Integer> pops) {
		ecology[x][y].addPops(pops);
	}
	
	public boolean groupPresentAtCoord(Group grp, int x, int y) {
		return map[x][y].containsGroup(grp);
	}
	
	/*
	 * Tries to move a group, located at X and Y, to new coordinates newX and newY.
	 */
	public boolean move(Group grp, int x, int y, int newX, int newY) {
		if(inBounds(x, y) && groupPresentAtCoord(grp, x, y) && inBounds(newX, newY)) {
			map[x][y].removeGroup(grp);
			map[newX][newY].addGroup(grp);
			
			return true;
		}
		
		return false;
	}
}
