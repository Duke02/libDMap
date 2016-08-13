package com.duke.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGenerator {
	DMap map;
	
	public MapGenerator(int width, int height) {
		this.map = new DMap(width, height);
	}
	
	public DMap build() {
		List<Tile> tiles = new ArrayList<Tile>(Arrays.asList(Tile.values()));
		tiles.remove(Tile.BOUNDS);
		randomizeTiles(tiles);
		smooth(6);
		return this.map;
	}
	
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
	
	protected void randomizeTiles(List<Tile> tiles) {
		for(int x = 0; x < map.width; x++) {
			for(int y = 0; y < map.height; y++) {
				map.setTile(x, y, tiles.get((int) (Math.random()*tiles.size())));
			}
		}
	}
	
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
