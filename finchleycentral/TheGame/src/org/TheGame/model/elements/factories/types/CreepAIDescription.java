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

package org.TheGame.model.elements.factories.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.TheGame.exceptions.GameLoadingError;
import org.TheGame.input.ai.AI;
import org.TheGame.input.ai.components.AIComponent;
import org.TheGame.input.ai.components.BoundedWalkingAI;
import org.TheGame.input.ai.components.CounteringAI;
import org.TheGame.input.ai.components.EscapingFromTypesAI;
import org.TheGame.input.ai.components.FollowingTypesAI;
import org.TheGame.input.ai.components.GuardingTypesAI;
import org.TheGame.input.ai.components.HealingTypesAI;
import org.TheGame.input.ai.components.PassiveFollowingTypesAI;
import org.TheGame.input.ai.components.PassiveAI;
import org.TheGame.input.ai.components.PathWalkingAI;
import org.TheGame.input.ai.components.ProjectileAI;
import org.TheGame.input.ai.components.SuicideBombingTypesAI;
import org.TheGame.input.ai.components.TextAI;
import org.TheGame.model.map.maputils.Position;

/**
 * Describes a creep's AI. Can create it with "createAI".. TODO implemented
 * quick'n'dirty.
 * 
 * @author Facundo Manuel Quiroga Mar 10, 2009
 */
public class CreepAIDescription {
	static public final String following = "following";
	static public final String escaping = "escaping";
	static public final String passiveFollowing = "passiveFollowing";
	static public final String passive = "passive";
	static public final String suicide = "suicide";
	static public final String bodyguard = "bodyguard";
	static public final String counter = "counter";
	static public final String pathWalking = "pathWalking";
	static public final String boundedWalking = "boundedWalking";
	static public final String projectile = "projectile";
	static public final String healing = "healing";
	static public final String explain = "explain";
	
	protected String type;
	protected ArrayList<String> typesReactiveTo;
	protected ArrayList<String> typesDefendingFrom;
	protected ArrayList<String> teamsReactiveTo;
	protected ArrayList<String> teamsDefendingFrom;
	protected ArrayList<Position> positions;
	protected String[] text;
	
	public CreepAIDescription(String type) {
		super();
		this.setType(type);
	}
	
	public CreepAIDescription(String type, ArrayList<String> typesReactiveTo,
			ArrayList<String> teamsReactiveTo) {
		super();
		this.setType(type);
		this.setTypesReactiveTo(new ArrayList<String>(typesReactiveTo));
		this.setTeamsReactiveTo(new ArrayList<String>(teamsReactiveTo));
	}
	
	public CreepAIDescription(String type, ArrayList<String> typesReactiveTo, 
			ArrayList<String> teamsReactiveTo, ArrayList<String> typesDefendingFrom, 
			ArrayList<String> teamsDefendingFrom) {
		this(type, typesReactiveTo, teamsReactiveTo);
		this.setTypesDefendingFrom(new ArrayList<String>(typesDefendingFrom));
		this.setTeamsDefendingFrom(new ArrayList<String>(teamsDefendingFrom));
	}

	
	public CreepAIDescription(String type, ArrayList<Position> positions) {
		this(type);
		this.setPositions(new ArrayList<Position>(positions));
	}
	
	public CreepAIDescription(String type, String[] text) {
		this(type);
		this.setText(text);
	}
	
	protected String getType() {
		return this.type;
	}

	protected void setType(String type) {
		this.type = type;
	}

	protected ArrayList<String> getTypesReactiveTo() {
		return this.typesReactiveTo;
	}

	protected void setTypesReactiveTo(ArrayList<String> typesReactiveTo) {
		this.typesReactiveTo = typesReactiveTo;
	}

	protected ArrayList<String> getTypesDefendingFrom() {
		return this.typesDefendingFrom;
	}
	
	protected void setTypesDefendingFrom(ArrayList<String> typesDefendingFrom) {
		this.typesDefendingFrom = typesDefendingFrom;
	}
	
	protected ArrayList<String> getTeamsReactiveTo() {
		return this.teamsReactiveTo;
	}
	
	protected void setTeamsReactiveTo(ArrayList<String> teamsReactiveTo) {
		this.teamsReactiveTo = teamsReactiveTo;
	}
	
	protected ArrayList<String> getTeamsDefendingFrom() {
		return this.teamsDefendingFrom;
	}
	
	protected void setTeamsDefendingFrom(ArrayList<String> teamsDefendingFrom) {
		this.teamsDefendingFrom = teamsDefendingFrom;
	}
	
	protected List<Position> getPositions() {
		return this.positions;
	}
	
	protected void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}
	
	protected String[] getText() {
		return this.text;
	}
	
	protected void setText(String[] text) {
		this.text = text;
	}
	
	public AI createCreepAI() {
		AIComponent aiComponent;
		if (this.getType().equals(CreepAIDescription.following)) {
			aiComponent = new FollowingTypesAI(this.getTypesReactiveTo(), 
					this.getTeamsReactiveTo());
		} else if (this.getType().equals(CreepAIDescription.escaping)) {
			aiComponent = new EscapingFromTypesAI(this.getTypesReactiveTo(), 
					this.getTeamsReactiveTo());
		} else if (this.getType().equals(CreepAIDescription.passiveFollowing)) {
			aiComponent = new PassiveFollowingTypesAI(this.getTypesReactiveTo(), 
					this.getTeamsReactiveTo());
		} else if (this.getType().equals(CreepAIDescription.passive)) {
			aiComponent = new PassiveAI();
		} else if (this.getType().equals(CreepAIDescription.suicide)) {
			aiComponent = new SuicideBombingTypesAI(this.getTypesReactiveTo(),
					this.getTeamsReactiveTo());
		} else if (this.getType().equals(CreepAIDescription.bodyguard)) {
			aiComponent = new GuardingTypesAI(this.getTypesReactiveTo(), 
					this.getTeamsReactiveTo(), this.getTypesDefendingFrom(), 
					this.getTeamsDefendingFrom());
		} else if (this.getType().equals(CreepAIDescription.counter)) {
			aiComponent = new CounteringAI();
		} else if (this.getType().equals(CreepAIDescription.pathWalking)) {
			aiComponent = new PathWalkingAI(this.getPositions());
		} else if (this.getType().equals(CreepAIDescription.boundedWalking)){
			aiComponent = new BoundedWalkingAI(this.getPositions());
		} else if (this.getType().equals(CreepAIDescription.projectile)){
			aiComponent = new ProjectileAI();
		} else if (this.getType().equals(CreepAIDescription.healing)) {
			aiComponent = new HealingTypesAI(this.getTypesReactiveTo(),
					this.getTeamsReactiveTo());
		} else if (this.getType().equals(CreepAIDescription.explain)){
			aiComponent = new TextAI(this.getText());
		} else {
			throw new GameLoadingError("Type of AI, " + this.getType()
					+ " is invalid");
		}
		return new AI(aiComponent);
	}
}
