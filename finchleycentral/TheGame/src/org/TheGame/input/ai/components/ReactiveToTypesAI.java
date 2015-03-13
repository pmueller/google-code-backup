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
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameObjects;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.maputils.Area;

/**
 * @author Facundo Manuel Quiroga 05/10/2008
 */
public abstract class ReactiveToTypesAI extends AIComponent {
	protected Collection<String> typeNames;
	protected Collection<String> teamNames;

	public ReactiveToTypesAI(Collection<String> typeNames, Collection<String> teamNames) {
		super();
		this.setTypeNames(new ArrayList<String>(typeNames));
		this.setTeamNames(new ArrayList<String>(teamNames));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ai.AI#doThink()
	 */
	@Override
	public List<Action> doThink() throws InvalidPositionException, OccupiedPositionException {
		Creep creep = this.getCreep();
		List<Action> actions = new ArrayList<Action>();
		if (!creep.isMoving()) {
			Area areaOfDetection = Area.newAreaAround(creep.getPosition(), this
					.getAreaOfDetection());
			Collection<Element> elements = creep.getCurrentMap().elementsIn(
					areaOfDetection);
			elements.remove(creep);

			ArrayList<Element> possibleTargets = new ArrayList<Element>();
			for (Element element : elements) {
				if (this.getTypeNames().contains(
						element.getElementType().getName())) {
					possibleTargets.add(element);
				} else if(this.getTeamNames().contains(
						element.getElementType().getTeam())) {
					possibleTargets.add(element);
					//System.out.println(creep.getElementType().getName() + " found opposing team: " + element.getElementType().getTeam());
				} 	
			}

			if (possibleTargets.size() > 0) {
				int selectedElementIndex = GameObjects.getInstance()
						.getRandomNumberGenerator().randomNumber()
						% possibleTargets.size();
				Element selectedElement = possibleTargets
						.get(selectedElementIndex);
				actions.addAll(this.reactTo(selectedElement));
			}
			actions = this.doThinkHook(actions);
		}
		return actions;
	}
	protected List<Action> doThinkHook(List<Action> actions) {
		return actions;
	}
	protected abstract List<Action> reactTo(Element selectedElement) throws InvalidPositionException, OccupiedPositionException;

	protected Collection<String> getTypeNames() {
		return this.typeNames;
	}

	protected void setTypeNames(Collection<String> types) {
		this.typeNames = types;
	}
	
	protected Collection<String> getTeamNames() {
		return this.teamNames;
	}
	
	protected void setTeamNames(Collection<String> teams) {
		this.teamNames = teams;
	}
}
