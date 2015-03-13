package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class HealingTypesAI extends ReactiveToTypesAI {
	
	public HealingTypesAI(Collection<String> typeNames, Collection<String> teamNames) {
		super(typeNames, teamNames);
	}
	
	@Override
	protected List<Action> reactTo(Element selectedElement) {
		MovableElement selectedMovableElement = (MovableElement)selectedElement;
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		Direction direction = creepsPosition.directionToGoTo(selectedElement
				.getPosition());
		Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
		if (nextPosition.equals(selectedElement.getPosition())) {
			selectedMovableElement.getHealthState().getHealth().
				updateCurrentValue(creep.getElementType().getDamage());
		} else {
			try {
				if (!tileMap.occupable(nextPosition)) {
					direction = this.randomValidDirectionForMoving();
				}
				actions.add(new WalkAction(this.getCreep(), direction));
			} catch (NoValidDirectionForMovingException e) {
			}
		}

		return actions;
	}
}
