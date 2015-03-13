package controller;

import java.util.HashMap;
import java.awt.event.*;

final public class GamePlayState extends ControllerState {
	HashMap<Integer, Command> keyHashMapInt = new HashMap<Integer, Command>();
	HashMap<Character, Command> keyHashMapChar = new HashMap<Character, Command>();

	/**
	 * Constructor
	 * 
	 * populates the keyHashMap and sets up key listeners
	 * 
	 * @param c
	 *            the controller
	 */
	public GamePlayState(Controller c) {
		setOwner(c);
		init();

		// populate keyHashMap
		// up keys
		// numpad 8
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD8), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTH);
			}
		});
		// keyboard W
		keyHashMapInt.put(new Integer(KeyEvent.VK_W), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTH);
			}
		});

		// down keys
		// numpad 2
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD2), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTH);
			}
		});
		// keyboard X
		keyHashMapInt.put(new Integer(KeyEvent.VK_S), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTH);
			}
		});

		// left keys
		// numpad 4
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD4), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.WEST);
			}
		});
		// keyboard D
		keyHashMapInt.put(new Integer(KeyEvent.VK_A), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.WEST);
			}
		});

		// right keys
		// numpad 6
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD6), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.EAST);
			}
		});
		// keyboard D
		keyHashMapInt.put(new Integer(KeyEvent.VK_D), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.EAST);
			}
		});

		// up-left keys
		// numpad 7
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD7), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTHWEST);
			}
		});
		// keyboard Q
		keyHashMapInt.put(new Integer(KeyEvent.VK_Q), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTHWEST);
			}
		});

		// up-right keys
		// numpad 9
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD9), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTHEAST);
			}
		});
		// keyboard E
		keyHashMapInt.put(new Integer(KeyEvent.VK_E), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.NORTHEAST);
			}
		});

		// down-left keys
		// numpad 1
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD1), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTHWEST);
			}
		});
		// keyboard Z
		keyHashMapInt.put(new Integer(KeyEvent.VK_Z), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTHWEST);
			}
		});

		// down-right keys
		// numpad 3
		keyHashMapInt.put(new Integer(KeyEvent.VK_NUMPAD3), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTHEAST);
			}
		});
		// keyboard C
		keyHashMapInt.put(new Integer(KeyEvent.VK_X), new Command() {
			public void execute() {
				getOwner().getModel().moveAvatar(model.Vector.SOUTHEAST);
			}
		});

		// spacebar
		keyHashMapInt.put(new Integer(KeyEvent.VK_SPACE), new Command() {
			public void execute() {
				stateTransition(new PauseMenuState(getOwner()));
			}
		});

		// 0 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_0), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(0);
			}
		});

		// 1 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_1), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(1);
			}
		});

		// 2 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_2), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(2);
			}
		});

		// 3 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_3), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(3);
			}
		});

		// 4 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_4), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(4);
			}
		});

		// 5 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_5), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(5);
			}
		});

		// 6 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_6), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(6);
			}
		});

		// 7 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_7), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(7);
			}
		});

		// 8 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_8), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(8);
			}
		});

		// 9 button
		keyHashMapInt.put(new Integer(KeyEvent.VK_9), new Command() {
			public void execute() {
				getOwner().getModel().useAvatarItem(9);
			}
		});

		// unequip weapon with U
		keyHashMapInt.put(new Integer(KeyEvent.VK_U), new Command() {
			public void execute() {
				getOwner().getModel().unequipAvatarWeapon();
			}
		});

		// unequip armor with I
		keyHashMapInt.put(new Integer(KeyEvent.VK_I), new Command() {
			public void execute() {
				getOwner().getModel().unequipAvatarArmor();
			}
		});

		// drop item by pressing shift and the space in the inventory
		// shift+1
		keyHashMapChar.put(new Character('!'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(1);
			}
		});

		// shift+2
		keyHashMapChar.put(new Character('@'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(2);
			}
		});

		// shift+3
		keyHashMapChar.put(new Character('#'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(3);
			}
		});

		// shift+4
		keyHashMapChar.put(new Character('$'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(4);
			}
		});

		// shift+5
		keyHashMapChar.put(new Character('%'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(5);
			}
		});

		// shift+6
		keyHashMapChar.put(new Character('^'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(6);
			}
		});

		// shift+7
		keyHashMapChar.put(new Character('&'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(7);
			}
		});

		// shift+8
		keyHashMapChar.put(new Character('('), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(9);
			}
		});

		// shift+0
		keyHashMapChar.put(new Character(')'), new Command() {
			public void execute() {
				getOwner().getModel().dropAvatarItem(0);
				System.out.println("test drop");
			}
		});
	}

	/**
	 * initializes the KeyListener
	 */
	public void init() {
		KeyListener kl = new KeyListener() {
			boolean shiftPressed = false;

			public void keyTyped(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 16) {
					shiftPressed = true;
				}
			}

			public void keyReleased(KeyEvent e) {
				Command c = keyHashMapInt.get(e.getKeyCode());
				if (c != null && !shiftPressed)
					c.execute();
				else if (shiftPressed) {
					c = keyHashMapChar.get(e.getKeyChar());
					if (c != null)
						c.execute();
				}
				if (e.getKeyCode() == 16) {
					shiftPressed = false;
				}
			}
		};
		getOwner().getView().initGameplay().addInputHandler(kl);
	}

	/**
	 * if the avatar has no lives left, game is over, transition to the main
	 * menu
	 */
	void update() {
		if (!getOwner().getModel().avatarHasLivesLeft())
			stateTransition(new MainMenuState(getOwner()));
	}
}