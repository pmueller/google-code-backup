package org.TheGame.input.ai.components;

import java.util.List;
import java.util.ArrayList;

import org.TheGame.events.actions.Action;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;

public class PassiveAI extends AIComponent{

	@Override
	public List<Action> doThink() {
		Creep creep = this.getCreep();
		List<Action> actions = new ArrayList<Action>();
		if(!creep.isMoving()) try {
			actions.add(new WalkAction(creep, this.randomValidDirectionForMoving()));	
		} catch (NoValidDirectionForMovingException e){
			
		}
		return actions;
	}
	
}
