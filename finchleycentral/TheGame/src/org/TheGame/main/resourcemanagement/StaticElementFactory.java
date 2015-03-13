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

package org.TheGame.main.resourcemanagement;

import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameFactories;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.staticElements.InventoryBagStaticElement;
import org.TheGame.model.elements.staticElements.StaticElement;
import org.TheGame.model.elements.staticElements.StaticElementType;
import org.TheGame.model.elements.staticElements.TeleportStaticElement;
import org.TheGame.model.items.Item;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.graphics.animations.Animation;
import org.TheGame.visualization.graphics.element.StaticElementGraphic;

/**
 * @author Facundo Manuel Quiroga Feb 10, 2009
 */
public class StaticElementFactory {

	public StaticElementFactory() {

	}

	public StaticElement newStaticElement(String name) {
		Animation animation = GameFactories.getInstance().getAnimationFactory()
				.getAnimation(name);
		StaticElementGraphic graphic = new StaticElementGraphic(animation);
		return new StaticElement(new StaticElementType(name), graphic);
	}

	public StaticElement newStaticElement(String name, TileMap tileMap,
			Position position) throws InvalidPositionException,
			OccupiedPositionException {
		StaticElement staticElement = this.newStaticElement(name);
		MapTeleporter.getInstance()
				.teleportTo(staticElement, tileMap, position);
		return staticElement;
	}

	public StaticElement newTeleportStaticElement(String name, TileMap tileMap, Position position,
			String teleportToMap, Position teleportToPosition)
			throws InvalidPositionException, OccupiedPositionException {
		Animation animation = GameFactories.getInstance().getAnimationFactory()
		.getAnimation(name);
		StaticElementGraphic graphic = new StaticElementGraphic(animation);
		StaticElement staticElement = new TeleportStaticElement(new StaticElementType(name), 
				graphic, teleportToMap, teleportToPosition); 
		MapTeleporter.getInstance().teleportTo(staticElement, tileMap, position);
		return staticElement;
	}

	public StaticElement newTeleportStaticElement(String name, TileMap tileMap, Position position,
			String teleportToMap, Position teleportToPosition, String keyString) 
			throws InvalidPositionException, OccupiedPositionException {
		//Animation animation = GameFactories.getInstance().getAnimationFactory()
		//.getAnimation(name);
		//StaticElementGraphic graphic = new StaticElementGraphic(animation);
		StaticElementGraphic graphic = new StaticElementGraphic(Animation.newAnimation(ImageManager.getInstance().getValue(name), 100));
		StaticElement staticElement = new TeleportStaticElement(new StaticElementType(name), 
				graphic, teleportToMap, teleportToPosition, keyString); 
		MapTeleporter.getInstance().teleportTo(staticElement, tileMap, position);
		return staticElement;
	}
	
	public StaticElement newInventoryBagElement(Item item, TileMap tileMap, Position position, Element e)
			throws InvalidPositionException, OccupiedPositionException {
		//Animation animation = GameFactories.getInstance().getAnimationFactory()
		//.getAnimation(name);
		//System.out.println("Then an image for "+name+" is created with properties:"+ImageManager.getInstance().getValue(name));
		//System.out.println("Which is then conveted to an animation:"+Animation.newAnimation(ImageManager.getInstance().getValue(name), 100));
		StaticElementGraphic graphic = new StaticElementGraphic(Animation.newAnimation(ImageManager.getInstance().getValue(item.getGraphic()), 100));
		//System.out.println("And finally a graphic:"+graphic);
		StaticElement staticElement = new InventoryBagStaticElement(new StaticElementType(item.getGraphic()), 
				graphic, tileMap, position, e, item); 
		MapTeleporter.getInstance().teleportTo(staticElement, tileMap, position);
		return staticElement;
	}
}
