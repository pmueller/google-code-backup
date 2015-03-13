package controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Hashtable;
import util.SaverLoader;
import util.Saveable;

public class KeyMap implements Saveable {
    //the hash map that holds the commands and their button mapping

    private Hashtable<Integer, Command> keymap;
    //the list of keycodes
    private Integer[] keycodes;
    //the list of possible keyboard commands
    private Command upCommand;
    private Command downCommand;
    private Command leftCommand;
    private Command rightCommand;
    private Command upleftCommand;
    private Command uprightCommand;
    private Command downleftCommand;
    private Command downrightCommand;
    private Command attackCommand;
    private Command pauseCommand;
    private Command abilityOne;
    private Command abilityTwo;
    private Command abilityThree;
    private Command abilityFour;
    private Command abilityFive;
    private Command abilitySix;
    private Command abilitySeven;
    private Command abilityEight;
    private Command abilityNine;
    private Command abilityZero;
    //-----------------------------\\
    //editor mode commands
    private Command enableEditing;
    private Command edit1;
    private Command edit2;
    private Command edit3;
    private Command edit4;
    private Command edit5;

    /**
     * Constructor: calls initialize
     */
    public KeyMap() {
        initialize();
    }

    /**
     * Initializes the key map and codes
     */
    private void initialize() {
        keymap = new Hashtable<Integer, Command>();
        keycodes = new Integer[20];

        //initialize command pairs
        //up command
        Command upCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.NORTH);
            }
        };
        this.upCommand = upCommand;

        //down command
        Command downCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.SOUTH);
            }
        };
        this.downCommand = downCommand;

        //left command
        Command leftCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.WEST);
            }
        };
        this.leftCommand = leftCommand;

        //right command
        Command rightCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.EAST);
            }
        };
        this.rightCommand = rightCommand;

        //up-left command
        Command upleftCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.NORTHWEST);
            }
        };
        this.upleftCommand = upleftCommand;

        //up-right command
        Command uprightCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.NORTHEAST);
            }
        };
        this.uprightCommand = uprightCommand;

        //down-left command
        Command downleftCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.SOUTHWEST);
            }
        };
        this.downleftCommand = downleftCommand;

        //down-right command
        Command downrightCommand = new Command() {

            public void execute() {
                model.Model.getInstance().moveAvatar(util.Vector.SOUTHEAST);
            }
        };
        this.downrightCommand = downrightCommand;

        //pause command
        Command pauseCommand = new Command() {

            public void execute() {
                Controller.getInstance().setCurrentState(new PauseMenuState());
            }
        };
        this.pauseCommand = pauseCommand;

        //attack command
        Command attackCommand = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(0));
            }
        };
        this.attackCommand = attackCommand;


        //ability 1
        Command abilityOne = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(1));
            }
        };
        this.abilityOne = abilityOne;

        //ability 2
        Command abilityTwo = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(2));
            }
        };
        this.abilityTwo = abilityTwo;

        //ability 3
        Command abilityThree = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(3));
            }
        };
        this.abilityThree = abilityThree;

        //ability 4
        Command abilityFour = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(4));
            }
        };
        this.abilityFour = abilityFour;

        //ability 5
        Command abilityFive = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(5));
            }
        };
        this.abilityFive = abilityFive;

        //ability 6
        Command abilitySix = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(6));
            }
        };
        this.abilitySix = abilitySix;

        //ability 7
        Command abilitySeven = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(7));
            }
        };
        this.abilitySeven = abilitySeven;

        //ability 8
        Command abilityEight = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(8));
            }
        };
        this.abilityEight = abilityEight;

        //ability 9
        Command abilityNine = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(9));
            }
        };
        this.abilityNine = abilityNine;

        //ability 0
        Command abilityZero = new Command() {

            public void execute() {
                model.Model.getInstance().useAvatarAbility(new Integer(10));
            }
        };
        this.abilityZero = abilityZero;
        //-------------------------\\
        //-----editor Commands-----\\
        //\\________________//\\
        enableEditing = new Command() {

            public void execute() {
                model.Model.getInstance().toggleEditor();
            }
        };
        edit1 = new Command() {

            public void execute() {
                model.Model.getInstance().editMap(0);
            }
        };
        edit2 = new Command() {

            public void execute() {
                model.Model.getInstance().editMap(1);
            }
        };
        edit3 = new Command() {

            public void execute() {
                model.Model.getInstance().editMap(2);
            }
        };
        edit4 = new Command() {

            public void execute() {
                model.Model.getInstance().editMap(3);
            }
        };
        edit5 = new Command() {

            public void execute() {
                model.Model.getInstance().editMap(4);
            }
        };


        // populate keyHashMap
        // up keys
        // keyboard W
        keymap.put(new Integer(KeyEvent.VK_W), upCommand);
        keycodes[0] = new Integer(KeyEvent.VK_W);

        // down keys
        // keyboard X
        keymap.put(new Integer(KeyEvent.VK_S), downCommand);
        keycodes[1] = new Integer(KeyEvent.VK_S);

        // left keys
        // keyboard A
        keymap.put(new Integer(KeyEvent.VK_A), leftCommand);
        keycodes[2] = new Integer(KeyEvent.VK_A);

        // right keys
        // keyboard D
        keymap.put(new Integer(KeyEvent.VK_D), rightCommand);
        keycodes[3] = new Integer(KeyEvent.VK_D);

        // up-left keys
        // keyboard Q
        keymap.put(new Integer(KeyEvent.VK_Q), upleftCommand);
        keycodes[4] = new Integer(KeyEvent.VK_Q);

        // up-right keys
        // keyboard E
        keymap.put(new Integer(KeyEvent.VK_E), uprightCommand);
        keycodes[5] = new Integer(KeyEvent.VK_E);

        // down-left keys
        // keyboard Z
        keymap.put(new Integer(KeyEvent.VK_Z), downleftCommand);
        keycodes[6] = new Integer(KeyEvent.VK_Z);

        // down-right keys
        // keyboard C
        keymap.put(new Integer(KeyEvent.VK_X), downrightCommand);
        keycodes[7] = new Integer(KeyEvent.VK_X);

        // press P to pause
        keymap.put(new Integer(KeyEvent.VK_P), pauseCommand);
        keycodes[8] = new Integer(KeyEvent.VK_P);

        // Press spacebar
        keymap.put(new Integer(KeyEvent.VK_SPACE), attackCommand);
        keycodes[9] = new Integer(KeyEvent.VK_SPACE);

        // Press 1 for Ability Slot One
        keymap.put(new Integer(KeyEvent.VK_1), abilityOne);
        keycodes[10] = new Integer(KeyEvent.VK_1);

        // Press 2 for Ability Slot Two
        keymap.put(new Integer(KeyEvent.VK_2), abilityTwo);
        keycodes[11] = new Integer(KeyEvent.VK_2);

        // Press 3 for Ability Slot Three
        keymap.put(new Integer(KeyEvent.VK_3), abilityThree);
        keycodes[12] = new Integer(KeyEvent.VK_3);

        // Press 4 for Ability Slot Four
        keymap.put(new Integer(KeyEvent.VK_4), abilityFour);
        keycodes[13] = new Integer(KeyEvent.VK_4);

        // Press 5 for Ability Slot Five
        keymap.put(new Integer(KeyEvent.VK_5), abilityFive);
        keycodes[14] = new Integer(KeyEvent.VK_5);

        // Press 6 for Ability Slot Six
        keymap.put(new Integer(KeyEvent.VK_6), abilitySix);
        keycodes[15] = new Integer(KeyEvent.VK_6);

        // Press 7 for Ability Slot Seven
        keymap.put(new Integer(KeyEvent.VK_7), abilitySeven);
        keycodes[16] = new Integer(KeyEvent.VK_7);

        // Press 8 for Ability Slot Eight
        keymap.put(new Integer(KeyEvent.VK_8), abilityEight);
        keycodes[17] = new Integer(KeyEvent.VK_8);

        // Press 9 for Ability Slot Nine
        keymap.put(new Integer(KeyEvent.VK_9), abilityNine);
        keycodes[18] = new Integer(KeyEvent.VK_9);

        // Press 0 for Ability Slot Zero
        keymap.put(new Integer(KeyEvent.VK_0), abilityZero);
        keycodes[19] = new Integer(KeyEvent.VK_0);

        // Editor Mode KeyMaps, not saved, always persistent
        keymap.put(new Integer(KeyEvent.VK_SEMICOLON), enableEditing);
        keymap.put(new Integer(KeyEvent.VK_N), edit1);
        keymap.put(new Integer(KeyEvent.VK_M), edit2);
        keymap.put(new Integer(KeyEvent.VK_COMMA), edit3);
        keymap.put(new Integer(KeyEvent.VK_PERIOD), edit4);
        keymap.put(new Integer(KeyEvent.VK_SLASH), edit5);
    }

    /**
     * execute commands from the key mapping
     * @param keycode
     */
    public void executeCommand(Integer keycode) {
        if (keymap.get(keycode) != null) {
            Command c = keymap.get(keycode);

            c.execute();
        }
    }

    /**
     * This method will change the keymap to a new hash table of new control values
     * @param newKeyCodes
     */
    public void setKeyMappings(Integer[] newKeyCodes) {
        //makes a temporary new hashmap newkeymap
        Hashtable<Integer, Command> newkeymap = new Hashtable<Integer, Command>();

        //go through the the passed array in order, and hash commands
        //into newkeymap
        newkeymap.put(newKeyCodes[0], upCommand);
        newkeymap.put(newKeyCodes[1], downCommand);
        newkeymap.put(newKeyCodes[2], leftCommand);
        newkeymap.put(newKeyCodes[3], rightCommand);
        newkeymap.put(newKeyCodes[4], upleftCommand);
        newkeymap.put(newKeyCodes[5], uprightCommand);
        newkeymap.put(newKeyCodes[6], downleftCommand);
        newkeymap.put(newKeyCodes[7], downrightCommand);
        newkeymap.put(newKeyCodes[8], pauseCommand);
        newkeymap.put(newKeyCodes[9], attackCommand);
        newkeymap.put(newKeyCodes[10], abilityOne);
        newkeymap.put(newKeyCodes[11], abilityTwo);
        newkeymap.put(newKeyCodes[12], abilityThree);
        newkeymap.put(newKeyCodes[13], abilityFour);
        newkeymap.put(newKeyCodes[14], abilityFive);
        newkeymap.put(newKeyCodes[15], abilitySix);
        newkeymap.put(newKeyCodes[16], abilitySeven);
        newkeymap.put(newKeyCodes[17], abilityEight);
        newkeymap.put(newKeyCodes[18], abilityNine);
        newkeymap.put(newKeyCodes[19], abilityZero);

        //set the newkeymap as the keymap
        this.keymap = newkeymap;
        this.keycodes = newKeyCodes;
    }

    /**
     * This method is the loader for the keymap
     * @param s
     * @param notSuperClass
     * @throws IOException
     */
    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        int[] temp = s.pullIntArray();
        for (int index = 0; index <= 20; index++) {
            this.keycodes[index] = new Integer(temp[index]);
        }
        s.assertEnd();

        //set the key map using the retrieved keycodes
        setKeyMappings(this.keycodes);
    }

    /**
     * This method saves the key map
     * @param sl
     * @param nSC
     * @throws IOException
     */
    public void save(SaverLoader sl, boolean nSC) throws IOException {
        //start the saver operation
        if (nSC) {
            sl.start("KeyMap");
        }

        //tranfer the Integer[] of keycodes to a primitive int[]
        int[] temp = new int[20];
        for (int index = 0; index <= 20; index++) {
            temp[index] = keycodes[index].intValue();
        }
        //push the array of primitives to the loader saver
        sl.push(temp);

        //close the saver operation
        if (nSC) {
            sl.close();
        }
    }

    /**
     * Replaces the specified key code at the specified index
     * @param index
     * @param oldCode
     * @param newCode
     */
    public void replaceKeyCode(int index, int oldCode, int newCode) {
        keycodes[index] = newCode;
        Command c = keymap.get(oldCode);
        keymap.remove(oldCode);
        keymap.put(newCode, c);
    }

    /**
     *
     * @return the array of key codes
     */
    public Integer[] getKeyCodes() {
        return keycodes;
    }
}
