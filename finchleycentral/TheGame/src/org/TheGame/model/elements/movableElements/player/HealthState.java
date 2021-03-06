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

import org.TheGame.main.GameObjects;

/**
 * @author Facundo Manuel Quiroga 13/10/2008
 */
public class HealthState extends EntityState {

	protected AttributeState health;
	protected AttributeState stamina;

	public HealthState(AttributeState health, AttributeState stamina) {
		this.setHealth(health);
		this.setStamina(stamina);
	}

	public void update(long timeElapsed) {
		if (GameObjects.getInstance().getRandomNumberGenerator().randomNumber() > 10) {
			this.getHealth().update(timeElapsed);
			this.getStamina().update(timeElapsed);
		}
	}

	public boolean isDead() {
		return this.getHealth().getCurrentValue() == 0;
	}

	public AttributeState getHealth() {
		return this.health;
	}

	protected void setHealth(AttributeState health) {
		this.health = health;
	}

	public AttributeState getStamina() {
		return this.stamina;
	}

	protected void setStamina(AttributeState stamina) {
		this.stamina = stamina;
	}

}
