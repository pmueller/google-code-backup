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

package org.TheGame.visualization.graphics.element.movable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.elements.movableElements.MovableElementObserver;
import org.TheGame.model.elements.movableElements.player.AttributeState;
import org.TheGame.model.elements.movableElements.player.HealthState;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.visualization.graphics.element.ElementGraphic;
import org.TheGame.visualization.graphics.element.ScreenPoint;

/**
 * As ElementGraphic, represents a movable element's avatar, strictly at pixel
 * level, containing the graphics of the element when heading in any direction,
 * and the animations of the element when walking in any direction
 * 
 * @author Facundo Manuel Quiroga
 */

public class MovableElementGraphic extends ElementGraphic implements
		MovableElementObserver {

	private MovableElement movableElement;

	private Direction direction;

	private MovableSprite idle;
	private MovableSprite walking;
	private MovableSprite attack;

	protected GraphicMovementState graphicMovementState;

	public MovableElementGraphic(MovableSprite idle, MovableSprite walking, 
									MovableSprite attack) {
		super();
		this.setIdle(idle);
		this.setWalking(walking);
		this.setAttack(attack);
		this.doStopMoving();
	}



	public void doMove(Direction direction, int speed) {
		this.getWalking().setSpeed(speed);
		//System.out.println(speed);
		this.setGraphicMovementState(new GraphicMovingMovementState(this, this
				.getWalking(), direction, speed));
	}

	public void doStopMoving() {
		this.setGraphicMovementState(new GraphicIdleMovementState(this, this
				.getIdle()));
	}

	public void doChangeDirection(Direction direction) {
		this.setDirection(direction);
		this.getIdle().changeDirection(direction);
		this.getWalking().changeDirection(direction);
		this.getAttack().changeDirection(direction);
	}

	public int getXCorrection() {
		return this.getGraphicMovementState().getXDifference();
	}

	public int getYCorrection() {
		return this.getGraphicMovementState().getYDifference();
	}

	/**
	 * Draws an element's graphic according to it's current sprite Also draws
	 * its current state.
	 * 
	 * @param g
	 *            Graphics context where to draw
	 * @param originalScreenX
	 *            X Position on the screen to draw
	 * @param originalScreenY
	 *            Y Position on the screen to draw
	 */
	public void draw(Graphics2D g, int originalScreenX, int originalScreenY) {
		super.draw(g, originalScreenX, originalScreenY);
		Image image = this.getCurrentImage();
		ScreenPoint point = this.getDrawingScreenPoint(originalScreenX,
				originalScreenY, image);
		this.drawHealthState(g, point);
	}

	  private AlphaComposite makeComposite(float alpha) {
		    int type = AlphaComposite.SRC_OVER;
		    return(AlphaComposite.getInstance(type, alpha));
	  }

	private void drawHealthState(Graphics2D g, ScreenPoint point) {
		HealthState state = this.getMovableElement().getHealthState();
		AttributeState health = state.getHealth();
		AttributeState stamina = state.getStamina();

		if (state != null) {
			Composite originalComposite = g.getComposite();
			float alpha = (float) 0.5;
			g.setFont(new Font("Arial", 1, 10));
			g.setColor(Color.BLACK);
			int filled = (int) (health.getCurrentValue()/health.getMaximumValue().getValue()*50);
			g.drawRect(point.x, point.y-20, 50, 10);
			g.setColor(Color.RED);
			g.setComposite(makeComposite(alpha));
			g.fillRect(point.x+1, point.y-19, filled-1, 9);
			g.setColor(Color.BLACK);
			g.setComposite(originalComposite);
			g.drawString(health.getCurrentValue()+" ", point.x+2, point.y-11);
			g.setColor(Color.BLACK);
			filled = (int) (stamina.getCurrentValue()/stamina.getMaximumValue().getValue()*50);
			g.drawRect(point.x, point.y-10, 50, 10);
			g.setColor(Color.YELLOW);
			g.setComposite(makeComposite(alpha));
			g.fillRect(point.x+1, point.y-9, filled-1, 9);
			g.setColor(Color.BLACK);
			g.setComposite(originalComposite);
			g.drawString(stamina.getCurrentValue()+" ", point.x+2, point.y-1);
			/*
			g.setColor(Color.YELLOW);
			g.drawString(stamina.getCurrentValue() + "/"
					+ stamina.getMaximumValue().getValue(), point.x,
					point.y + 10);*/
		}
	}

	/**
	 * Updates the sprite position, if necessary
	 */
	public void update(long timeElapsed) {
		this.getGraphicMovementState().update(timeElapsed);
	}

	@Override
	public Image getCurrentImage() {
		return this.getCurrentSprite().getImage();
	}

	// SPRITES
	protected MovableSprite getCurrentSprite() {
		return this.getGraphicMovementState().getMovableSprite();
	}

	protected MovableSprite getIdle() {
		return idle;
	}

	protected void setIdle(MovableSprite idle) {
		this.idle = idle;
	}

	protected MovableSprite getWalking() {
		return walking;
	}

	protected void setWalking(MovableSprite walking) {
		this.walking = walking;
	}
	
	protected MovableSprite getAttack() {
		return attack;
	}
	
	protected void setAttack(MovableSprite attack) {
		this.attack = attack;
	}
	
	// DIRECTION
	protected Direction getDirection() {
		return direction;
	}

	protected void setDirection(Direction direction) {
		this.direction = direction;
	}

	protected MovableElement getMovableElement() {
		return this.movableElement;
	}

	public void setMovableElement(MovableElement movableElement) {
		this.movableElement = movableElement;
	}

	@Override
	public void attack(Direction direction) {
		// TODO attacking sprites needed!
		//this.setGraphicMovementState(new GraphicMovingMovementState(this, this
		//		.getAttack(), direction, 120));
		this.getGraphicMovementState().setMovableSprite(attack.clone());
		//System.out.println("Attack!" + direction.toString());

	}

	@Override
	public void attackedFrom(Direction direction) {
		// TODO Defending Sprites Needed!
		System.out.println("Attack FROM!" + direction.toString());
		

	}
	
	@Override
	public void finishedAttack() {
		// Finished Attack
		//this.getGraphicMovementState().setMovableSprite(walking);
		//System.out.println("Finish Attacking");

	}
	@Override
	public void die() {
		// Do nothing; maybe, sometime, ghost sprite and/or dying animation

	}

	@Override
	public void changeDirection(Direction direction) {
		this.getGraphicMovementState().changeDirection(direction);
	}

	/**
	 * Move one block in a direction with a speed if im facing the same
	 * direction i am going to move, i just move speed as in ms/tile, ej
	 * speed=118ms, blockSize=59 118 ms ____ 1 tile 59 pixels ___ 1 tile 59
	 * pixels ___ 118 ms 1/2 pixels ___ 1 ms
	 */
	@Override
	public void move(Direction direction, int speed) {
		this.getGraphicMovementState().move(direction, speed);
	}

	@Override
	public void stoppedMoving() {
		this.getGraphicMovementState().stoppedMoving();
	}

	protected GraphicMovementState getGraphicMovementState() {
		return this.graphicMovementState;
	}

	protected void setGraphicMovementState(
			GraphicMovementState graphicMovementState) {
		this.graphicMovementState = graphicMovementState;
	}

	public MovableElementGraphic clone() {
		MovableSprite idle = this.getIdle().clone();
		MovableSprite walking = this.getWalking().clone();
		MovableSprite attack = this.getAttack().clone();
		return new MovableElementGraphic(idle, walking, attack);
	}

}
