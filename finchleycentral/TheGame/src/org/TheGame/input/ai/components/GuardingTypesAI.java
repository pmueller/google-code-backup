package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.main.GameObjects;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Distance;
import org.TheGame.model.map.maputils.Position;

public class GuardingTypesAI extends ReactiveToTypesAI {

	public enum State {DEFEND, FIND};
	State state;
	Collection<String> defendFromTypeNames;
	Collection<String> defendFromTeamNames;
	
	public GuardingTypesAI(Collection<String> typeNames, Collection<String> teamNames, 
			Collection<String> defendFromTypeNames, Collection<String> defendFromTeamNames) {
		super(typeNames, teamNames);
		this.defendFromTypeNames = defendFromTypeNames;
		this.defendFromTeamNames = defendFromTeamNames;
	
		state = State.FIND;
		// TODO Auto-generated constructor stub
	}
	
	public int getMaxDistanceFromPlayer() {
		return 3;
	}
	
	protected Collection<String> getDefendFromTypeNames() {
		return this.defendFromTypeNames;
	}
	
	protected Collection<String> getDefendFromTeamNames() {
		return this.defendFromTeamNames;
	}
	
	@Override
	protected List<Action> reactTo(Element selectedElement) {
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		Direction direction = creepsPosition.directionToGoTo(selectedElement.getPosition());
		Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
		Distance distance = creepsPosition.distanceTo(selectedElement.getPosition());
		if(distance.getHorizontalDistance() > this.getMaxDistanceFromPlayer() 
				|| distance.getVerticalDistance() > this.getMaxDistanceFromPlayer())
			state = State.FIND;
		switch(this.state) {
			case FIND:
				if(nextPosition.equals(selectedElement.getPosition())) {
					//next to the person we should be defending, so start DEFENDing
					state = State.DEFEND;
				}
				else {
					//we are a short distance away from the person we are defending
					try {
						if(!tileMap.occupable(nextPosition)) {
							direction = this.randomValidDirectionForMoving();
						}
						actions.add(new WalkAction(creep, direction));
					} catch(NoValidDirectionForMovingException e) {
						
					}
				}
				
			case DEFEND:
				Area area = Area.newAreaAround(creepsPosition, this.getAreaOfDetection());
				Collection<Element> elements = creep.getCurrentMap().elementsIn(area); 
				elements.remove(creep);
				elements.remove(selectedElement);
				List<Element> possibleTargets = new ArrayList<Element>();
				for(Element element : elements){
					if(this.getDefendFromTypeNames().contains(element.getElementType().getName()))
						possibleTargets.add(element);
					else if(this.getDefendFromTeamNames().contains(element.getElementType().getTeam()))
						possibleTargets.add(element);
				}
				if (possibleTargets.size() > 0) {
					int selectedElementIndex = GameObjects.getInstance()
							.getRandomNumberGenerator().randomNumber()
							% possibleTargets.size();
					Element selectedTarget = possibleTargets
							.get(selectedElementIndex);
					actions.addAll(this.attack(selectedTarget));
				}
				if (possibleTargets.size() == 0) {
					this.state = State.FIND;
				}
				
		}
		return actions;
	}
	
	protected Collection<Action> attack(Element selectedElement) {
		List<Action> actions = new ArrayList<Action>();
		Creep creep = this.getCreep();
		TileMap tileMap = creep.getCurrentTileMap();
		Position creepsPosition = creep.getPosition();
		Direction direction = creepsPosition.directionToGoTo(selectedElement
				.getPosition());
		Position nextPosition = creepsPosition.positionAfterMovingIn(direction);
		if (nextPosition.equals(selectedElement.getPosition())) {
			// I am next to the guy! tear him apart!
			actions.add(new AttackAction(this.getCreep(), direction));
		} else {
			try {
				if (!tileMap.occupable(nextPosition)) {
					direction = this.randomValidDirectionForMoving();
				}
				actions.add(new WalkAction(this.getCreep(), direction));
			} catch (NoValidDirectionForMovingException e) {
				// cant move.. ok, do nothing.
			}
		}

		return actions;
	}
}
