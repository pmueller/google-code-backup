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

import org.TheGame.model.elements.movableElements.MovableElementCharacteristics;
import org.TheGame.model.elements.movableElements.MovableElementType;

/**
 * @author Facundo Manuel Quiroga Mar 10, 2009
 */
public class CreepElementType extends MovableElementType {

	private int level;
	public CreepElementType(String name, String team, int level,
			MovableElementCharacteristics movableElementCharacteristics) {
		super(name, team, movableElementCharacteristics);
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}
}