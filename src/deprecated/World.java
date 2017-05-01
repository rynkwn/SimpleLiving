package world;

/*
 * The World class contains a set of Tiles.
 */

public class World {
	public Tile[][] tiles;
	int length;
	int width;
	
	public World(int x, int y) {
		length = x;
		width = y;
		tiles = new Tile[length][width];
	}
	
	public void generateNewTiles() {
		for(int x = 0; x < length; x++) {
			for(int y = 0; y < width; y++) {
				tiles[x][y] = new Tile();
			}
		}
	}
}
