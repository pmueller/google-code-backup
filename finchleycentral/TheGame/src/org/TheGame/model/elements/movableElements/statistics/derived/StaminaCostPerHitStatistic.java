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

package org.TheGame.model.elements.movableElements.statistics.derived;

import java.util.ArrayList;

import org.TheGame.model.elements.movableElements.statistics.Statistic;
import org.TheGame.model.elements.movableElements.statistics.characteristics.attributes.Attribute;
import org.TheGame.model.elements.movableElements.statistics.characteristics.skills.Skill;

/**
 * @author Facundo Manuel Quiroga Jul 26, 2009
 */
public class StaminaCostPerHitStatistic extends DerivedStatistic {

	public static StaminaCostPerHitStatistic newStaminaCostPerHit(
			float baseValue, Attribute endurance, Attribute dexterity,
			Skill weapons) {
		ArrayList<Statistic> statistics = new ArrayList<Statistic>();
		;
		statistics.add(endurance);
		statistics.add(dexterity);
		statistics.add(weapons);
		return new StaminaCostPerHitStatistic("StaminaCostPerHit", baseValue,
				statistics);
	}

	public StaminaCostPerHitStatistic(String name, float baseValue,
			ArrayList<Statistic> dependingStatistics) {
		super(name, baseValue, dependingStatistics);
	}

	@Override
	public float calculate() {
		float value = this.getBaseValue()
				- (this.getValueOfDependingStatistic("Endurance") / 3
						+ this.getValueOfDependingStatistic("Dexterity") / 10 + this
						.getValueOfDependingStatistic("Weapons") / 3);
		return (value > 0) ? value : 1;
	}

}
