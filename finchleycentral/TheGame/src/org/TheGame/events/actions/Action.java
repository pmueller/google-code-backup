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

package org.TheGame.events.actions;

import org.TheGame.events.actions.results.ActionResult;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;

/**
 * An abstract action an Element can perform. Only gets done when execute is
 * called.
 * 
 * @author Facundo Manuel Quiroga
 */
public abstract class Action {

	public abstract ActionResult execute() throws InvalidPositionException, OccupiedPositionException;
}
