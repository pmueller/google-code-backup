/* TiledZelda, a top-down 2d action-rpg game written in Java.
    Copyright (C) 2008  Facundo Manuel Quiroga <facundoq@gmail.com>
 	
 	This file is part of TiledZelda.

    TiledZelda is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TiledZelda is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TiledZelda.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.TheGame.events.eventgeneration;

import java.util.List;

import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameEntities;
import org.TheGame.main.GameFactories;
import org.TheGame.main.GameObjects;
import org.TheGame.main.resourcemanagement.MapManager;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

/**
 * @author Facundo Quiroga Creation date: 22/10/2008
 */
public class CreepSpawner {
	protected TileMap tileMap;
	protected long accumulatedTime;
	protected List<String> creepNameList;
	protected Position spawningPosition;
	protected String tileMapString;
	protected int spawningLimit;
	protected long timeBetweenUpdates = 2000;
	
	public CreepSpawner(String tileMapString, List<String> creepNameList, Position spawningPosition, int spawningLimit) {
		this.setTileMapString(tileMapString);
		this.setAccumulatedTime(0);
		this.creepNameList = creepNameList;
		this.spawningPosition = spawningPosition;
		this.spawningLimit = spawningLimit;
	}

	public CreepSpawner(String tileMapString, List<String> creepNameList, Position spawningPosition, int spawningLimit, long timeBetweenSpawns) {
		this(tileMapString, creepNameList, spawningPosition, spawningLimit);
		this.setTimeBetweenUpdates(timeBetweenSpawns);
	}
	
	public void update(long elapsedTime) {
		this.setAccumulatedTime(this.getAccumulatedTime() + elapsedTime);
		if (this.getAccumulatedTime() > this.defaultTimeBetweenUpdates()) {
			this.setAccumulatedTime(this.getAccumulatedTime()
					- this.defaultTimeBetweenUpdates());
			this.spawnCreep();
		}
	}

	protected void spawnCreep() {
		if(spawningLimit != 0)
		{
			TileMap map = this.getTileMap();
			
			int x = this.getSpawningPosition().getX();
			int y = this.getSpawningPosition().getY();
			//x, y in {-1, 0, 1}
			int dx = (GameObjects.getInstance().getRandomNumberGenerator()
				.randomNumber()	% 3) - 1;
			int	dy = (GameObjects.getInstance().getRandomNumberGenerator()
				.randomNumber() % 3) - 1;
			// Position spawningPosition = new Position(x,y);
			//Position spawningPosition = Position.newPosition(x, y);
			
			Position spawningPosition = Position.newPosition(x + dx, y + dy);
			if (map.validPosition(spawningPosition)) {
				if (map.occupable(spawningPosition)) {
				doSpawnCreep(map, spawningPosition);
				}
			}
			--spawningLimit;
		}
	}

	protected void doSpawnCreep(TileMap map, Position spawningPosition) {

		try {
			int i = (int)(Math.random() * creepNameList.size());
			String randomCreepName = creepNameList.get(i);
			Creep creep = GameFactories.getInstance().getCreepFactory()
					.createCreep(randomCreepName);
			/*
			 * Creep creep = GameFactories.getInstance().getCreepFactory()
					.createCreep(map, spawningPosition);
			
			 * RandomNumberGenerator randomNumberGenerator =
			 * GameObjects.getInstance().getRandomNumberGenerator(); int
			 * interval =
			 * randomNumberGenerator.getUpperRange()-randomNumberGenerator
			 * .getLowerRange(); int cutOff = (interval * 20) / 100; if
			 * (randomNumberGenerator.randomNumber() < cutOff){ creep =
			 * GameFactories
			 * .getInstance().getCreepFactory().createCreep("monster.red",map,
			 * spawningPosition); }else if (
			 * randomNumberGenerator.randomNumber() < cutOff) { creep =
			 * GameFactories
			 * .getInstance().getCreepFactory().createCreep("monster.blueGreen"
			 * ,map, spawningPosition); }else { creep =
			 * GameFactories.getInstance
			 * ().getCreepFactory().createCreep("monster.yellow",map,
			 * spawningPosition); }
			 */
			GameEntities.getInstance().addElement(creep);
			MapTeleporter.getInstance().teleportTo(creep, this.getTileMap(), spawningPosition);
		} catch (InvalidPositionException e) {
			throw new GameError("Position was both valid and occupable");
		} catch (OccupiedPositionException e) {
			throw new GameError("Position was both valid and occupable");
		}
	}

	public TileMap getTileMap() {
		if(this.tileMap == null) {
			this.setTileMap(MapManager.getInstance().getValue(this.tileMapString));
		}
		return this.tileMap;
	}

	/**
	 * @return default MS between updates
	 */
	protected long defaultTimeBetweenUpdates() {
		return this.timeBetweenUpdates;
	}

	protected void setTimeBetweenUpdates(long timeBetweenSpawns) {
		this.timeBetweenUpdates = timeBetweenSpawns;
	}
	
	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public long getAccumulatedTime() {
		return this.accumulatedTime;
	}

	public void setAccumulatedTime(long accumulatedTime) {
		this.accumulatedTime = accumulatedTime;
	}

	public List<String> getCreepNameList() {
		return this.creepNameList;
	}
	
	public void setCreepNameList(List<String> creepNameList) {
		this.creepNameList = creepNameList;
	}
	
	public Position getSpawningPosition() {
		return this.spawningPosition;
	}

	public void setSpawningPosition(Position position) {
		this.spawningPosition = position;
	}
	
	public void setTileMapString(String tileMapString) {
		this.tileMapString = tileMapString;
	}
	
}
