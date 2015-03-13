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

package org.TheGame.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.TheGame.events.eventgeneration.CreepSpawner;
import org.TheGame.exceptions.GameError;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.input.ai.AIManager;
import org.TheGame.input.human.InputAdapter;
import org.TheGame.main.logic.FPSCounter;
import org.TheGame.main.logic.RandomNumberGenerator;
import org.TheGame.main.logic.TimeKeeper;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.ImageManager;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.main.resourcemanagement.MapManager;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.elements.staticElements.InventoryBagStaticElement;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.items.Item;
import org.TheGame.model.items.ItemManager;
import org.TheGame.model.map.LimboTileMap;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.sound.MusicThread;
import org.TheGame.sound.SFXManager;
import org.TheGame.visualization.gui.SidePanelGUI;
import org.TheGame.visualization.rendering.TileMapRenderer;
import org.TheGame.visualization.screen.ScreenManager;

/**
 * @author Facundo Quiroga Creation date: 2/2/2008
 */
public class GameCore {

	protected static GameCore instance;

	public static GameCore getInstance() {
		if (GameCore.instance == null) {
			GameCore.instance = new GameCore();
		}
		return GameCore.instance;
	}
	public static GameCore reCreatInstance() {	
		GameCore.instance = new GameCore();
		return GameCore.instance;
	}
	private boolean isRunning;

	protected ScreenManager screenManager;

	protected TileMap mainMap;
	protected CreepSpawner mainMapCreepSpawner;
	protected Player player;

	protected TileMapRenderer tileMapRenderer;

	protected InputAdapter inputAdapter;

	protected SidePanelGUI sidePanelGUI;

	/**
	 * GameObjects
	 */

	protected ProfilingStats profilingStats;
	protected TimeKeeper timeKeeper;
	protected RandomNumberGenerator randomNumberGenerator;
	protected AIManager aiManager;
	protected FPSCounter fpsCounter;
	protected LimboTileMap limboTileMap;
	protected MusicThread musicThread;
	
	protected Queue<InventoryBagStaticElement> bags;
	//protected HashMap<InventoryBagStaticElement, >
	
	protected boolean exited = false;
	
	private GameCore() {
		super();
	}

	public void run() {
		try {
			do{
				init();
				//tileMapRenderer.draw(screenManager, player, player);
				/*int width = Configuration.getInstance().getValueAsInt("resolutionWidth");
				int height = Configuration.getInstance().getValueAsInt("resolutionHeight");
				Component c = screenManager.getRenderingSurface();
				c.setBounds(new Rectangle(width, height));
				c.setSize(width, height);
				screenManager.getMainPanel().add(c);*/
				gameLoop();
				//System.out.println("HERE");
				JPanel death = new JPanel() {
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						//ImageManager imageManager = new ImageManager();
						Image die = ImageManager.getInstance().getValue("die");
						g.drawImage(die, 0, 0, null);
					}
				};
				int width = Configuration.getInstance().getValueAsInt("resolutionWidth");
				int height = Configuration.getInstance().getValueAsInt("resolutionHeight");
				death.setLayout(new BorderLayout());
				death.setSize(width, height);
				death.setBounds(0, 0, width, height);
				death.setOpaque(true);
				screenManager.getGamePanel().remove(screenManager.getMainPanel());
				screenManager.getGamePanel().add(death);
				screenManager.getGamePanel().repaint();
				inputAdapter.resetInputManager(death);
				//ScreenManager screenManager = getScreen();
				//Graphics2D g = screenManager.getGraphics();
				//Image die = ImageManager.getInstance().getValue("die");
				///g.drawImage(die, 0, 0, null);
				//screenManager.update();
				int checkEndInput = 0;
				while(checkEndInput == 0) {
					try {
						Thread.sleep(1);
						timeKeeper.update();
						checkEndInput = inputAdapter.checkEndInput();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//screenManager.getGamePanel().remove(death);
				//screenManager.getGamePanel().add(screenManager.getMainPanel());
				//screenManager.getGamePanel().repaint();
				//inputAdapter.resetInputManager(screenManager.getRenderingSurface());
//				/g.dispose();
				if(checkEndInput == 2)
					exited = true;
				MusicThread.getInstance().exit();
				getScreen().getJFrame().setEnabled(false);
				getScreen().getJFrame().setVisible(false);
				getScreen().getJFrame().dispose();
			}while(!exited);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//System.out.println(screenManager);
			screenManager.restoreScreen();
			lazilyExit();
		}
	}

	/**
	 * Exits the VM from a daemon thread. The daemon thread waits 2 seconds then
	 * calls System.exit(0). Since the VM should exit when only daemon threads
	 * are running, this makes sure System.exit(0) is only called if necessary.
	 * It's necessary if the Java Sound system is running.
	 */
	public void lazilyExit() {
		Thread thread = new Thread() {
			public void run() {
				// wait it out
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex) {

				}
				// if it has not exited by itself, exit
				System.exit(0);
			}
		};
		thread.setDaemon(true);
		thread.start();
		
	}
	
