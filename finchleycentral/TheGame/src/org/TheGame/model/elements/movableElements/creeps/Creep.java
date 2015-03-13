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

package org.TheGame.model.elements.movableElements.creeps;

import java.util.Random;

import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.input.ai.AI;
import org.TheGame.main.GameCore;
import org.TheGame.main.GameFactories;
import org.TheGame.main.resourcemanagement.ImageManager;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.elements.movableElements.player.HealthState;
import org.TheGame.model.elements.movableElements.player.PlayerElementType;
import org.TheGame.model.elements.staticElements.InventoryBagStaticElement;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.items.Item;
import org.TheGame.model.items.ItemManager;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.visualization.graphics.element.movable.MovableElementGraphic;

public class Creep extends MovableElement {
	protected CreepElementType elementType;
	private AI ai;
	Inventory inventory;
	private static boolean crowned = false;
	private static int numKilled;

	public Creep(CreepElementType elementType, MovableElementGraphic graphic,
			AI ai, HealthState healthState) {
		super(graphic);
		this.setElementType(elementType);
		ai.setCreep(this);
		this.setAI(ai);
		this.setHealthState(healthState);
		Random rand = new Random();
		inventory = new Inventory();
		Item item;
		if(!crowned&&this.getElementType().getName().equals("monster.jasonLink")) {
			item = GameFactories.getInstance().getItemFactory().CreateItem("crown");
			crowned = true;
		}
		else {
			while(true) {
				
				item = GameFactories.getInstance().getItemFactory().createRandomItem(elementType.getLevel()).createItem();
				if(!(item.getName().toLowerCase().equals("crown of justice")) &&
				   !(item.getName().toLowerCase().equals("silver key")) &&
				   !(item.getName().toLowerCase().equals("gold key")) &&
				   !(item.getName().toLowerCase().equals("boss key")) &&
				   ItemManager.getInstance().howManyInPlay(item) < item.itemElementTypeDescription.getMaxAmount() &&
				   ImageManager.getInstance().getValue(item.getGraphic())!=null) break;
			}
		}
		//System.out.println("!"+item.getMonsterDropLevel());
		item.setMonsterDropLevel(elementType.getLevel());
		//System.out.println("!!"+item.getMonsterDropLevel());
		ItemManager.getInstance().addItemIntoPlay(item);
		inventory.AddItem(item);
	}
	
	public Creep(CreepElementType elementType, MovableElementGraphic graphic,
			AI ai, HealthState healthState, Direction creepDirection) {
		this(elementType, graphic, ai,healthState);
		this.setDirection(creepDirection);
	}

	public AI getAI() {
		return ai;
	}

	public void setAI(AI ai) {
		this.ai = ai;
	}

	public void die() {
		try {
			super.die();
		} catch(Exception e){};
		if(this.getAttacker()!=null && this.getAttacker().equals(GameCore.getInstance().getPlayer())) {
			try {
				++numKilled;
				//System.out.println("Creep dies and drops a:"+inventory.getInventoryList().get(0).getName());
				if(numKilled==5) {
					inventory.removeItem(inventory.getInventoryList().get(0));
					Item item = GameFactories.getInstance().getItemFactory().CreateItem("silverkey");
					item.setMonsterDropLevel(elementType.getLevel());
					ItemManager.getInstance().addItemIntoPlay(item);
					inventory.AddItem(item);
				}
				else if(numKilled==10) {
					inventory.removeItem(inventory.getInventoryList().get(0));
					Item item = GameFactories.getInstance().getItemFactory().CreateItem("goldkey");
					item.setMonsterDropLevel(elementType.getLevel());
					ItemManager.getInstance().addItemIntoPlay(item);
					inventory.AddItem(item);
				}
				else if(numKilled==15) {
					inventory.removeItem(inventory.getInventoryList().get(0));
					Item item = GameFactories.getInstance().getItemFactory().CreateItem("bosskey");
					item.setMonsterDropLevel(elementType.getLevel());
					ItemManager.getInstance().addItemIntoPlay(item);
					inventory.AddItem(item);
				}
				InventoryBagStaticElement bag = (InventoryBagStaticElement) GameFactories.getInstance().getStaticElementFactory().newInventoryBagElement(inventory.getInventoryList().get(0), (TileMap)this.getCurrentMap(), this.getPosition(), this);
				//GameCore.getBags().add(bag);
				System.out.println(numKilled);
			} catch (Exception e) {};
			InventoryManager.getInstance().addCreepInventory(this, this.inventory);
		}
		this.getAI().die();
	}

	public CreepElementType getElementType() {
		return this.elementType;
	}

	protected void setElementType(CreepElementType elementType) {
		this.elementType = elementType;
	}
	
	public ActionResultPickUp pickUp(Direction direction) {
		// TODO ¿How do they attack?
		return ActionResultPickUp.notExecuted;
	}

	@Override
	public ActionResultDelete delete(Direction direction) {
		// TODO ¿How do they attack?
		return ActionResultDelete.notExecuted;
	}

}
