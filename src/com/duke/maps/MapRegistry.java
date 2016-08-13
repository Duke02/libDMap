package com.duke.maps;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to store maps.
 * 
 * 
 * @author Duke
 *
 */
public class MapRegistry {

	/**
	 * Container for custom (ie from a file) maps.
	 */
	protected Map<String, DMap> customMaps;
	/**
	 * Randomly generated maps.
	 */
	protected List<DMap> randomMaps;
	/**
	 * Use custom maps when randomly selecting maps?
	 */
	protected boolean useCustomMaps;
	/**
	 * File path to custom map file locations. Used when loading maps from file.
	 */
	protected String mapsFilePath;
	
	/**
	 * Constructor for Registry. 
	 * <p>
	 * Generates random maps when randMaps is greater than 0. Sets randomMaps size to the greater of randMaps or 10.
	 * @param randMaps number of random maps to generate at init.
	 * @param gen Generator to use when Generating maps.
	 * @param useCustom Sets default value to use custom maps when randomly selecting from map storages.
	 * @param mapsFilePath File path that is used when loading maps.
	 */
	public MapRegistry(int randMaps, MapGenerator gen, boolean useCustom, String mapsFilePath) {
		this.useCustomMaps = useCustom;
		this.mapsFilePath = mapsFilePath;
		if(!this.mapsFilePath.endsWith(FileSystems.getDefault().getSeparator())) {
			this.mapsFilePath += FileSystems.getDefault().getSeparator();
		}
		this.customMaps = new HashMap<String, DMap>();
		this.randomMaps = new ArrayList<DMap>(Math.max(10, randMaps));
		
		for(int r = 0; r < randMaps; r++) {
			this.generateRandomMap(gen);
		}
		
	}
	
	/**
	 * Constructor that does not automatically generate maps.
	 * 
	 * <p>
	 * Uses {@link #MapRegistry(int, MapGenerator, boolean, String)} but sets randMaps to 0 
	 * and the generator to null.	 
	 *  
	 * @param useCustom Sets default value to use custom maps when randomly selecting from map storages.
	 * @param mapsFilePath File path that is used when loading maps.
	 * @see #MapRegistry(int, MapGenerator, boolean, String)
	 */
	public MapRegistry(boolean useCustom, String mapsFilePath) {
		this(0, null, useCustom, mapsFilePath);
	}
	
	/**
	 * Generates random map using given generator.
	 * <p>
	 * Adds built map to {@link #randomMaps}.
	 * @param gen generator to be used to make maps.
	 * @return built map.
	 */
	public DMap generateRandomMap(MapGenerator gen) {
		DMap map = new DMap(gen);
		randomMaps.add(map);
		return map;
	}
	
	/**
	 * Returns map from storages.
	 * @param useCustom Bypasses default value when selecting maps. Will use custom maps when selecting if  true.
	 * @return randomly selected map from random maps and, if applicable, custom maps.
	 */
	public DMap getMap(boolean useCustom) {
		List<DMap> maps = new ArrayList<DMap>(this.randomMaps);
		
		if(useCustom) {
			maps.addAll(this.customMaps.values());
		}
		
		return maps.get((int)(Math.random()*maps.size()));
	}
	
	/**
	 * Uses default value to select map.
	 * @return Map from storages of random and, if {@link #useCustomMaps} is True, custom maps.
	 */
	public DMap getMap() {
		return this.getMap(this.useCustomMaps);
	}
	
	/**
	 * Gets custom map from {@link #customMaps}.
	 * <p>
	 * @param name name of file of map's origin
	 * @return custom map if it is found.
	 * @throws Exception if map is not found. Provides name of map and gives suggestion.
	 */
	public DMap getCustomMap(String name) throws Exception {
		DMap out = customMaps.get(name);
		
		if(out == null) {
			throw new Exception("Can't find map with name " + name + ". Did you add it to the registry first?");
		}
		
		return out;
	}
	
	/**
	 * Method not finished until {@link DMap#loadFromFile(String)} is finished.
	 * <p>
	 * Loads a map from the file system and includes it into {@link #customMaps} if found.
	 * @param fileName
	 * @return loaded map
	 * @see DMap#loadFromFile(String)
	 */
	public DMap loadFromFile(String fileName) {
		// Do not include path!!!
		DMap map = new DMap(this.mapsFilePath + fileName);
		customMaps.put(fileName, map);
		return map;
	}
}
