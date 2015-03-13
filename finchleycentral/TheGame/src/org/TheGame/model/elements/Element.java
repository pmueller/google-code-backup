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

package org.TheGame.model.elements;

import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.GameException;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameObjects;
import org.TheGame.model.entities.GameEntity;
import org.TheGame.model.map.AbstractTileMap;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.graphics.element.ElementGraphic;

/**
 * @author Facundo Manuel Quiroga
 */
public abstract class Element extends GameEntity {
	// all elements are unwalkable
	protected Position position;
	protected AbstractTileMap currentMap;
	protected boolean attacked;
	protected Element attacker;
	
	public Element() {
		super();
		AbstractTileMap map = GameObjects.getInstance().getLimboTileMap();
		this.setPosition(null);
		this.setCurrentMap(map);
		try {
			map.addElementAt(this, null);
		} catch (GameException e) {
			throw new GameError(e.getMessage());
		}

	}

	public abstract void update(long timeElapsed);

	public AbstractTileMap getCurrentMap() {
		return currentMap;
	}

	public TileMap getCurrentTileMap() {
		TileMap tileMap;
		try {
			tileMap = (TileMap) this.getCurrentMap();
		} catch (ClassCastException e) {
			throw new GameError("Element is not in a TileMap!");
		}
		return tileMap;
	}

	public void setCurrentMap(AbstractTileMap currentMap) {
		this.currentMap = currentMap;
	}

	public abstract ElementGraphic getGraphic();

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String toString() {
		return this.getClass().getName() + ", type: "
				+ this.getElementType().getName() + " at " + this.getPosition()
				+ " id " + this.getId();
	}

	public abstract ActionResultAttack attack(Direction direction) throws InvalidPositionException, OccupiedPositionException;
	
	public abstract ActionResultAttack attackAtRange(Element target) throws InvalidPositionException, OccupiedPositionException;

	public abstract ActionResultPickUp pickUp(Direction direction) throws InvalidPositionException, OccupiedPositionException;
	
	public abstract ActionResultDelete delete(Direction direction) throws InvalidPositionException, OccupiedPositionException;
	
	public abstract ActionResultAttack defendFromAttackBy(Element attacker,
			int attackStrength) throws InvalidPositionException, OccupiedPositionException;
	
	public abstract ActionResultPickUp defendFromPickUpBy(Element attacker) throws InvalidPositionException, OccupiedPositionException;
	
	public abstract ActionResultDelete defendFromDeleteBy(Element attacker) throws InvalidPositionException, OccupiedPositionException;
	
	
	public void die() throws InvalidPositionException, OccupiedPositionException {
		this.getCurrentMap().removeElement(this);
	}

	public abstract ElementType getElementType();

	public boolean isAttacked(){
		return this.attacked;
	}
	
	public void resetAttacked() {
		this.attacked = false;
	}
	
	public void setAttacked() {
		this.attacked = true;
	}
	
	public void setAttacker(Element attacker) {
		this.attacker = attacker;
	}
	
	public Element getAttacker() {
		return this.attacker;
	}
	
}
