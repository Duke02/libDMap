package com.duke.maps;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRegistry {

	protected Map<String, DMap> customMaps;
	protected List<DMap> randomMaps;
	protected boolean useCustomMaps;
	protected String mapsFilePath;
	
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
	
	public MapRegistry(boolean useCustom, String mapsFilePath) {
		this(0, null, useCustom, mapsFilePath);
	}
	
	public DMap generateRandomMap(MapGenerator gen) {
		DMap map = new DMap(gen);
		randomMaps.add(map);
		return map;
	}
	
	public DMap getMap(boolean useCustom) {
		List<DMap> maps = new ArrayList<DMap>(this.randomMaps);
		
		if(useCustom) {
			maps.addAll(this.customMaps.values());
		}
		
		return maps.get((int)(Math.random()*maps.size()));
	}
	
	public DMap getMap() {
		return this.getMap(this.useCustomMaps);
	}
	
	public DMap getCustomMap(String name) throws Exception {
		DMap out = customMaps.get(name);
		
		if(out == null) {
			throw new Exception("Can't find map with name " + name + ". Did you add it to the registry first?");
		}
		
		return out;
	}
	
	public DMap loadFromFile(String fileName) {
		// Do not include path!!!
		DMap map = new DMap(this.mapsFilePath + fileName);
		customMaps.put(fileName, map);
		return map;
	}
}
