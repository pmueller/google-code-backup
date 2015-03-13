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

package org.TheGame.input.human;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import org.TheGame.events.actionProcessing.ActionPipeline;
import org.TheGame.events.actions.Action;
import org.TheGame.events.actions.AttackAction;
import org.TheGame.events.actions.DeleteAction;
import org.TheGame.events.actions.JumpAction;
import org.TheGame.events.actions.PickUpAction;
import org.TheGame.events.actions.RunAction;
import org.TheGame.events.actions.SneakAction;
import org.TheGame.events.actions.WalkAction;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameCore;
import org.TheGame.main.GameObjects;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.ImageManager;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.elements.movableElements.statistics.characteristics.attributes.Attribute;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.sound.MusicThread;
import org.TheGame.sound.SFXManager;
import org.TheGame.visualization.screen.ScreenManager;
import org.TheGame.visualization.screen.WindowedScreenManager;

/**
 * Generic input adapter, gets key/mouse inputs and transforms them into
 * messages to domain objects
 */
// TODO In the future, must partition into several different input adapters, and
// figure out where to configure it
public class InputAdapter {

	protected GameCore gameCore;

	protected Player player;

	protected GameInput moveLeft;

	protected GameInput moveRight;

	protected GameInput moveUp;

	protected GameInput moveDown;

	protected GameInput escape;

	protected GameInput run;

	protected GameInput sneak;

	protected GameInput jump;
	
	protected GameInput pause;
	
	protected GameInput pickUp;
	
	protected GameInput delete;

	protected InputManager inputManager;
	protected MouseInputManager mouseInputManager;

	protected GameInput attack;

	protected MouseInput click;
	protected MouseInput click2;
	protected MouseInput click3;
	
	protected long lastAttackTime = 0;

	public InputAdapter(GameCore gameCore, Player player) {

		this.setGameCore(gameCore);
		this.setPlayer(player);

		moveLeft = new GameInput();
		moveRight = new GameInput();
		moveUp = new GameInput();
		moveDown = new GameInput();
		escape = new GameInput();
		run = new GameInput();
		sneak = new GameInput();
		jump = new GameInput();
		pause = new GameInput();
		attack = new GameInput();
		pickUp = new GameInput();
		delete = new GameInput();

		inputManager = new InputManager(gameCore.getScreen()
				.getGamePanel());

		// inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

		inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
		inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(moveRight, KeyEvent.VK_D);
		inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
		inputManager.mapToKey(moveUp, KeyEvent.VK_W);
		inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
		inputManager.mapToKey(moveDown, KeyEvent.VK_S);
		inputManager.mapToKey(escape, KeyEvent.VK_ESCAPE);
		inputManager.mapToKey(run, KeyEvent.VK_V);
		inputManager.mapToKey(sneak, KeyEvent.VK_SHIFT);
		inputManager.mapToKey(jump, KeyEvent.VK_C);
		inputManager.mapToKey(pause, KeyEvent.VK_P);
		inputManager.mapToKey(attack, KeyEvent.VK_CONTROL);
		inputManager.mapToKey(pickUp, KeyEvent.VK_SPACE);
		inputManager.mapToKey(delete, KeyEvent.VK_B);

		mouseInputManager = new MouseInputManager(gameCore.getScreen()
				.getGamePanel());
		this.click = new MouseInput();
		this.click2 = new MouseInput();
		this.click3 = new MouseInput();

		mouseInputManager.setLeftButton(click);
		mouseInputManager.setRightButton(click2);
		mouseInputManager.setMiddleButton(click3);
	}

