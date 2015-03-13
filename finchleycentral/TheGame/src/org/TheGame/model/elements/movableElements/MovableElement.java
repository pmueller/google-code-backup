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

package org.TheGame.model.elements.movableElements;

import java.util.ArrayList;
import java.util.Collection;

import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.logic.DiceRoller;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.player.AttributeState;
import org.TheGame.model.elements.movableElements.player.HealthState;
import org.TheGame.model.map.AbstractTileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.graphics.element.movable.MovableElementGraphic;

/**
 * @author Facundo Manuel Quiroga 08/4/2008
 */

public abstract class MovableElement extends Element {

	protected static Direction getDefaultStartingDirection() {
		return Direction.newDirection("n");
	}

	protected double sneakingSpeed = 0.5;

	protected double walkingSpeed = 1;

	protected double runningSpeed = 2;

	protected double jumpingSpeed = 3;

	protected MovementState movementState;

	protected MovableElementGraphic graphic;

	protected Direction direction; // where the element is looking at

	protected HealthState healthState;

	protected MovableElementType elementType;
	
	protected boolean attacked;
	
	protected Element attacker;

	protected Collection<MovableElementObserver> observers;

	public MovableElement(MovableElementGraphic graphic) {
		this.setGraphic(graphic);
		graphic.setMovableElement(this);
		this.setMovementState(new IdleMovementState(this));
		this.setDirection(MovableElement.getDefaultStartingDirection());
		this.setObservers(new ArrayList<MovableElementObserver>());
		this.register(graphic);
	}

	public void update(long timeElapsed) {
		this.getMovementState().update(timeElapsed);
		this.notifyObserversOfUpdate(timeElapsed);
		this.getHealthState().update(timeElapsed);
	}

	public void move(Direction direction, int speed) {
		this.getMovementState().move(direction, speed);
	}

	public void walk(Direction direction) {
		this
				.move(
						direction,
						(int) (this.getElementType().getBaseSpeed() / this.walkingSpeed));
	}

	public void run(Direction direction) {
		this
				.move(
						direction,
						(int) (this.getElementType().getBaseSpeed() / this.runningSpeed));
	}

	public void sneak(Direction direction) {
		this
				.move(
						direction,
						(int) (this.getElementType().getBaseSpeed() / this.sneakingSpeed));
	}

	public void jump(Direction direction) {
		this
				.move(
						direction,
						(int) (this.getElementType().getBaseSpeed() / this.jumpingSpeed));
	}

	// STATE

	public HealthState getHealthState() {
		return this.healthState;
	}

	public void setHealthState(HealthState state) {
		this.healthState = state;
	}

	// GRAPHIC
	public MovableElementGraphic getGraphic() {
		return this.graphic;
	}

	private void setGraphic(MovableElementGraphic graphic) {
		this.graphic = graphic;
	}

	// DIRECTION
	public Direction getDirection() {
		return direction;
	}

	protected boolean facesDirection(Direction direction) {
		return (direction.equals(this.getDirection()));
	}

	protected void changeDirection(Direction direction) {
		this.setDirection(direction);
		this.notifyObserversOfChangeDirection(direction);
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	// SPEED

	protected int getDiagonalSpeed(int speed) {
		return (int) (speed * 1.4);
	}

	// MOVEMENT
	public boolean isMoving() {
		return !this.getMovementState().isIdle();
	}

	public MovementState getMovementState() {
		return this.movementState;
	}

	public void setMovementState(MovementState movementState) {
		this.movementState = movementState;
	}

	/**
	 * OBSERVERS Notify them of changes Register/Unregister observers
	 */
	protected Collection<MovableElementObserver> getObservers() {
		return this.observers;
	}

	protected void setObservers(Collection<MovableElementObserver> observers) {
		this.observers = observers;
	}

	public void register(MovableElementObserver observer) {
		this.getObservers().add(observer);
	}

	public void unregister(MovableElementObserver observer) {
		this.getObservers().remove(observer);
	}

	protected void notifyObserversOfMove(Direction direction, int speed) {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.move(direction, speed);
		}
	}

