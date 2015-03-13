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

package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

/**
 * Follows player on sight
 * 
 * @author Facundo Manuel Quiroga 30/09/2008
 */
public class FollowingTypesAI extends ReactiveToTypesAI {

	public FollowingTypesAI(Collection<String> typeNames, Collection<String> teamNames) {
		super(typeNames, teamNames);
	}

	@Override
	protected List<Action> reactTo(Element selectedElement) {
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		Direction direction = creepsPosition.directionToGoTo(selectedElement
				.getPosition());
		Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
		if (nextPosition.equals(selectedElement.getPosition())) {
			// I am next to the guy! tear him apart!
			actions.add(new AttackAction(this.getCreep(), direction));
		} else {
			try {
				if (!tileMap.occupable(nextPosition)) {
					direction = this.randomValidDirectionForMoving();
				}
				actions.add(new WalkAction(this.getCreep(), direction));
			} catch (NoValidDirectionForMovingException e) {
				// cant move.. ok, do nothing.
			}
		}

		return actions;
	}
}