	public void checkInput(long elapsedTime) throws InvalidPositionException, OccupiedPositionException {

		int horizontalDirection = 0;
		int verticalDirection = 0;
		if (moveDown.isPressed()) {
			verticalDirection++;
		}
		if (moveLeft.isPressed()) {
			horizontalDirection--;
		}
		if (moveRight.isPressed()) {
			horizontalDirection++;
		}
		if (moveUp.isPressed()) {
			verticalDirection--; 	
		}
		
		if (!(verticalDirection == 0 && horizontalDirection == 0)) {

			Direction direction = Direction.newDirection(horizontalDirection,
					verticalDirection);
			Action action;
			if (run.isPressed()) {
				action = new RunAction(this.getPlayer(), direction);
			} else if (sneak.isPressed()) {
				action = new SneakAction(this.getPlayer(), direction);
			} else if (jump.isPressed()) {
				action = new JumpAction(this.getPlayer(), direction);
			} else {
				action = new WalkAction(this.getPlayer(), direction);
			}
			ActionPipeline.getInstance().executeAction(action);

		}

		if (escape.isPressed()) {
			this.getGameCore().stop();
		}
		
		if (attack.isPressed()) {
			/*ActionPipeline.getInstance().executeAction(
					new AttackAction(this.getPlayer(), this.getPlayer()
							.getDirection()));*/
			Attribute playerSpeed = GameCore.getInstance().getPlayer().getElementType().
					getCharacteristics().getAttributeNamed("Speed");
			int speedLevel = playerSpeed.getCurrentLevel();
			
			if(System.currentTimeMillis() - lastAttackTime > 3000 / (speedLevel + 1)) {
				//System.out.println("InputAdapter: " + (System.currentTimeMillis() - lastAttackTime));
				lastAttackTime = System.currentTimeMillis();
				
				SFXManager.playSound("hit");
				ActionPipeline.getInstance().executeAction(
					new AttackAction(this.getPlayer(), this.getPlayer()
							.getDirection()));
				
			}
		}
		
		if (pickUp.isPressed()) {
			ActionPipeline.getInstance().executeAction(
				new PickUpAction(this.getPlayer(), this.getPlayer()
					.getDirection()));
		}
		
		if (delete.isPressed()) {
			ActionPipeline.getInstance().executeAction(
					new DeleteAction(this.getPlayer(), this.getPlayer()
							.getDirection()));
		}
		
		if(pause.isPressed()){
			System.out.println("Game is Paused.");
			pause.release();
			MusicThread.getInstance().pause();
			ScreenManager screenManager = GameCore.getInstance().getScreen();
			int width = Configuration.getInstance().getValueAsInt("resolutionWidth");
			int height = Configuration.getInstance().getValueAsInt("resolutionHeight");
			JPanel paused = new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					//ImageManager imageManager = new ImageManager();
					Image pause = ImageManager.getInstance().getValue("paused");
					g.drawImage(pause, 0, 0, null);
				}
			};
			paused.setLayout(new BorderLayout());
			paused.setSize(width, height);
			paused.setBounds(0, 0, width, height);
			paused.setOpaque(true);
			screenManager.getGamePanel().add(paused);
			screenManager.getGamePanel().remove(screenManager.getMainPanel());
			//screenManager.getGamePanel().add(screenManager.getMainPanel());
			screenManager.getGamePanel().repaint();
			//screenManager.getGamePanel().repaint();
			resetInputManager(paused);
			do{
					try {
						Thread.sleep(1);
						//checkInput(elapsedTime);
						GameObjects.getInstance().getTimeKeeper().update();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}while(!pause.isPressed());
			screenManager.getGamePanel().remove(paused);
			screenManager.getGamePanel().add(screenManager.getMainPanel());
			screenManager.getGamePanel().repaint();
			gameCore.getInputAdapter().resetInputManager(screenManager.getRenderingSurface());
			System.out.println("Game is UnPaused");
			MusicThread.getInstance().unpause();
			pause.release();

		}
		Point point = new Point();
		if (this.click.checkPressed()&&!point.equals(this.click.getPoint())) {
			point = this.click.getPoint();
			Position position = new ScreenPointToPositionTranslator()
					.translate(GameCore.getInstance().getPlayer(), point);
			//System.out.println("Click at " + point);
		} else if (this.click2.checkPressed()&&!point.equals(this.click2.getPoint())) {
			point = this.click2.getPoint();
			Position position = new ScreenPointToPositionTranslator()
					.translate(GameCore.getInstance().getPlayer(), point);
			//System.out.println("Click2 at " + position);
		} else if (this.click3.checkPressed()&&!point.equals(this.click3.getPoint())) {
			point = this.click3.getPoint();
			Position position = new ScreenPointToPositionTranslator()
					.translate(GameCore.getInstance().getPlayer(), point);
//			/System.out.println("Click3 at " + position);
		}
	}
	
	public boolean checkMenuInput() {
		if (this.click.checkPressed()) {
			Point point = this.click.getPoint();
			//System.out.println("Click at "+point);
			if(point.x>=350&&point.x<=544&&point.y>=395&&point.y<=472)
				return true;
		} 
		return false;
	}
	public int checkEndInput() {
		if (this.click.checkPressed()) {
			Point point = this.click.getPoint();
			//System.out.println("Click at "+point);
			if(point.x>=177&&point.x<=368&&point.y>=424&&point.y<=502)
				return 1;
			else if(point.x>=425&&point.x<=610&&point.y>=424&&point.y<=502)
				return 2;
			else
				return 0;
		} 
		return 0;
	}
	private GameCore getGameCore() {
		return gameCore;
	}

	private void setGameCore(GameCore gameCore) {
		this.gameCore = gameCore;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void resetInputManager(Component c) {
		this.setGameCore(gameCore);
		this.setPlayer(player);

		moveLeft = new GameInput();
		moveRight = new GameInput();
		moveUp = new GameInput();
		moveDown = new GameInput();
		escape = new GameInput();
		run = new GameInput();
		sneak = new GameInput();
		jump = new GameInput();
		pause = new GameInput();
		attack = new GameInput();
		pickUp = new GameInput();
		delete = new GameInput();

		inputManager = new InputManager(c);

		// inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

		inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
		inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(moveRight, KeyEvent.VK_D);
		inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
		inputManager.mapToKey(moveUp, KeyEvent.VK_W);
		inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
		inputManager.mapToKey(moveDown, KeyEvent.VK_S);
		inputManager.mapToKey(escape, KeyEvent.VK_ESCAPE);
		inputManager.mapToKey(run, KeyEvent.VK_V);
		inputManager.mapToKey(sneak, KeyEvent.VK_SHIFT);
		inputManager.mapToKey(jump, KeyEvent.VK_C);
		inputManager.mapToKey(pause, KeyEvent.VK_P);
		inputManager.mapToKey(attack, KeyEvent.VK_CONTROL);
		inputManager.mapToKey(pickUp, KeyEvent.VK_SPACE);
		inputManager.mapToKey(delete, KeyEvent.VK_B);

		mouseInputManager = new MouseInputManager(c);
		this.click = new MouseInput();
		this.click2 = new MouseInput();
		this.click3 = new MouseInput();

		mouseInputManager.setLeftButton(click);
		mouseInputManager.setRightButton(click2);
		mouseInputManager.setMiddleButton(click3);
	}

}
