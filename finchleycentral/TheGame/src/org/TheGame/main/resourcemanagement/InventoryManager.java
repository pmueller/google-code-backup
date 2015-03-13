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


import java.util.HashMap;

import org.TheGame.main.GameCore;
import org.TheGame.model.elements.Element;
import org.TheGame.model.items.Inventory;

public class InventoryManager {
	private static InventoryManager inventoryManager;
	private HashMap<Element, Inventory> inventories;

	public InventoryManager() {
		inventories = new HashMap<Element, Inventory>();
	}

	public Inventory getCreepInventory(Element e) {
		return inventories.get(e);
	}
	
	public Inventory getPlayerInventory(String type) {
		if(type.equals("normal")) {
			return GameCore.getInstance().getPlayer().getNormalInventory();
		}
		else if(type.equals("equipped")) {
			return GameCore.getInstance().getPlayer().getEquippedInventory();
		}
		return GameCore.getInstance().getPlayer().getQuestInventory();
	}

	public void addCreepInventory(Element e, Inventory i) {
		inventories.put(e, i);
	}

	public HashMap<Element, Inventory> getAll() {
		return this.inventories;
	}

	public static InventoryManager getInstance() {
		if (inventoryManager == null) {
			inventoryManager = new InventoryManager();
		}
		return inventoryManager;
	}

}
