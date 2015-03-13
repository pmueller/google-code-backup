package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.events.actions.RangedAttackAction;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class SuicideBombingTypesAI extends ReactiveToTypesAI {
	private boolean attacked = false;
	public SuicideBombingTypesAI(Collection<String> typeNames, Collection<String> teamNames) {
		super(typeNames, teamNames);
	}
	
	@Override
	protected List<Action> reactTo(Element selectedElement) throws InvalidPositionException, OccupiedPositionException {
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		Direction direction = creepsPosition.directionToGoTo(selectedElement.getPosition());
		Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
		if(attacked == true)
			creep.die();
		if(nextPosition.equals(selectedElement.getPosition())) {
			//int range = this.getCreep().getElementType().getRange();
			int range = 3;
			Area attackArea = Area.newAreaAround(creepsPosition, range);
			Collection<Element> elements = creep.getCurrentMap().elementsIn(attackArea);
			elements.remove(creep);
			for(Element e : elements) {
				actions.add(new RangedAttackAction(creep, e));
			}
			attacked = true;
		} else {
			try {
				if(!tileMap.occupable(nextPosition)) {
					direction = this.randomValidDirectionForMoving();
				}
				actions.add(new WalkAction(creep, direction));
			} catch (NoValidDirectionForMovingException e) {
				
			}
		}
		
		return actions;
	}

}