	public void instantExit() {
		MusicThread.getInstance().exit();
		System.exit(0);
	}

	public void init() {
		GameInitializer gameInitializer = new GameInitializer();
		// screenManager = gameInitializer.initializeFullScreen();
		screenManager = gameInitializer.initializeWindowedScreen();
		mainMap = gameInitializer.createExampleMap();
		//this.mainMapCreepSpawner = new CreepSpawner(mainMap);
		GameEntities.getInstance().addMap(mainMap);
		//Creep creep;

		try {
			//Thread.sleep(1000);
			if(player!=null){
				player.die();
			}
			player = gameInitializer.createPlayer(mainMap, Position
					.newPosition(2, 2));
			GameEntities.getInstance().addElement(player);
			/*for (int i = 0; i < 7; i++) {
				creep = gameInitializer.createGuardingCreep(mainMap, Position
						.newPosition(8, 40 + i));
				GameEntities.getInstance().addElement(creep);
			}
			//GameEntities.getInstance().addElement(player);
			for (int i = 0; i < 7; i++) {
				creep = gameInitializer.createGuardingCreep(mainMap, Position
						.newPosition(9, 40 + i));
				GameEntities.getInstance().addElement(creep);
			}
			for (int i = 0; i < 7; i++) {
				creep = gameInitializer.createEscapingCreep(mainMap, Position
						.newPosition(30, 40 + i));
				GameEntities.getInstance().addElement(creep);
			}
			for (int i = 0; i < 7; i++) {
				creep = gameInitializer.createEscapingCreep(mainMap, Position
						.newPosition(31, 40 + i));
				GameEntities.getInstance().addElement(creep);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		tileMapRenderer = new TileMapRenderer();
		inputAdapter = new InputAdapter(this, player);
		isRunning = true;
		
		profilingStats = new ProfilingStats(false);
		GameObjects gameObjects = GameObjects.getInstance();
		timeKeeper = gameObjects.getTimeKeeper();
		randomNumberGenerator = gameObjects.getRandomNumberGenerator();
		fpsCounter = gameObjects.getFPSCounter();
		aiManager = gameObjects.getAIManager();
		limboTileMap = gameObjects.getLimboTileMap();
		bags = new PriorityQueue<InventoryBagStaticElement>();

		JPanel sidePanel = screenManager.getSidePanel();
		JPanel mainPanel = screenManager.getMainPanel();
		JPanel tabbedPanel = screenManager.getTabbedPanel();
		JTextPane textPanel = screenManager.getTextPanel();
		this.sidePanelGUI = new SidePanelGUI(screenManager.getGamePanel(), sidePanel, tabbedPanel, textPanel, player);
		
		//create a new music thread and set the music
		//to the music tied to the first map
		musicThread = MusicThread.getInstance();
		musicThread.setMusic("midi2.mid");
		/*ImageManager imageManager = new ImageManager();
		Image menu = imageManager.getValue("menu");
		Image button = imageManager.getValue("button");
		Graphics2D g = screenManager.getGraphics();
		g.drawImage(menu, 0, 0, null);
		g.drawImage(button, 350, 384, null);
		g.dispose();
		screenManager.update();
		boolean checkMenuInput = false;
		while(!checkMenuInput) {
			try {
				Thread.sleep(1);
				timeKeeper.update();
				checkMenuInput = inputAdapter.checkMenuInput();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		screenManager.getGamePanel().remove(screenManager.getLoading());
		screenManager.getGamePanel().add(screenManager.getMainMenu());
		screenManager.getGamePanel().repaint();
		//System.out.println("HERE");
		musicThread = MusicThread.getInstance();
		musicThread.setMusic("midi2.mid");
		musicThread.setMusic(mainMap.getMusic());
		boolean checkMenuInput = false;
		while(!checkMenuInput) {
			try {
				Thread.sleep(1);
				timeKeeper.update();
				checkMenuInput = inputAdapter.checkMenuInput();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		screenManager.getGamePanel().remove(screenManager.getMainMenu());
		screenManager.getGamePanel().add(screenManager.getMainPanel());
		screenManager.getGamePanel().repaint();
		inputAdapter.resetInputManager(screenManager.getRenderingSurface());
		this.getSidePanelGUI().addText("Welcome to TheGame!\n" + 
				"Use WASD to control the player\n" + 
				"Press Ctrl to attack\n" + 
				"Press Space to pick up items\n" + 
				"Press B to delete items\n");
		
		
		/*if(Configuration.getInstance().getValueAsBoolean("cheatMode")) {	
			//Item crown = GameFactories.getInstance().getItemFactory().CreateItem("crown");
			Item silverKey = GameFactories.getInstance().getItemFactory().CreateItem("silverkey");
			Item goldKey = GameFactories.getInstance().getItemFactory().CreateItem("goldkey");
			Item bossKey = GameFactories.getInstance().getItemFactory().CreateItem("bosskey");
			Inventory inventory = InventoryManager.getInstance().getPlayerInventory("normal");
	
			//crown.pickUp();
			//ItemManager.getInstance().addItemIntoPlay(crown);
			silverKey.pickUp();
			ItemManager.getInstance().addItemIntoPlay(silverKey);
			goldKey.pickUp();
			ItemManager.getInstance().addItemIntoPlay(goldKey);
			bossKey.pickUp();
			ItemManager.getInstance().addItemIntoPlay(bossKey);
		}*/
	}
	
	public void setMap(TileMap tileMap, Position position) {
		Player player = this.getPlayer();
		TileMap firstMap = getPlayer().getCurrentTileMap();
		Collection<Element> elements = GameEntities.getInstance().getElements();
		/*
		for(Element element : elements)
			if(!element.equals(player)) {
				element.setAttacker(null);
				element.die();
			}
		*/
		try {
			if(firstMap.getElements().contains(player))
			{
				GameEntities.getInstance().getMaps().remove(firstMap);
				GameEntities.getInstance().getCreepSpawners().clear();
				GameEntities.getInstance().getCreepSpawners().addAll(
						MapManager.getInstance().getCreepSpawners(tileMap));
				MapTeleporter.getInstance().teleportTo(player, tileMap, position);
				//change the music to the new map's music
				MusicThread.getInstance().setMusic(tileMap.getMusic());
				
			}
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//mainMapCreepSpawner = new CreepSpawner(tileMap);
	}
	
	public void gameLoop() throws InvalidPositionException, OccupiedPositionException {
		/**
		 * Updates the state of the game/animation based on the amount of
		 * elapsed time that has passed.
		 */
		
		long baseTime = System.currentTimeMillis();
		while (this.isRunning()) {
			
			//if(!done && System.currentTimeMillis() - baseTime2 > 10000) {this.setMap("test2"); done = true;}
			profilingStats.startOfLoop();

			timeKeeper.update();
			profilingStats.finishedSystem("TimeKeeper");

			long elapsedTime = GameObjects.getInstance().getTimeKeeper()
					.getElapsedTimeSinceLastUpdate();
			// update
			
			baseTime = System.currentTimeMillis();
			while(!bags.isEmpty()) {
				InventoryBagStaticElement bag = bags.peek();
				if(System.currentTimeMillis()-bag.creationTime() >= 15000) {
					bags.remove(bag);
					bag.getMap().removeElement(bag);
					ItemManager.getInstance().removeItemFromPlay(bag.getItem());
				}
				else break;
			}

			randomNumberGenerator.randomize();
			profilingStats.finishedSystem("RandomNumberGenerator");

			GameEntities.getInstance().updateElements(elapsedTime);
			GameEntities.getInstance().updateCreepSpawners(elapsedTime);
			profilingStats.finishedSystem("UpdateElements");

			inputAdapter.checkInput(elapsedTime);
			profilingStats.finishedSystem("InputAdapter");

			aiManager.think();
			profilingStats.finishedSystem("AI");

			
			// ActionPipeline.getInstance().processQueue();

			//this.mainMapCreepSpawner.update(elapsedTime);
			profilingStats.finishedSystem("CreepSpawner");

			tileMapRenderer.draw(screenManager, player, player);
			
			profilingStats.finishedSystem("Render");

			if (this.player.getHealthState().isDead()) {
				SFXManager.playSound("death");
				this.stop();
			}
			if (limboTileMap.getElements().size() > 0) {
				throw new GameError(
						"there should not be any elements in the LimboMap");
			}
			fpsCounter.count();
			this.sidePanelGUI.update();
			profilingStats.finishedSystem("FPS and SidePanel");

			while(System.currentTimeMillis() - baseTime < 20) {
				try{
					long nextTime = 20 - System.currentTimeMillis() + baseTime;
					if(nextTime>=0)
						Thread.sleep(nextTime);
				} catch(InterruptedException e) {
					
				}
			}
			profilingStats.endOfLoop();
		}
	}

	private boolean isRunning() {
		return this.isRunning;
	}

	public void stop() {
		isRunning = false;
	}

	public ScreenManager getScreen() {
		return this.screenManager;
	}

	public Player getPlayer() {
		return this.player;
	}
	public TimeKeeper getTimeKeeper() {
		return timeKeeper;
	}
	public InputAdapter getInputAdapter() {
		return inputAdapter;
	}
	
	public SidePanelGUI getSidePanelGUI() {
		return sidePanelGUI;
	}
	
	public void removeBag(InventoryBagStaticElement e) {
		bags.remove(e);
	}
	
	public void addBag(InventoryBagStaticElement e) {
		bags.add(e);
	}
}
