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

package org.TheGame.visualization.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.TheGame.model.elements.Element;
import org.TheGame.model.items.*;
import org.TheGame.main.GameCore;
import org.TheGame.main.GameObjects;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.ImageManager;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.elements.movableElements.statistics.Statistic;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.sound.MusicThread;
import org.TheGame.visualization.gui.panels.labelbased.AttributesPanel;
import org.TheGame.visualization.gui.panels.labelbased.SkillsPanel;
import org.TheGame.visualization.gui.panels.labelbased.StatisticsPanel;
import org.TheGame.visualization.gui.panels.listbased.AWTAttributesPanel;
import org.TheGame.visualization.gui.panels.listbased.AWTSkillsPanel;

/**
 * @author Facundo Manuel Quiroga Nov 18, 2008
 */
public class SidePanelGUI {
	protected final String fpsTitle = "fps: ";
	protected final String directionTitle = "direction: ";
	protected final String positionTitle = "position: ";

	protected JPanel mainPanel;
	protected JPanel tabbedPanel;
	protected JPanel sidePanel;
	protected JLabel debuggers;
	protected JLabel fps;
	protected JLabel direction;
	//protected JLabel position;
	protected AttributesPanel attributesPanel;
	protected SkillsPanel skillsPanel;
	protected StatisticsPanel statisticsPanel;
	
	protected Inventory inventory;
	protected ImageManager imageManager;

	//protected AWTAttributesPanel awtAttributesPanel;
	//protected AWTSkillsPanel awtSkillsPanel;

	protected MovableElement player;

	static int defaultHeight = 20;
	static int defaultWidth = 150;
	
	private StyledDocument document;
	private SimpleAttributeSet left;

