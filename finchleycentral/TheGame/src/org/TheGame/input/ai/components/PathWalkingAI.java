package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

public class PathWalkingAI extends AIComponent {

	List<Position> waypoints;
	List<Position> path;
	int currentPoint;
	boolean hasPath;
	int currentPointInPath;
	
	public PathWalkingAI(List<Position> waypoints) {
		currentPoint = 0;
		this.waypoints = waypoints;
	}
	
	@Override
	public List<Action> doThink() {
		Position nextPositionToGoTo = waypoints.get(currentPoint);
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		label:
		if(creepsPosition.equals(nextPositionToGoTo)) {
			//we are where we want to be, update to the next waypoint
			if(currentPoint + 1 == waypoints.size())
				currentPoint = 0;
			else
				++currentPoint;
			hasPath = false;
		} 
		else {
			if(!hasPath) {
				path = this.findPathTo(nextPositionToGoTo);
				hasPath = path.size() != 0;
				currentPointInPath = 0;
				break label;
			}
			if(creepsPosition.equals(path.get(currentPointInPath))) {
				++currentPointInPath;
				if(currentPointInPath == path.size())
					hasPath = false;
				break label;
			}
			//System.out.println("PWAI: " + path.size());
			Direction direction = creepsPosition.directionToGoTo(path.get(currentPointInPath));
			
			Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
			try {
				if (!tileMap.occupable(nextPosition)) {
					this.hasPath = false;
					direction = this.randomValidDirectionForMoving();
				}
				actions.add(new WalkAction(this.getCreep(), direction));
			} catch (NoValidDirectionForMovingException e) {
				this.hasPath = false;
			}
		}
		return actions;
	}

}
