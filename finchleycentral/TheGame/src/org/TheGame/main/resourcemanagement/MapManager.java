package org.TheGame.main.resourcemanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import org.TheGame.events.eventgeneration.CreepSpawner;
import org.TheGame.events.eventgeneration.ProjectileCreepSpawner;
import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.PositionException;
import org.TheGame.main.GameEntities;
import org.TheGame.main.GameFactories;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.entities.FloorCell;
import org.TheGame.model.items.Item;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class MapManager {
	private static MapManager mapManager;
	private Hashtable<String, TileMap> mapDictionary;
	private Hashtable<String, String> stringDictionary;
	private Hashtable<TileMap, Collection<CreepSpawner>> spawnerDictionary;
	
	private MapManager() {
		mapDictionary = new Hashtable<String, TileMap>();
		stringDictionary = new Hashtable<String, String>();
		spawnerDictionary = new Hashtable<TileMap, Collection<CreepSpawner>>();
		Configuration configuration = Configuration.getInstance();
		String mapDirectoryPath = configuration.getValue("applicationPath")
				+ configuration.getValue("mapDirectory");
		File mapDirectory = new File(mapDirectoryPath);
		File[] files = mapDirectory.listFiles();
		for (int i = 0; i < files.length; i++) {
			// TODO damn svn puts files in all dirs =/
			if (!files[i].isDirectory()) {
				if (!files[i].getName().equals(".svn")) {
					//TileMap map = loadMap(files[i].getAbsolutePath());
					String mapName = files[i].getName();
					int pointPosition = mapName.lastIndexOf(".");
					mapName = mapName.substring(0, pointPosition);
					//System.out.println(mapName + "::" + files[i].getAbsolutePath());
					//dictionary.put(mapName, map);
					// System.out.println(imageName);
					stringDictionary.put(mapName, files[i].getAbsolutePath());
				}
			}
		}
	}
	public TileMap loadMap(String fName){
		String[] data = fName.split("[\\\\/]");
		String mapFileName = data[data.length - 1];
		String mapName = mapFileName.substring(0, mapFileName.length() - 4);
		String border = "";
		//if(dictionary.get(mapName) !=null) return dictionary.get(mapName);
		
		char[][] map = null;
		String musicName = "";
		ArrayList<String> maps = new ArrayList<String>();
		ArrayList<Position> positions = new ArrayList<Position>();
		ArrayList<Character> teleportTiles = new ArrayList<Character>();
		ArrayList<String> keyStrings = new ArrayList<String>();
		ArrayList<List<String>> creepListList = new ArrayList<List<String>>();
		ArrayList<List<String>> projectileListList = new ArrayList<List<String>>();
		ArrayList<Character> creepSpawnerTiles = new ArrayList<Character>();
		ArrayList<Character> projectileSpawnerTiles = new ArrayList<Character>();
		ArrayList<Direction> directions = new ArrayList<Direction>();
		ArrayList<Integer> creepLimits = new ArrayList<Integer>();
		ArrayList<Long> creepUpdateTime = new ArrayList<Long>();
		ArrayList<Long> projectileUpdateTime = new ArrayList<Long>();
		Collection<CreepSpawner> creepSpawners = new ArrayList<CreepSpawner>();
		
		try{
			Scanner in = new Scanner(new File(fName));
    	    musicName = in.nextLine().split("=")[1];
			in.useDelimiter(",");

			int width = Integer.parseInt(in.next());
			//System.out.println(width);
			int height = Integer.parseInt(in.next());
			int numTeleporters = Integer.parseInt(in.next());
			int numCreepSpawners = Integer.parseInt(in.next());
			int numProjectileSpawners = Integer.parseInt(in.next());
			
			//read in teleporters
			for(int a = 0; a < numTeleporters; ++a) {
				in.nextLine();
				teleportTiles.add(in.next().charAt(0));
				maps.add(in.next());
				positions.add(Position.newPosition(in.nextInt(), in.nextInt()));
				keyStrings.add(in.next());
				//tiles.add(in.next().charAt(0));
			}
			
			//read in the creepSpawners
			in.nextLine();
			for(int a = 0; a < numCreepSpawners; ++a){
				String[] line = in.nextLine().split(";");
				String[] info = line[0].split(",");
				creepSpawnerTiles.add(info[0].charAt(0));
				creepLimits.add(new Integer(info[1]));
				creepUpdateTime.add(new Long(info[2]));
				String[] creepNameArray = line[1].split(",");
				creepListList.add(Arrays.asList(creepNameArray));
			}
			
			//read in the projectileSpawners
			for(int a = 0; a < numProjectileSpawners; ++a){
				String[] line = in.nextLine().split(";");
				String[] projectileInfo = line[0].split(",");
				projectileSpawnerTiles.add(projectileInfo[0].charAt(0));				
				int horizontalDirection = Integer.parseInt(projectileInfo[1]);
				int verticalDirection = Integer.parseInt(projectileInfo[2]);
				projectileUpdateTime.add(new Long(projectileInfo[3]));
				directions.add(Direction.newDirection(horizontalDirection, verticalDirection));
				
				String[] projectileNameArray = line[1].split(",");
				projectileListList.add(Arrays.asList(projectileNameArray));
			}
			
			border = in.next();						
			map = new char[height][width];
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					
					String temp = in.next();
					if(temp.charAt(0) < 65){
						if(temp.charAt(1) < 65)
							map[j][i] = temp.charAt(2);
						else
							map[j][i] = temp.charAt(1);
					}else{
						map[j][i] = temp.charAt(0);
					}
				}
			}

    	    in.close();
		}catch (Exception e){//Catch exception if any
    	      System.err.println("Error in reading file " + fName + " error: "+e.getMessage());
    	      e.printStackTrace();
		}
		
		TileMap tileMap = new TileMap(map.length, map[1].length);
		tileMap.setMusic(musicName);
		tileMap.setBorder(border);
		FloorCellFactory floorCellFactory = GameFactories.getInstance()
				.getFloorCellFactory();
		FloorCell earth = floorCellFactory.getFloorCell("earth");
		FloorCell grass = floorCellFactory.getFloorCell("grass");
		FloorCell water = floorCellFactory.getFloorCell("water");
		FloorCell black = floorCellFactory.getFloorCell("black");
		FloorCell earth2 = floorCellFactory.getFloorCell("earth2");
		FloorCell castleFloor = floorCellFactory.getFloorCell("castleFloor");
		StaticElementFactory staticElementFactory = GameFactories.getInstance()
		.getStaticElementFactory();
		
		// add floor
		for (Position position : tileMap.area()) {
			tileMap.setFloor(position, earth);
		}
		int currentTeleport = 0;
		int currentSpawner = 0;
		int currentProjectileSpawner = 0;
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j <map[i].length; j++){
				Position position = Position.newPosition(i, j);
				if(map[i][j] == 'r'){
					try {
						staticElementFactory.newStaticElement("rock", tileMap, position);
						char temp = 'e'; //ground tile copies that of tile to the left or right
						if(i > 0 && map[i-1][j]!='w'){ //makes sure it isn't water
							temp = map[i-1][j];
						}
						else{
							if(i+1 < map.length)
								temp = map[i+1][j];
						}
						if(temp == 'g') tileMap.setFloor(position, grass);
						
					} catch (PositionException e) {
					throw new GameError("Positions should be both occupable and valid");
					}
				}else if(map[i][j] == 'T'){
					try {
						staticElementFactory.newStaticElement("tree", tileMap, position);
						char temp = 'e';
						if(i > 0 && map[i-1][j]!='w'){
							temp = map[i-1][j];
						}
						else{
							if(i+1 < map.length)
								temp = map[i+1][j];
						}
						if(temp == 'g') tileMap.setFloor(position, grass);
					} catch (PositionException e) {
						throw new GameError("Positions should be both occupable and valid");
					}
				} else if(map[i][j] == 'p'){
					try {
						staticElementFactory.newStaticElement("pillar", tileMap, position);
						tileMap.setFloor(position, castleFloor);
					} catch (PositionException e) {
						throw new GameError("Positions should be both occupable and valid");
					}
				}else if(map[i][j] == 't'){
						try {
							staticElementFactory.newStaticElement("smallTree", tileMap, position);
							char temp = 'e';
							if(i > 0 && map[i-1][j]!='w'){
								temp = map[i-1][j];
							}
							else{
								if(i+1 < map.length)
									temp = map[i+1][j];
							}
							if(temp == 'g') tileMap.setFloor(position, grass);
						} catch (PositionException e) {
							throw new GameError("Positions should be both occupable and valid");
						}
				} else if(map[i][j] == 'C'){
					try {
						//staticElementFactory.newStaticElement("castle", tileMap, position);
						String s = keyStrings.get(currentTeleport);
						if(s.equals("none")) {
							staticElementFactory.newTeleportStaticElement("castle", tileMap, position, 
								maps.get(currentTeleport), positions.get(currentTeleport));
						}
						else {
							staticElementFactory.newTeleportStaticElement("Castle", tileMap, position, 
									maps.get(currentTeleport), positions.get(currentTeleport), 
									keyStrings.get(currentTeleport));
						}
						map[i][j] = teleportTiles.get(currentTeleport);
						++currentTeleport;
					} catch (PositionException e) {
						throw new GameError("Positions should be both occupable and valid");
					}
				} else if(map[i][j] == 'm'){
					try {
						String s = keyStrings.get(currentTeleport);
						if(s.equals("none")) {
							staticElementFactory.newTeleportStaticElement("teleport", tileMap, position, 
								maps.get(currentTeleport), positions.get(currentTeleport));
						}
						else {
							staticElementFactory.newTeleportStaticElement("silverTeleport", tileMap, position, 
									maps.get(currentTeleport), positions.get(currentTeleport), 
									keyStrings.get(currentTeleport));
						}
						map[i][j] = teleportTiles.get(currentTeleport);
						++currentTeleport;
					} catch (PositionException e) {
						throw new GameError("Positions should be both occupable and valid");
					}
					
					//Creep Spawner
				} else if(map[i][j] == 'c') {
					CreepSpawner cs = new CreepSpawner(mapName, creepListList.get(currentSpawner), 
							Position.newPosition(i, j), creepLimits.get(currentSpawner).intValue(), 
							creepUpdateTime.get(currentSpawner));
					creepSpawners.add(cs);
					GameEntities.getInstance().addCreepSpawner(cs);
					map[i][j] = creepSpawnerTiles.get(currentSpawner);
					++currentSpawner;
					
					//Projectile Spawner
				} else if(map[i][j] == 'P') {
					CreepSpawner cs = new ProjectileCreepSpawner(mapName, projectileListList.get(currentProjectileSpawner), 
							Position.newPosition(i, j),directions.get(currentProjectileSpawner), projectileUpdateTime.get(currentProjectileSpawner));
					creepSpawners.add(cs);
					GameEntities.getInstance().addCreepSpawner(cs);
					map[i][j] = projectileSpawnerTiles.get(currentProjectileSpawner);
					++currentProjectileSpawner;
					
				}if(map[i][j] == 'g'){
					tileMap.setFloor(position, grass);
				}if(map[i][j] == 'w'){
					tileMap.setFloor(position, water);
				}if(map[i][j] == 'b'){
					tileMap.setFloor(position, black);
				}if(map[i][j] == 'E'){
					tileMap.setFloor(position, earth2);
				}
				if(map[i][j] == 'f'){
					tileMap.setFloor(position, castleFloor);
				}
				
			}
		}
		this.spawnerDictionary.put(tileMap, creepSpawners);
		return tileMap;
	}

	public void setTile(char c, Position position) {
		
	}

	public TileMap getValue(String key) {
		if(mapDictionary.get(key) == null) {
			TileMap tileMap = this.loadMap(stringDictionary.get(key));
			mapDictionary.put(key, tileMap);
		}
		return (mapDictionary.get(key));
	}
	
	public Collection<CreepSpawner> getCreepSpawners(TileMap tileMap) {
		return spawnerDictionary.get(tileMap);
	}

	public void setValue(String key, TileMap value) {
		mapDictionary.put(key, value);
	}

	public Hashtable<String, TileMap> getAll() {
		return this.mapDictionary;
	}

	public static MapManager getInstance() {
		if (mapManager == null) {
			mapManager = new MapManager();
		}
		return mapManager;
	}
}
