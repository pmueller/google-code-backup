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

package org.TheGame.model.elements.movableElements.player;

import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.elements.staticElements.StaticElementType;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.map.AbstractTileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.graphics.element.movable.MovableElementGraphic;

public class Player extends MovableElement {

	protected PlayerElementType elementType;
	protected PlayerObservations playerObservations;
	protected Inventory normalInventory;
	protected Inventory questInventory;
	protected Inventory equippedInventory;
	protected long money;

	public Player(PlayerElementType elementType, MovableElementGraphic graphic,
			HealthState healthState) {
		super(graphic);
		this.setElementType(elementType);
		this.setHealthState(healthState);
		this.normalInventory = new Inventory();
		this.questInventory = new Inventory();
		this.equippedInventory = new Inventory();
		this.setPlayerObservations(new PlayerObservations(this));
	}
	
	public Inventory getNormalInventory() {
		return normalInventory;
	}
	
	public Inventory getQuestInventory() {
		return questInventory;
	}

	public PlayerElementType getElementType() {
		return this.elementType;
	}

	protected void setElementType(PlayerElementType elementType) {
		this.elementType = elementType;
	}

	/**
	 * @param position
	 */
	public void lookAt(Position position) {
		this.getPlayerObservations().lookAt(position);
	}

	public long getMoney()
	{
		return this.money;
	}
	
	public void setMoney(long newMoney)
	{
		this.money = newMoney;
	}
	
	public void addMoney(long add)
	{
		this.money += add;
	}
	
	public void clearMoney()
	{
		this.money = 0L;
	}
	
	protected PlayerObservations getPlayerObservations() {
		return this.playerObservations;
	}

	protected void setPlayerObservations(PlayerObservations playerObservations) {
		this.playerObservations = playerObservations;
	}
	
	public ActionResultPickUp pickUp(Direction direction) throws InvalidPositionException, OccupiedPositionException {
		AbstractTileMap currentMap = this.getCurrentMap();
		Position targetPosition = this.getPosition().positionAfterMovingIn(
				direction);
		if (currentMap.validPosition(targetPosition)) {
			if (currentMap.existsElementAt(targetPosition)) {
				PlayerElementType elementType = this.getElementType();
				Element element = currentMap.elementAt(targetPosition);
				return element.defendFromPickUpBy(this);
			}
			else {
				return ActionResultPickUp.invalidTarget;
			}
		} 
		else {
			return ActionResultPickUp.invalidTarget;
		}
	}

	public ActionResultDelete delete(Direction direction) throws InvalidPositionException, OccupiedPositionException {
		AbstractTileMap currentMap = this.getCurrentMap();
		Position targetPosition = this.getPosition().positionAfterMovingIn(
				direction);
		if (currentMap.validPosition(targetPosition)) {
			if (currentMap.existsElementAt(targetPosition)) {
				PlayerElementType elementType = this.getElementType();
				Element element = currentMap.elementAt(targetPosition);
				return element.defendFromDeleteBy(this);
			}
			else {
				return ActionResultDelete.invalidTarget;
			}
		} 
		else {
			return ActionResultDelete.invalidTarget;
		}
	}

	public Inventory getEquippedInventory() {
		return equippedInventory;
	}

}
