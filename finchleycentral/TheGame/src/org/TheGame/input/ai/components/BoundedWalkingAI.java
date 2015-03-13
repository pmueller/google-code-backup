package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class BoundedWalkingAI extends AIComponent {
	Area bounds;
	
	public BoundedWalkingAI(List<Position> bounds) {
		this.bounds = new Area(bounds.get(0), bounds.get(1));
	}
	
	@Override
	public List<Action> doThink() {
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		try {
			Direction direction = this.randomValidDirectionForMoving();
			Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
				
			if(bounds.contains(nextPosition)) {
				//we are trying to move within bounds, so it's allowed
				if(tileMap.occupable(nextPosition)) {
					actions.add(new WalkAction(creep, direction));
				}
			}
		} catch (NoValidDirectionForMovingException e) {
			
		}
		return actions;
	}

}
