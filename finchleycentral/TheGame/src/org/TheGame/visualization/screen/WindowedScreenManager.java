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

package org.TheGame.visualization.screen;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.ImageManager;

/**
 * @author Facundo Manuel Quiroga Nov 16, 2008
 */

public class WindowedScreenManager extends ScreenManager {

	protected static WindowedScreenManager instance = null;

	/**
	 * @return
	 */
	public static WindowedScreenManager getInstance() {
		if (WindowedScreenManager.instance == null) {
			WindowedScreenManager.instance = new WindowedScreenManager();
		}
		return WindowedScreenManager.instance;
	}
	public static WindowedScreenManager newInstance() {
		WindowedScreenManager.instance = new WindowedScreenManager();

		return WindowedScreenManager.instance;
	}

	protected GraphicsEnvironment environment;

	protected GraphicsDevice device;

	protected GraphicsConfiguration configuration;

	protected DisplayMode currentDisplayMode;

	protected DisplayMode originalDisplayMode;

	protected Canvas drawingSurface;
	
	protected JPanel gamePanel;

	protected JPanel sidePanel;

	protected JFrame frame;

	protected JPanel mainPanel;
	
	protected JPanel tabbedPanel;
	
	protected JTextPane textPanel;
	
	protected JPanel mainMenu;
	
	protected JPanel loading;

	protected WindowedScreenManager() {
		super();
		this.environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.device = environment.getDefaultScreenDevice();
		this.configuration = device.getDefaultConfiguration();
		this.currentDisplayMode = device.getDisplayMode();
		this.originalDisplayMode = device.getDisplayMode();

		this.device.getFullScreenWindow();
		
		this.frame = new JFrame("TheGame");
		
		int width = Configuration.getInstance().getValueAsInt("resolutionWidth");
		int height = Configuration.getInstance().getValueAsInt("resolutionHeight");
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setSize(width, height);
		gamePanel.setBounds(0, 0, width, height);
		gamePanel.setOpaque(true);
		frame.setContentPane(gamePanel);
		
		loading = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				//ImageManager imageManager = new ImageManager();
				Image loading = ImageManager.getInstance().getValue("loading");
				g.drawImage(loading, 0, 0, null);
			}
		};
		loading.setLayout(new BorderLayout());
		loading.setSize(width, height);
		loading.setBounds(0, 0, width, height);
		loading.setOpaque(true);
		gamePanel.add(loading);
		
		mainMenu = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				//ImageManager imageManager = new ImageManager();
				Image menu = ImageManager.getInstance().getValue("menu");
				Image button = ImageManager.getInstance().getValue("button");
				g.drawImage(menu, 0, 0, null);
				g.drawImage(button, 350, 384, null);
			}
		};
		mainMenu.setLayout(new BorderLayout());
		mainMenu.setSize(width, height);
		mainMenu.setBounds(0, 0, width, height);
		mainMenu.setBackground(Color.black);
		mainMenu.setOpaque(true);
		
		
		mainPanel = new JPanel();

		mainPanel.setLayout(null);

		this.drawingSurface = new Canvas();
		drawingSurface.setIgnoreRepaint(true);
		drawingSurface.setBounds(new Rectangle(height, (int)(height*.8)));
		//drawingSurface.setBounds(new Rectangle(768, 768));
		drawingSurface.setSize(height, (int)(height*.8));
		//drawingSurface.setSize(768, 768);
		mainPanel.add(this.drawingSurface);
		mainPanel.setBackground(Color.BLACK);
		
//		/this.drawingSurface.createBufferStrategy(3);
		sidePanel = new JPanel();
		
		//sidePanel.setSize(1024 - 768, 768);
		sidePanel.setSize((width - height), height/2);
		sidePanel.setBackground(Color.gray);
		//sidePanel.setBounds(768, 0, 1024 - 768, 768);

		sidePanel.setBounds(height, 0, (width - height), height/2);
		
//		/Canvas drawingSurface2 = new Canvas();
		//drawingSurface2.setIgnoreRepaint(true);
		//drawingSurface2.setBound(new REctangle)
		
		
		
		textPanel = new JTextPane();
		//textPanel.setSize(height, (int)(height*.2));
		textPanel.setBackground(Color.LIGHT_GRAY);
		textPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		//textPanel.setBounds(0, (int)(height*.8), height, width);
		textPanel.setAutoscrolls(false);
		
		JScrollPane slider = new JScrollPane(textPanel);
		slider.setSize(height, (int)(height*.165));
		slider.setBackground(Color.LIGHT_GRAY);
		slider.setBounds(0, (int)(height*.8), height, (int)(height*.165));
		slider.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPanel = new JPanel();
		tabbedPanel.setSize((width-height), height/2);
		tabbedPanel.setBackground(Color.gray);
		tabbedPanel.setBounds(height, height/2, (width-height), height);
		mainPanel.add(sidePanel);
		mainPanel.add(tabbedPanel);
		mainPanel.add(slider);
		frame.setVisible(true);
	}

	@Override
	public DisplayMode getCurrentDisplayMode() {
		return this.currentDisplayMode;
	}

	@Override
	public Dimension getDimension() {
		int width = this.currentDisplayMode.getWidth();
		int height = this.currentDisplayMode.getHeight();
		return new Dimension(width, height);
	}

	
	@Override
	public Graphics2D getGraphics() {
		/*
		 * if (mainPanel != null) { BufferStrategy strategy =
		 * mainPanel.getBufferStrategy(); return (Graphics2D)
		 * strategy.getDrawGraphics(); } else { return null; }
		 */
		// return (Graphics2D) mainPanel.getGraphics();
 
		try {
			this.drawingSurface.getBufferStrategy().getDrawGraphics();
		} catch (Exception e) {
			this.drawingSurface.createBufferStrategy(3);
			this.drawingSurface.getBufferStrategy().getDrawGraphics();
		} 
		
		return (Graphics2D) this.drawingSurface.getBufferStrategy().getDrawGraphics();
		
		

	}

	@Override
	public Component getRenderingSurface() {
		return this.drawingSurface;
	}

	@Override
	public void restoreScreen() {
		// device.setDisplayMode(originalDisplayMode);

	}

	@Override
	public void setDimension(Dimension d) {
		// this.device.setDisplayMode(new DisplayMode(d.width,d.height,32,60));
		this.frame.setBounds(new Rectangle(d));
	}

	@Override
	public void update() {
		if (drawingSurface != null) {
			BufferStrategy strategy = drawingSurface.getBufferStrategy();
			if (!strategy.contentsLost()) {
				strategy.show();
			}
		}

	}
	
	@Override
	public JFrame getJFrame(){
		return frame;
	}
	
	public JPanel getSidePanel() {
		return this.sidePanel;
	}
	
	public JPanel getMainPanel() {
		return this.mainPanel;
	}

	public JPanel getTabbedPanel() {
		return this.tabbedPanel;
	}
	
	public JPanel getGamePanel() {
		return this.gamePanel;
	}
	
	public JPanel getMainMenu() {
		return this.mainMenu;
	}
	
	public JPanel getLoading() {
		return this.loading;
	}
	
	public JTextPane getTextPanel() {
		return this.textPanel;
	}
}