	public SidePanelGUI(final JPanel mainPanel, final JPanel sidePanel2, final JPanel tabbedPanel, final JTextPane textPanel, MovableElement player) {
		//imageManager = new ImageManager();
		sidePanel2.setLayout(new BoxLayout(sidePanel2, BoxLayout.Y_AXIS));
		this.mainPanel = mainPanel;
		int currentY = 0;
		this.player = player;
		this.setSidePanel(sidePanel2);
		this.debuggers = this.addLabel("<html>"+this.fpsTitle+"<br>"+this.positionTitle+"<br>"+this.directionTitle+"<br></html>", 0, currentY, defaultWidth,
				defaultHeight);
		currentY += defaultHeight;
		sidePanel2.add(new JLabel("<html><br></html>"));
	
		// AttributesPanel
		currentY += 10;
		attributesPanel = new AttributesPanel(player);
		attributesPanel.setBounds(0, currentY, AttributesPanel.defaultWidth,
				attributesPanel.getUsedHeight());
		sidePanel2.add(attributesPanel);
		currentY += attributesPanel.getUsedHeight();
		sidePanel2.add(new JLabel("<html><br></html>"));

		// SkillsPanel
		currentY += 10;
		skillsPanel = new SkillsPanel(player);
		skillsPanel.setBounds(0, currentY, SkillsPanel.defaultWidth,
				skillsPanel.getUsedHeight());
		sidePanel2.add(skillsPanel);
		currentY += skillsPanel.getUsedHeight();
		sidePanel2.add(new JLabel("<html><br></html>"));
		
		
		// StatisticsPanel
		currentY += 10;
		statisticsPanel = new StatisticsPanel("", player);
		statisticsPanel.setBounds(0, currentY, StatisticsPanel.defaultWidth,
				statisticsPanel.getUsedHeight());
		int height = Configuration.getInstance().getValueAsInt("resolutionHeight");

		JButton exitButton = new JButton("Exit");
		JTabbedPane panes = new JTabbedPane();
		JPanel c = new JPanel();
		JLabel music = new JLabel("Disable music: ");
    	JCheckBox mute = new JCheckBox();
    	boolean muteState = false;
    	mute.setSelected(muteState);
    	mute.addActionListener(
			new ActionListener() {
				boolean muteState = false;
				public void actionPerformed(ActionEvent e) {
					MusicThread.getInstance().switchState();
					muteState = !muteState;
				}
			}
			
		);
		c.add(music);
		c.add(mute);
		c.add(exitButton);
		JTabbedPane inventory = new JTabbedPane();
		JTabbedPane playerPane = new JTabbedPane();
		JPanel inv = new JPanel();
		JPanel quest = new JPanel();
		JPanel playerTab = new JPanel();
		playerTab.setLayout(null);
		//System.out.println(sidePanel.getWidth()+" "+sidePanel.getHeight());
		playerTab.setSize(sidePanel.getWidth(), sidePanel.getHeight());
		playerTab.setBounds(0, 0, sidePanel.getWidth(), sidePanel.getHeight());
		//playerTab.setOpaque(true);
		JLabel leftHand = new JLabel(new ImageIcon(ImageManager.getInstance().getValue("leftHand")));
		leftHand.setSize(50,50);
		leftHand.setBounds(25,150,50,50);
		JLabel rightHand = new JLabel(new ImageIcon(ImageManager.getInstance().getValue("rightHand")));
		rightHand.setSize(50,50);
		rightHand.setBounds(175,150,50,50);
		JLabel torso = new JLabel(new ImageIcon(ImageManager.getInstance().getValue("torso")));
		torso.setSize(50,50);
		torso.setBounds(100,125,50,50);
		JLabel head = new JLabel(new ImageIcon(ImageManager.getInstance().getValue("head")));
		head.setSize(50,50);
		head.setBounds(100,50,50,50);
		JLabel legs = new JLabel(new ImageIcon(ImageManager.getInstance().getValue("legs")));
		legs.setSize(50,50);
		legs.setBounds(100,200,50,50);
		leftHand.setBorder(new LineBorder(Color.BLACK));
		rightHand.setBorder(new LineBorder(Color.BLACK));
		torso.setBorder(new LineBorder(Color.BLACK));
		head.setBorder(new LineBorder(Color.BLACK));
		legs.setBorder(new LineBorder(Color.BLACK));
		playerTab.add(leftHand);
		playerTab.add(rightHand);
		playerTab.add(torso);
		playerTab.add(head);
		playerTab.add(legs);
		images[convertSlot("head")] = new ImageIcon(ImageManager.getInstance().getValue("head"));
		images[convertSlot("torso")] = new ImageIcon(ImageManager.getInstance().getValue("torso"));
		images[convertSlot("leftHand")] = new ImageIcon(ImageManager.getInstance().getValue("leftHand"));
		images[convertSlot("rightHand")] = new ImageIcon(ImageManager.getInstance().getValue("rightHand"));
		images[convertSlot("legs")] = new ImageIcon(ImageManager.getInstance().getValue("legs"));
		equippedMap[convertSlot("head")] = head;
		equippedMap[convertSlot("torso")] = torso;
		equippedMap[convertSlot("leftHand")] = leftHand;
		equippedMap[convertSlot("rightHand")] = rightHand;
		equippedMap[convertSlot("legs")] = legs;
		equippedCheck[convertSlot("head")] = false;
		equippedCheck[convertSlot("torso")] = false;
		equippedCheck[convertSlot("leftHand")] = false;
		equippedCheck[convertSlot("rightHand")] = false;
		equippedCheck[convertSlot("legs")] = false;
		inv.setLayout(new GridLayout(12, 5));
		for(int i = 0; i < 25; ++i) {
			inv.add(new JLabel());
		}
		quest.setLayout(new GridLayout(12, 5));
		for(int i = 0; i < 25; ++i) {
			quest.add(new JLabel());
		}
		panes.addTab("Options", c);
		panes.addTab("Inventory", inventory);
		panes.addTab("Player", playerPane);
		inventory.addTab("Regular", inv);
		inventory.addTab("Quest", quest);
		playerPane.addTab("Equipment", playerTab);
		playerPane.addTab("Statistics", statisticsPanel);
		exitButton.addActionListener(
				new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    GameCore.getInstance().instantExit();
	                }
	            }
		);
		tabbedPanel.setLayout(new BoxLayout(tabbedPanel, BoxLayout.Y_AXIS));
		tabbedPanel.add(panes);
		
		textPane = textPanel;
		//int x;
		//textPane.selectAll();
		//x = textPane.getSelectionEnd();
		//textPane.select(x, x);
		//textPane.setAutoscrolls(true);
		document =textPane.getStyledDocument();
		left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left,StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(left, 14);
		document.setParagraphAttributes(0,document.getLength(),left,false);
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setEditable(false);
		
		//textPanel.add(textPane);
		
		
		this.setTabbedPanel(tabbedPanel);
		time=System.currentTimeMillis();
	}

	public JLabel addLabel(String name, int x, int y, int width, int height) {
		JLabel label = new JLabel(name);
		label.setBounds(x, y, width, height);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.getSidePanel().add(label);
		return label;
	}
	
	private Position itemPosition;
	private boolean pickUp; //true = pickUp, false = delete;
	private Item item;
	private TileMap map;
	private Element toRemove;
	ImageIcon[] images = new ImageIcon[5];
	JLabel[] equippedMap = new JLabel[5];
	boolean[] equippedCheck = new boolean[5];	
	final Item[] equippedArray = new Item[5];
	long time;
	private JTextPane textPane;

	public void update() {
		int fps = GameObjects.getInstance().getFPSCounter().getFPS();
		InventoryManager inventoryManager = InventoryManager.getInstance();
		final Inventory normalInventory = inventoryManager.getPlayerInventory("normal");
		final Inventory questInventory = inventoryManager.getPlayerInventory("quest");
		final Inventory equippedInventory = inventoryManager.getPlayerInventory("equipped");
		final ArrayList<Item> normalList = normalInventory.getInventoryList();
		final ArrayList<Item> questList = questInventory.getInventoryList();
		Position position = this.player.getPosition();
		Direction direction = this.player.getDirection();
		this.debuggers.setText("<html>"+this.fpsTitle+fps+"<br>"+this.positionTitle+position+"<br> (debug)Money:"+GameCore.getInstance().getPlayer().getMoney()+"<br>"+this.directionTitle+direction+"<br></html>");
		if(tabbedPanel!=null) {
			JTabbedPane tab = (JTabbedPane)(tabbedPanel.getComponent(0));
			JTabbedPane normal = (JTabbedPane)(tab.getComponentAt(1));
			JPanel inv = (JPanel)(normal.getComponentAt(0));
			JPanel quest = (JPanel)(normal.getComponentAt(1));
			for(int i = 0; i < 15; ++i) {
				final JLabel normalLabel = (JLabel)(inv.getComponent(i));
				if(i>=normalList.size()) {
					normalLabel.setIcon(null);
					normalLabel.setToolTipText(null);
					continue;
				}
				final Item next = normalList.get(i);
				final int num = i;
				//if(System.currentTimeMillis()-time>100) {
				//	time = System.currentTimeMillis();
				//	System.out.println(i);
					normalLabel.addMouseListener(new MouseAdapter() {
						
						boolean done = false;
						
						public void mouseClicked(MouseEvent e){
							if(!done) {
								normalLabel.removeMouseListener(this);
								//System.out.println("here"+next.getName());
								if (e.getButton()==1) {
									if(!next.checked&&num<normalList.size()&&normalList.get(num)==next) {
										next.checked=true;
										done = true;
										if(use(next)) {
											normalList.remove(next);
											equippedArray[convertSlot(next.getSlot())] = next;
										}
									}
						        }
								else if(e.getButton()==3&&num<normalList.size()&&normalList.get(num)==next) {
									if(!next.checked) {
										//next.useItem();
										normalList.remove(next);
										next.checked = true;
										done = true;
										addText("Deleted a "+next.getName()+"!\n");
									}
									next.checked=true;
								}
					        }
							else if(e.getButton()==3) {
								if(!next.checked&&num<normalList.size()&&normalList.get(num)==next) {
									//next.useItem();
									normalList.remove(next);
									addText("Deleted a "+next.getName()+"!\n");
								}
							}
							done = true;
			            } 
					});
				//}
				if(normalList.contains(next)) {
					normalLabel.setToolTipText(next.getToolTip());
					ImageManager im = ImageManager.getInstance();
					String str = next.getGraphic();
					Image imag = im.getValue(str);
					if(imag==null) normalList.remove(i);
					else normalLabel.setIcon(new ImageIcon(imag));
				}
				else {
					normalLabel.setToolTipText(null);
					normalLabel.setIcon(null);
				}
				next.checked=false;
			}
			for(int i = 0; i < 15; ++i) {
				JLabel questLabel = (JLabel)(quest.getComponent(i));
				if(i>=questList.size()) {
					questLabel.setIcon(null);
					continue;
				}
				final Item next = questList.get(i);
				questLabel.setToolTipText(next.getToolTip());
				ImageManager im = ImageManager.getInstance();
				String str = next.getGraphic();
				Image imag = im.getValue(str);
				if(imag==null) questList.remove(i);
				else questLabel.setIcon(new ImageIcon(imag));
			}
			//ArrayList<Statistic> statistic = GameCore.getInstance().getPlayer().getElementType().getCharacteristics().;
			for(int i = 0; i < 5; ++i) {
				final JLabel playerLabel = (JLabel)(equippedMap[i]);
				final Item next = equippedArray[i];
				final int num = i;
				if(next!=null&&equippedMap[num].getIcon()!=images[num]) {
					playerLabel.setToolTipText(next.getToolTip());
					MouseAdapter l = new MouseAdapter() {
						boolean done = false;
						public void mouseClicked(MouseEvent e){
							if (e.getButton()==1&&!done) {
								playerLabel.removeMouseListener(this);
								if(!next.checked) {
									if(normalList.size()<15) {
										if(!normalList.contains(next)) {
											normalList.add(next);
											equippedArray[num]= null;
											next.checked=true;
											equippedMap[num].setIcon(images[num]);
											EquipableItem n = (EquipableItem)next;
											n.unequipItem();
											addText("Unequipped a "+next.getName()+" in the "+next.getSlot()+" slot!\n");
											equippedCheck[num] = false;
											//done = true;
										}
									}
									else {
										next.checked=true;
										addText("Inventory is full!\n");
									}
								}
					        }
							done = true;
			            } 
					};
					playerLabel.addMouseListener(l);
					next.checked = false;
				}
				else {
					playerLabel.setToolTipText(null);
				}
			}
			if(itemPosition!=null) {
				if(pickUp) {
					if(item.pickUp()) {
						try {
							addText("Picked up a "+item.getName()+"!\n");
						} catch (Exception e) {};
						map.removeElement(toRemove);
						itemPosition = null;
					}
				}
				else {
					map.removeElement(toRemove);
					itemPosition = null;
				}
			}
		}
		statisticsPanel.update();
		attributesPanel.update();
		skillsPanel.update();
		tabbedPanel.repaint();
		sidePanel.repaint();
	}
	
	public void setTabbedPanel(JPanel tabbedPanel2) {
		this.tabbedPanel = tabbedPanel2;
	}
	
	public JPanel getTabbedPanel() {
		return this.tabbedPanel;
	}

	public JPanel getSidePanel() {
		return this.sidePanel;
	}

	public void setSidePanel(JPanel sidePanel2) {
		this.sidePanel = sidePanel2;
	}
	
	public void deleteItem(Position position, Item item, TileMap map, Element e) {
		itemPosition = position;
		this.item = item;
		this.map = map;
		toRemove = e;
		pickUp = false;
	}
	
	public void pickUpItem(Position position, Item item, TileMap map, Element e) {
		itemPosition = position;
		this.item = item;
		this.map = map;
		toRemove = e;
		pickUp = true;
	}
	
	int lineCount;
	
	public void addText(String text) {
		try {
			/*char[] textArr = text.toCharArray();
			for(int i = 0; i < textArr.length; ++i) {
				if(textArr[i]=='\n') ++lineCount;
			}
			int remove = -1;
			char[] docText = document.getText(0, document.getLength()).toCharArray();
			for(int j = document.getLength(); j > 0; --j) {
				if(lineCount<=50) {
					remove = j;
					break;
				}
				else if(docText[j]=='\n') {
					--lineCount;
				}
			}
			System.out.println(lineCount);
			document.remove(remove, document.getLength());*/
			document.insertString(document.getLength(), text, left);
			textPane.selectAll();
		} catch (Exception e) {};
	}
	
	public boolean use(Item item) {
		if(item instanceof ConsumableItem) {
			item.useItem();
			addText("Used a "+item.getName()+"!\n");
		}
		else if(item instanceof EquipableItem) {
			int slot = convertSlot(item.getSlot());
			if(slot!=-1) {
				if(item.useItem()) {
					equippedMap[slot].setIcon(new ImageIcon(ImageManager.getInstance().getValue(item.getGraphic())));
					addText("Equipped a "+item.getName()+" in the "+item.getSlot()+" slot!\n");
					equippedCheck[convertSlot(item.getSlot())] = true;
					return true;
				}
				else {
					addText(item.getSlot()+" slot already contains an equipment!\n");
				}
			}
		}
		return false;
	}
	
	public int convertSlot(String slot) {
		if(slot.equals("head")) return 0;
		else if(slot.equals("torso")) return 1;
		else if(slot.equals("leftHand")) return 2;
		else if(slot.equals("rightHand")) return 3;
		else if(slot.equals("legs")) return 4;
		return -1;
	}
}
