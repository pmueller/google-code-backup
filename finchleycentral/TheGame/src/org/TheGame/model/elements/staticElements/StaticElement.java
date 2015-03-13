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

package org.TheGame.model.elements.staticElements;

import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.model.elements.Element;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.visualization.graphics.element.StaticElementGraphic;

/**
 * @author Facundo Manuel Quiroga 08/4/2008
 */
public class StaticElement extends Element {

	private StaticElementGraphic graphic;

	protected StaticElementType elementType;

	public StaticElement(StaticElementType elementType,
			StaticElementGraphic graphic) {
		super();
		this.setElementType(elementType);
		this.setGraphic(graphic);
	}

	public StaticElementGraphic getGraphic() {
		return this.graphic;
	}

	public void setGraphic(StaticElementGraphic graphic) {
		this.graphic = graphic;
	}

	public void update(long elapsedTime) {
		this.getGraphic().update(elapsedTime);
	}

	@Override
	public ActionResultAttack attack(Direction direction) {
		return ActionResultAttack.notExecuted;
	}

	public ActionResultAttack attackAtRange(Element target) {
		return ActionResultAttack.notExecuted;
	}
	
	@Override
	public ActionResultAttack defendFromAttackBy(Element attacker,
			int attackStrength) {
		return ActionResultAttack.cannotBeAttacked;
	}
	
	public ActionResultPickUp defendFromPickUpBy(Element attacker)
		throws InvalidPositionException, OccupiedPositionException {
		return ActionResultPickUp.cannotBePickedUp;
	}

	public ActionResultDelete defendFromDeleteBy(Element attacker)
		throws InvalidPositionException, OccupiedPositionException {
		return ActionResultDelete.cannotBeDeleted;
	}
	
	public ActionResultPickUp pickUp(Direction direction) {
		return ActionResultPickUp.notExecuted;
	}

	@Override
	public ActionResultDelete delete(Direction direction) {
		return ActionResultDelete.notExecuted;
	}

	public StaticElementType getElementType() {
		return this.elementType;
	}

	protected void setElementType(StaticElementType elementType) {
		this.elementType = elementType;
	}

}
