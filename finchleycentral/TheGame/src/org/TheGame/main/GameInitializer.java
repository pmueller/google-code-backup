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

package org.TheGame.main;

import java.awt.Dimension;
import java.awt.DisplayMode;

import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.exceptions.PositionException;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.FloorCellFactory;
import org.TheGame.main.resourcemanagement.MapManager;
import org.TheGame.main.resourcemanagement.StaticElementFactory;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.entities.FloorCell;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.screen.FullScreenManager;
import org.TheGame.visualization.screen.ScreenManager;
import org.TheGame.visualization.screen.WindowedScreenManager;

public class GameInitializer {

	/*
	 * private static final DisplayMode POSSIBLE_MODES[] = { new
	 * DisplayMode(800, 600, 16, 0), new DisplayMode(800, 600, 32, 0), new
	 * DisplayMode(800, 600, 24, 0), new DisplayMode(640, 480, 16, 0), new
	 * DisplayMode(640, 480, 32, 0), new DisplayMode(640, 480, 24, 0), new
	 * DisplayMode(1024, 768, 16, 0), new DisplayMode(1024, 768, 32, 0), new
	 * DisplayMode(1024, 768, 24, 0), new DisplayMode(1280, 1024, 16, 0), new
	 * DisplayMode(1280, 1024, 24, 0), new DisplayMode(1280, 1024, 32, 0) };
	 */

	public ScreenManager initializeFullScreen() {

		FullScreenManager screen = FullScreenManager.newInstance();
		// DisplayMode displayMode =
		// screen.findFirstCompatibleMode(POSSIBLE_MODES);
		// DisplayMode displayMode = POSSIBLE_MODES[6];
		// screen.setFullScreen(displayMode);
		screen.setFullScreen(new DisplayMode(1024, 768, 32, 0));
		return screen;
	}

	public ScreenManager initializeWindowedScreen() {

		WindowedScreenManager screen = WindowedScreenManager.newInstance();
		//screen.setDimension(new Dimension(1024, 768));
		// screen.setDimension(new Dimension(1280, 1024));
		return screen;
	}

	public TileMap createExampleMap() {
		TileMap tileMap = MapManager.getInstance().getValue("Map1");
		
		/*
		int size = Configuration.getInstance().getValueAsInt("mapSize");

		TileMap tileMap = new TileMap(size, size);
		FloorCellFactory floorCellFactory = GameFactories.getInstance()
				.getFloorCellFactory();
		FloorCell earth = floorCellFactory.getFloorCell("earth");
		FloorCell grass = floorCellFactory.getFloorCell("grass");
		FloorCell water = floorCellFactory.getFloorCell("water");

		// add floor
		for (Position position : tileMap.area()) {
			tileMap.setFloor(position, earth);
		}

		Area area = new Area(Position.newPosition(12, 17), Position
				.newPosition(27, 24));

		for (Position position : area) {
			tileMap.setFloor(position, water);
		}

		area = new Area(Position.newPosition(40, 35), Position.newPosition(44,
				39));
		for (Position position : area) {
			tileMap.setFloor(position, water);
		}

		area = new Area(Position.newPosition(10, 11), Position.newPosition(19,
				37));
		for (Position position : area) {
			tileMap.setFloor(position, grass);
		}

		StaticElementFactory staticElementFactory = GameFactories.getInstance()
				.getStaticElementFactory();
		// add group of rocks
		area = new Area(Position.newPosition(43, 45), Position.newPosition(48,
				46));
		for (Position position : area) {
			try {
				staticElementFactory
						.newStaticElement("rock", tileMap, position);
			} catch (PositionException e) {
				throw new GameError(
						"Positions should be both occupable and valid");
			}
		}

		// add little forest!
		area = new Area(Position.newPosition(33, 43), Position.newPosition(41,
				45));
		for (Position position : area) {
			try {
				staticElementFactory
						.newStaticElement("tree", tileMap, position);
			} catch (PositionException e) {
				throw new GameError(
						"Positions should be both occupable and valid");
			}
		}

		// add that single rock
		try {
			Position position = Position.newPosition(1, 2);
			staticElementFactory.newStaticElement("rock", tileMap, position);
		} catch (PositionException e) {
			throw new GameError("Positions should be both occupable and valid");
		}
		*/
		return tileMap;
	}

	public Player createPlayer(TileMap mainMap, Position position)
			throws InvalidPositionException, OccupiedPositionException {
		return GameFactories.getInstance().getPlayerFactory().createPlayer(
				mainMap, position);
	}

	public Creep createFollowingCreep(TileMap mainMap, Position position)
			throws InvalidPositionException, OccupiedPositionException {
		return GameFactories.getInstance().getCreepFactory().createCreep(
				"monster.red", mainMap, position);
	}

	public Creep createEscapingCreep(TileMap mainMap, Position position)
			throws InvalidPositionException, OccupiedPositionException {
		return GameFactories.getInstance().getCreepFactory().createCreep(
				"monster.green", mainMap, position);
	}

	public Creep createPassiveFollowingCreep(TileMap mainMap, Position position)
			throws InvalidPositionException, OccupiedPositionException {
		return GameFactories.getInstance().getCreepFactory().createCreep(
				"monster.purple", mainMap, position);
	}
	
	public Creep createGuardingCreep(TileMap mainMap, Position position)
			throws InvalidPositionException, OccupiedPositionException{
		return GameFactories.getInstance().getCreepFactory().createCreep("monster.purple", mainMap, position);
	}
}
