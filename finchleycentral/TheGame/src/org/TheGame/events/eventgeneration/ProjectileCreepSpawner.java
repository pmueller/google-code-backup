package org.TheGame.events.eventgeneration;

import java.util.List;

import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameEntities;
import org.TheGame.main.GameFactories;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class ProjectileCreepSpawner extends CreepSpawner {
	protected Direction spawningDirection;
	
	public ProjectileCreepSpawner(String tileMapString, List<String> creepNameList, Position spawningPosition, Direction spawningDirection) {
		super(tileMapString, creepNameList, spawningPosition, -1);
		this.spawningDirection = spawningDirection;
	}
	
	public ProjectileCreepSpawner(String tileMapString, List<String> creepNameList, Position spawningPosition, Direction spawningDirection, long timeBetweenUpdates) {
		super(tileMapString, creepNameList, spawningPosition, -1, timeBetweenUpdates);
		this.spawningDirection = spawningDirection;
	}
	
	@Override
	protected void doSpawnCreep(TileMap map, Position spawningPosition) {

		try {
			int i = (int)(Math.random() * creepNameList.size());
			String randomCreepName = creepNameList.get(i);
			Creep creep = GameFactories.getInstance().getCreepFactory()
					.createCreep(randomCreepName);
			
			creep.setDirection(spawningDirection);
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
			MapTeleporter.getInstance().teleportTo(creep, this.getTileMap(), this.spawningPosition);
		} catch (InvalidPositionException e) {
			throw new GameError("Position was both valid and occupable");
		} catch (OccupiedPositionException e) {
			throw new GameError("Position was both valid and occupable");
		}
	}
}
