package com.duke.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that is responsible for randomly generating maps.
 * <p>
 * All methods in this are extendable, but if aren't overriden will use default methods from here.
 * <p>
 * If you want to make multiple maps from one instance of MapGenerator, clear the map ({@link DMap#clear()})
 * <p>
 * Provides basic (and probably not very efficient) smoothing and randomization tools.
 * 
 * @author Duke
 *
 */
public class MapGenerator {
	/**
	 * Map that is being worked on.
	 */
	public DMap map;
	
	/**
	 * Constructs map that is to be worked on using the given size parameters.
	 * @param width Width of map
	 * @param height Height of map
	 * @see DMap#DMap(int, int)
	 */
	public MapGenerator(int width, int height) {
		this.map = new DMap(width, height);
	}
	
	/**
	 * 
	 * Builds map using randomness.
	 * <p>
	 * Default operations are randomizing tiles within map, then smoothing it out.
	 * 
	 * @return finished map
	 */
	public DMap build() {
		List<Tile> tiles = new ArrayList<Tile>(Arrays.asList(Tile.values()));
		tiles.remove(Tile.BOUNDS);
		randomizeTiles(tiles);
		smooth(6);
		return this.map;
	}
	
	/**
	 * Basic smoothing algorithm.
	 * <p>
	 * Goes through all of the tiles in {@link #map}, counts most occurring tile in a square radius of 1, 
	 * then sets center tile to most common tile.
	 * @param times
	 */
	protected void smooth(int times) {
		Map<Tile, Integer> index;
		for(int time = 0; time < times; time++) {
			
			for(int x = 0; x < map.width; x++) {
				for(int y = 0; y < map.height; y++) {
					
					index = new HashMap<Tile, Integer>();
					for(Tile t : Tile.values())
						index.put(t, new Integer(0));
					index.remove(Tile.BOUNDS);
					
					for(int rx = -1; rx <= 1; rx++) {
						for(int ry = -1; ry <= 1; ry++) {
							
							if(!map.inBounds(x + rx, y + ry)) 
								continue;
							
							int old = index.get(map.tile(x + rx, y + ry)).intValue();
							index.replace(map.tile(rx + x, ry + y), Integer.sum(old, 1));
							
						}
					}
					
					Tile maxTile = map.tile(x, y);
					for(Tile t : index.keySet()) {
						if(index.get(t).compareTo(index.get(maxTile)) > 0) 
							maxTile = t;
					}
					map.setTile(x, y, maxTile);
					
				}
			}
			
		}
	}
	
	/**
	 * Randomizes tiles based on given tile {@link java.util.List List}.
	 * <p>
	 * Each tile has an equal weight.
	 * @param tiles tiles that are to be used
	 */
	protected void randomizeTiles(List<Tile> tiles) {
		for(int x = 0; x < map.width; x++) {
			for(int y = 0; y < map.height; y++) {
				map.setTile(x, y, tiles.get((int) (Math.random()*tiles.size())));
			}
		}
	}
	
	/**
	 * Weighted randomization.
	 * <p>
	 * Uses a weighted random number generator to pick tile at current (x,y) coordinates.
	 * <p>
	 * For unweighted randomize, use {@link #randomizeTiles(List)}.
	 * @param tiles weighted tile list.
	 * 
	 */
	protected void randomizeTiles(Map<Tile, Float> tiles) {
		int maxRoll;
		int roll;
		
		for(int x = 0; x < map.width; x++) {
			for(int y = 0; y < map.height; y++) {
				
				maxRoll = 0;
				for(Tile t : tiles.keySet())
					maxRoll += tiles.get(t);
				
				roll = (int)(Math.random()*maxRoll);
				for(Tile t : tiles.keySet()) {
					if(roll < tiles.get(t))
						map.setTile(x, y, t);
					roll -= tiles.get(t);
				}
				map.setTile(x, y, (Tile)(tiles.keySet()
						.toArray()[(int)(Math.random() * tiles.keySet().size())]
						));
				
			}
		}
	}
}
