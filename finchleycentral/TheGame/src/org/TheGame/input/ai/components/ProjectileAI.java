package org.TheGame.input.ai.components;

import java.util.List;
import java.util.ArrayList;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.events.actions.WalkAction;

public class ProjectileAI extends AIComponent{

	@Override
	public List<Action> doThink() {
		Creep creep = this.getCreep();
		Direction creepDirection = creep.getDirection();
		TileMap  tileMap = creep.getCurrentTileMap();
		List<Action> actions = new ArrayList<Action>();
		if(tileMap.validPosition(creep.getPosition().positionAfterMovingIn(creepDirection)))
		{
			if(tileMap.occupable(creep.getPosition().positionAfterMovingIn(creepDirection)))
			{
				actions.add(new WalkAction(creep, creepDirection));
			} else {
				actions.add(new AttackAction(creep, creepDirection));
				creep.die();
			}
		} else {
			creep.die();
		}
		return actions;
	}
	
}
