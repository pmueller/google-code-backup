package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GamePlayState extends ControllerState {

    view.StatsView sv;
    private KeyMap keymap;

    /**
     * Constructor: calls init, and sets the game to not paused
     */
    public GamePlayState() {
        init();
        model.Model.getInstance().setPaused(false);
    }

    /**
     *
     * @return the ability key codes
     */
    public Integer[] getAbilityKeyCodes() {
        int numNonAbilityCommands = 9;
        Integer[] codes = keymap.getKeyCodes();
        Integer[] ret = new Integer[codes.length - numNonAbilityCommands];

        for (int i = numNonAbilityCommands; i < codes.length; ++i) {
            ret[i - numNonAbilityCommands] = codes[i];
        }
        return ret;
    }

    /**
     * Sets up the key listener, and initializes the view gameplay
     */
    protected void init() {

        keymap = Controller.getInstance().getKeyMap();
        KeyListener kl = new KeyListener() {

            //These will handle keyboard based event
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                Integer keycode = new Integer(e.getKeyCode());

                keymap.executeCommand(keycode);
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        view.View.getInstance().initGameplay(kl, this);
    }

    /**
     *
     * @param list
     * @return the list of stat listeners
     */
    public List<MouseListener> getStatListeners(List<String> list) {
        final List<String> strings = list;
        ArrayList<MouseListener> listeners = new ArrayList<MouseListener>();

        for (int i = 0; i != list.size(); ++i) {
            final String s = list.get(i);
            listeners.add(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    model.Model.getInstance().upgradeAvatarStat(s);
                }

                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        }
        return listeners;
    }

    /**
     *
     * @param list
     * @return the list of skill listeners
     */
    public List<MouseListener> getSkillListeners(List<String> list) {
        final List<String> strings = list;
        ArrayList<MouseListener> listeners = new ArrayList<MouseListener>();

        for (int i = 0; i != list.size(); ++i) {
            final String s = list.get(i);
            listeners.add(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    model.Model.getInstance().upgradeAvatarSkill(s);
                }

                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        }
        return listeners;
    }

    /**
     *
     * @return the weapon listener
     */
    public MouseListener getWeaponListener() {
        return new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.Model.getInstance().unequipWeapon();
                }
            }

            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    /**
     *
     * @return the armor listener
     */
    public MouseListener getArmorListener() {
        return new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.Model.getInstance().unequipArmor();
                }
            }

            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    /**
     *
     * @param size
     * @return the inventory listener
     */
    public List<MouseListener> getInventoryListeners(int size) {

        ArrayList<MouseListener> listeners = new ArrayList<MouseListener>();

        for (int i = 0; i != size; ++i) {

            final int j = i;
            listeners.add(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        model.Model.getInstance().useItem(j);
                    } else if (e.getButton() == MouseEvent.BUTTON2) {
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        model.Model.getInstance().dropItem(j);
                    }
                }

                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        }
        return listeners;
    }

    /**
     * Sets the stats view
     * @param v
     */
    public void setStatsView(view.StatsView v) {
        sv = v;
    }
}