	protected void notifyObserversOfAttack(Direction direction) {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.attack(direction);
		}
	}

	protected void notifyObserversOfDeath() {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.die();
		}
	}

	protected void notifyObserversOfStoppedMoving() {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.stoppedMoving();
		}
	}

	protected void notifyObserversOfChangeDirection(Direction direction) {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.changeDirection(direction);
		}
	}

	protected void notifyObserversOfAttackedFrom(Direction direction) {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.attackedFrom(direction);
		}
	}

	protected void notifyObserversOfFinishedAttack() {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.finishedAttack();
		}
	}

	public void notifyObserversOfUpdate(long timeElapsed) {
		for (MovableElementObserver observer : this.getObservers()) {
			observer.update(timeElapsed);
		}
	}
	
	public abstract MovableElementType getElementType();

	// TODO add precision of strike as parameter
	public ActionResultAttack defendFromAttackBy(Element attacker,
			int attackStrength) throws InvalidPositionException, OccupiedPositionException {
		MovableElementType elementType = this.getElementType();
		elementType.getCharacteristics().usedSkillWithIntensityAndProbability(
				"Dodge", 3, 90);
		if (DiceRoller.rollDiceWithProbability(elementType.getChanceToDodge())) {
			/*
			 * elementType.getCharacteristics().getAttributeNamed("Dexterity").usedWith
			 * (1);
			 * elementType.getCharacteristics().getAttributeNamed("Speed").usedWith
			 * (1);
			 * elementType.getCharacteristics().getAttributeNamed("Concentration"
			 * ).usedWith(1);
			 */
			return ActionResultAttack.dodge;
		} else {
			int damageDealt = attackStrength - elementType.getDamageReduction();
			AttributeState health = this.getHealthState().getHealth();
			health.updateCurrentValue(-1 * damageDealt);
			elementType.getCharacteristics()
					.usedAttributeWithIntensityAndProbability("Constitution",
							3, 90);
			elementType.getCharacteristics()
					.usedAttributeWithIntensityAndProbability("Endurance", 1,
							80);
			elementType.getCharacteristics()
					.usedAttributeWithIntensityAndProbability("Willpower", 1,
							40);
			if (health.getCurrentValue() == 0) {
				this.setAttacker(attacker);
				this.die();
			}
			
			this.setAttacked();
			this.setAttacker(attacker);
			return ActionResultAttack.hit;
		}
	}
	
	public ActionResultPickUp defendFromPickUpBy(Element attacker)
			throws InvalidPositionException, OccupiedPositionException {
		return ActionResultPickUp.cannotBePickedUp;
	}

	public ActionResultDelete defendFromDeleteBy(Element attacker)
			throws InvalidPositionException, OccupiedPositionException {
		return ActionResultDelete.cannotBeDeleted;
	}

	public ActionResultAttack attack(Direction direction) throws InvalidPositionException, OccupiedPositionException {
		AbstractTileMap currentMap = this.getCurrentMap();
		Position targetPosition = this.getPosition().positionAfterMovingIn(
				direction);
		if (currentMap.validPosition(targetPosition)) {
			this.notifyObserversOfAttack(direction);
			if (currentMap.existsElementAt(targetPosition)) {
				MovableElementType elementType = this.getElementType();
				int staminaHitCost = elementType.getStaminaHitCost();
				if (this.getHealthState().getStamina().getCurrentValue() >= staminaHitCost) {
					elementType.getCharacteristics()
							.usedSkillWithIntensityAndProbability("Weapons", 1,
									10);
					if (DiceRoller.rollDiceWithProbability(elementType
							.getChanceToHit())) {
						elementType.getCharacteristics()
								.usedSkillWithIntensityAndProbability(
										"Weapons", 1, 90);
						Element element = currentMap.elementAt(targetPosition);
						this.getHealthState().getStamina().updateCurrentValue(
								-1 * staminaHitCost);
						this.notifyObserversOfFinishedAttack();
						return element.defendFromAttackBy(this, this
								.getElementType().getDamage());
					} else {
						this.notifyObserversOfFinishedAttack();
						return ActionResultAttack.miss;
					}
				} else {
					this.notifyObserversOfFinishedAttack();
					return ActionResultAttack.couldNotPerform;
				}
			} else {
				this.notifyObserversOfFinishedAttack();
				return ActionResultAttack.invalidTarget;
			}
		} else {
			return ActionResultAttack.invalidTarget;
		}
	}
	
	public ActionResultAttack attackAtRange(Element target) throws InvalidPositionException, OccupiedPositionException {
		AbstractTileMap currentMap = this.getCurrentMap();
		Position targetPosition = target.getPosition();
		if (currentMap.validPosition(targetPosition)) {
			this.notifyObserversOfAttack(this.getPosition().directionToGoTo(targetPosition));
			if (currentMap.existsElementAt(targetPosition)) {
				MovableElementType elementType = this.getElementType();
				int staminaHitCost = elementType.getStaminaHitCost();
				if (this.getHealthState().getStamina().getCurrentValue() >= staminaHitCost) {
					elementType.getCharacteristics()
							.usedSkillWithIntensityAndProbability("Weapons", 1,
									10);
					if (DiceRoller.rollDiceWithProbability(elementType
							.getChanceToHit())) {
						elementType.getCharacteristics()
								.usedSkillWithIntensityAndProbability(
										"Weapons", 1, 90);
						Element element = currentMap.elementAt(targetPosition);
						this.getHealthState().getStamina().updateCurrentValue(
								-1 * staminaHitCost);
						this.notifyObserversOfFinishedAttack();
						return element.defendFromAttackBy(this, this
								.getElementType().getDamage());
					} else {
						this.notifyObserversOfFinishedAttack();
						return ActionResultAttack.miss;
					}
				} else {
					this.notifyObserversOfFinishedAttack();
					return ActionResultAttack.couldNotPerform;
				}
			} else {
				this.notifyObserversOfFinishedAttack();
				return ActionResultAttack.invalidTarget;
			}
		} else {
			return ActionResultAttack.invalidTarget;
		}
	}
}
