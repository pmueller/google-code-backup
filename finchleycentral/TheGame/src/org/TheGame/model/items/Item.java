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

package org.TheGame.model.items;

import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;
import org.TheGame.model.entities.GameEntity;

/**
 * @author Facundo Manuel Quiroga Nov 21, 2008
 */
public abstract class Item extends GameEntity
{
	public ItemElementTypeDescription itemElementTypeDescription;
	public ElementGraphicDescription elementGraphicDescription;
	private int monsterDropLevel;
	public boolean checked = false;
	
	public Item(ElementGraphicDescription _elementGraphicDescription,
				ItemElementTypeDescription _itemElementTypeDescription)
	{
		super();
		itemElementTypeDescription = _itemElementTypeDescription;
		elementGraphicDescription = _elementGraphicDescription;
	}
	
	abstract public String getToolTip();
	abstract public boolean useItem();
	abstract public boolean pickUp();
	abstract public Item createItem();
	public String getName()
	{
		return itemElementTypeDescription.name;
	}
	
	public String getSlot()
	{
		return itemElementTypeDescription.slot;
	}
	
	public String getGraphic()
	{
		return elementGraphicDescription.getGraphic();
	}

	public void setMonsterDropLevel(int monsterDropLevel) {
		this.monsterDropLevel = monsterDropLevel;
	}

	public int getMonsterDropLevel() {
		return this.monsterDropLevel;
	}
	
	
}
