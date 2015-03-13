package model.behavior;

import java.io.IOException;
import model.*;
import model.entity.npc.NPC;
import util.*;

public class TigerBehavior implements UpdateableBehavior {

    private NPC owner;
    private Log logger;
    private final TigerBehavior tb = this;
    private Actor enemy;
    private UpdateableBehavior currentState;
    private UpdateableBehavior sleeping, angry;

    /**
     * Constructor: sets the owner and logger references, and calls initStates()
     * @param owner
     * @param logger
     */
    public TigerBehavior(NPC owner, Log logger) {
        this.owner = owner;
        this.logger = logger;

        initStates();
    }

    /**
     * Sets the specified state
     * @param state
     */
    private void setState(UpdateableBehavior state) {
        currentState = state;
    }

    /**
     * Creates the sleeping and angry state, and sets the current state to
     * sleeping
     */
    private void initStates() {
        sleeping = new UpdateableBehavior() {

            private int timesPoked = 0;

            public void interactWithActor(Actor actor) {
                if (timesPoked > 2) {
                    logger.logDialogue(owner.getName(), "Now you've done it!");
                    timesPoked = 0;
                    enemy = actor;
                    tb.setState(angry);
                } else {
                    logger.logDialogue(owner.getName(), "ZZzzzZZZzzzzZZzz");
                    timesPoked++;
                }
            }

            public void updateBehavior() {
                owner.setRepresentation("SleepingTiger");
            }

            public void setOwner(NPC o) {
            }

            public void load(SaverLoader s, boolean notSuperClass) throws IOException {
                throw new IOException("Anonymous class saving and loading is not possible");
            }

            public void save(SaverLoader s, boolean notSuperClass) throws IOException {
                throw new IOException("Anonymous class saving and loading is not possible");
            }
        };

        currentState = sleeping;

        angry = new UpdateableBehavior() {

            private boolean first = true;
            private final int maxCycles = 200;
            private int cyclesLeft = maxCycles;

            public void interactWithActor(Actor actor) {
                if (first) {
                    first = false;
                }
                logger.logDialogue(owner.getName(), "ANGRY TIGER!! rawr");

            }

            public void updateBehavior() {
                owner.setRepresentation("Tiger");
                if (owner == null) {
                    setState(sleeping);
                    return;
                }

                Vector tiger = owner.getPosition();
                Vector avatar = enemy.getPosition();
                Vector directionToMove = new Vector(
                        avatar.x > tiger.x ? 1 : (avatar.x < tiger.x) ? -1 : 0,
                        avatar.y > tiger.y ? 1 : (avatar.y < tiger.y) ? -1 : 0);

                owner.attemptMoveDirectionally(directionToMove);

                if (Math.abs(enemy.getPosition().x - tiger.x) == 1 && Math.abs(enemy.getPosition().y - tiger.y) == 1
                        || Math.abs(enemy.getPosition().x - tiger.x) == 1 && Math.abs(enemy.getPosition().y - tiger.y) == 0
                        || Math.abs(enemy.getPosition().x - tiger.x) == 0 && Math.abs(enemy.getPosition().y - tiger.y) == 1) {
                    owner.useAbility(0);
                }
                owner.addToCoolDownTimer(5);


                if (--cyclesLeft <= 0) {
                    cyclesLeft = maxCycles;
                    first = true;

                    tb.setState(sleeping);
                }
            }

            public void setOwner(NPC o) {
            }

            public void load(SaverLoader s, boolean notSuperClass) throws IOException {
                throw new IOException("Anonymous class saving and loading is not possible");
            }

            public void save(SaverLoader s, boolean notSuperClass) throws IOException {
                throw new IOException("Anonymous class saving and loading is not possible");
            }
        };
    }

    /**
     * Passes this method call to the current state
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        currentState.interactWithActor(actor);
    }

    /**
     * Passes this method call to the current state
     */
    public void updateBehavior() {
        currentState.updateBehavior();
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.logger = Model.getInstance().getLog();
        initStates();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    /**
     * Sets the NPC this behavior is attached to
     * @param o
     */
    public void setOwner(NPC o) {
        owner = o;
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("TigerBehavior");
        }

        if (notSuperClass) {
            s.close();
        }
    }
}
