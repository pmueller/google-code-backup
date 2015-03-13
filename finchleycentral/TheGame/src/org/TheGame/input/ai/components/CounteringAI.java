package org.TheGame.input.ai.components;

import java.util.List;
import java.util.ArrayList;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;

public class CounteringAI extends AIComponent{

	@Override
	public List<Action> doThink() {
		Creep creep = this.getCreep();
		Position creepsPosition = creep.getPosition();
		List<Action> actions = new ArrayList<Action>();
		if(creep.isAttacked()) {
			Element e = creep.getAttacker();
			actions.add(new AttackAction(creep, 
					creepsPosition.directionToGoTo(e.getPosition())));
			creep.resetAttacked();
		}
		else try {
			actions.add(new WalkAction(creep, this.randomValidDirectionForMoving()));	
		} catch (NoValidDirectionForMovingException e){
		
		}
		return actions;
	}
	
}
