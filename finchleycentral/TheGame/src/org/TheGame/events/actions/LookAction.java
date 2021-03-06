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
import org.TheGame.events.actions.results.ActionResultNone;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.map.maputils.Position;

/**
 * @author Facundo Manuel Quiroga Mar 16, 2009
 */
public class LookAction extends Action {
	public LookAction(Player looker, Position position) {
		looker.lookAt(position);
	}

	@Override
	public ActionResult execute() {
		return new ActionResultNone();
	}

}
