package com.duke.maps;

/**
 * Extendable map class, stands for Duke Map.
 * 
 * @author Duke
 *
 */
public class DMap {
	
	/**
	 * Tiles contained within map.
	 */
	protected Tile[][] tiles;
	
	/**
	 * Width of map.
	 */
	protected int width;
	
	/**
	 * Height of map.
	 */
	protected int height;
	
	/**
	 * Constructor that essentially copies the inputted Tile 2D array to itself
	 * Determines width and height based on inputted tiles size.
	 * @param tiles Tiles to be copied into the map.
	 */
	public DMap(Tile[][] tiles) {
		this.tiles = tiles;
		this.width = this.tiles.length;
		this.height = this.tiles[0].length;
	}
	
	/**
	 * Really just a copy constructor.
	 * @param m Map that is to be copied.
	 */
	public DMap(DMap m) {
		this.tiles = m.tiles;
		this.width = m.width;
		this.height = m.height;
	}
	
	/**
	 * Used to generate a random map using MapGenerator.
	 * Uses MapGenerator's build method to copy returned DMap to this map.
	 * @param gen MapGenerator to generate random map.
	 * @see MapGenerator MapGenerator
	 */
	public DMap(MapGenerator gen) {
		this(gen.build());
	}
	
	/**
	 * Basically makes a map that you can mess around with the tiles.
	 * Initializes tiles, then defines them to be {@link Tile.BOUNDS}
	 * @param w width of map
	 * @param h height of map 
	 */
	public DMap(int w, int h) {
		this.width = w;
		this.height = h;
		this.tiles = new Tile[width][height];
		clear();
	}
	
	/**
	 * Load the DMap from file.
	 * @param fileName file name (including path) of map. Map file should be a .txt file.
	 */
	public DMap(String fileName) {
		this(DMap.loadFromFile(fileName));
	}
	
	/**
	 * Forces all tiles in map to be {@link Tile.BOUNDS}.
	 */
	protected void clear() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tiles[x][y] = Tile.BOUNDS;
			}
		}
	}
	
	/**
	 * Checks if given x and y coords are within map.
	 * @param x x coordinate to be checked
	 * @param y y coordinate to be checked.
	 * @return True if given coordinates are in bounds. False otherwise.
	 */
	public boolean inBounds(int x, int y) {
		return 	x >= 0 && x < this.width &&
				y >= 0 && y < this.height;
	}
	
	/**
	 * Get tile at given x and y coordinates.
	 * Checks if coordinates are in bounds. 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return {@link Tile.BOUNDS} if out of bounds, the tile at the given x and y coordinates otherwise.
	 * 
	 * @see DMap#inBounds(int, int) DMap.inBounds(int, int)
	 */
	public Tile tile(int x, int y) {
		if(!inBounds(x,y))
			return Tile.BOUNDS;
		return tiles[x][y];
	}
	
	/**
	 * Sets the tile at the given coordinates with the given tile.
	 * Will not change tile if given coordinates are not in bounds.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param newTile replacement tile.
	 */
	public void setTile(int x, int y, Tile newTile) {
		if(inBounds(x,y))
			tiles[x][y] = newTile;
	}
	
	/**
	 * (To Be Finished)
	 * 
	 * @param filename file name that includes path to to be loaded map file (should be a .txt file)
	 * @return map at location.
	 */
	protected static DMap loadFromFile(String filename) {
		// TODO: Loads from map file in map path 
		// Each tile in file is first letter of tile name
		DMap out = new DMap(10, 10);
		
		return out;
	}
}
