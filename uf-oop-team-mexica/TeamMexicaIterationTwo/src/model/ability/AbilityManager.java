package model.ability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import model.*;
import model.mapstuff.aoe.AoE;
import util.*;

//========================================================//
public final class AbilityManager implements Updateable, Saveable, Cloneable {

    private MapGrid mapGrid;
    private ArrayList<Ability> propagatingAbilities;
    private HashMap<Ability, Integer> cyclesToWaitMap;
    private HashMap<Ability, Integer> cyclesCompleted;

    /**
     * Constructor: initializes the lists and hashmaps
     * @param mapGridRef
     */
    public AbilityManager(MapGrid mapGridRef) {
        mapGrid = model.Model.getInstance().getMap();
        propagatingAbilities = new ArrayList<Ability>();
        cyclesToWaitMap = new HashMap<Ability, Integer>();
        cyclesCompleted = new HashMap<Ability, Integer>();
        this.registerForUpdate();
    }

    /**
     * Sets the map grid
     * @param mapGridRef
     */
    public void loadMapGrid() {
        mapGrid = model.Model.getInstance().getMap();
    }
//========================================================//

    /**
     * Adds the specified ability to associated lists
     * @param ability
     */
    private void addAbility(Ability ability) {
        propagatingAbilities.add(ability);
        cyclesToWaitMap.put(ability, ability.getRateOfPropagation());
        cyclesCompleted.put(ability, 0);
    }

    /**
     * Removes the specified ability
     * @param ability
     */
    private void removeAbility(Ability ability) {
        cyclesToWaitMap.remove(ability);
        cyclesCompleted.remove(ability);
        propagatingAbilities.remove(ability);
    }

    /**
     * Propagates the ability
     * @param ability
     */
    public void propagateAbility(Ability ability) {
        removeAbility(ability);
        addAbility(ability);
    }

    /**
     *
     * @param ability
     */
    private void singleCyclePropagate(Ability ability) {
        loadMapGrid();
        if (ability.getShape().hasNext()) {
            List<Vector> vectors = ability.getShape().next();
            List<Tile> tiles = new ArrayList<Tile>();
            int cycles = cyclesCompleted.get(ability);

            for (Vector v : vectors) {
                Tile tile = mapGrid.getTileAtPosition(v);
                if (tile != null) {
                    tiles.add(tile);
                }
            }

            for ( int i = 0; i < tiles.size(); i++ ) {
                AoE aoe;
                try {
                    aoe = ability.getAreaEffect().clone();

                    System.out.println("rate of entropy = " + ability.getRateOfEntropy() + "  cycles" + cycles);
                    System.out.println("  initial power = " + ability.getInitialPower());
                    aoe.setEffectPower(aoe.getEffect().getPower() - ability.getRateOfEntropy() * cycles);
                    aoe.registerToTile(tiles.get(i));
                    System.out.println("aoe added to tile: " + tiles.get(i).toString());
                } catch (CloneNotSupportedException e) {
                    System.out.println("problem cloning aoe");
                }
            }

            cycles++;
            cyclesCompleted.put(ability, cycles);
        } else {
            // no more expansion, so no more propagation
            removeAbility(ability);
        }
        // } catch (Exception e) {} //TODO: fix...
    }

    public void update() {
        for (int index = 0; index < propagatingAbilities.size(); index++ ) {
            Ability a = propagatingAbilities.get(index);
            int i = cyclesToWaitMap.get(a);
            i--;
            if (i <= 0) {
                cyclesToWaitMap.put(a, a.getRateOfPropagation());
                singleCyclePropagate(a);
            } else {
                cyclesToWaitMap.put(a, i);
            }
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.propagatingAbilities = s.pullList();

        int cyclesSize = s.pullInt();
        this.cyclesToWaitMap = new HashMap<Ability, Integer>(cyclesSize);
        for (int i = 0; i < cyclesSize; i++) {
            Ability ability = s.pullSaveable();
            Integer integer = s.pullInt();
            this.cyclesToWaitMap.put(ability, integer);
        }

        int completedSize = s.pullInt();
        this.cyclesCompleted = new HashMap<Ability, Integer>(completedSize);
        for (int i = 0; i < completedSize; i++) {
            Ability ability = s.pullSaveable();
            Integer integer = s.pullInt();
            this.cyclesCompleted.put(ability, integer);
        }
            mapGrid = model.Model.getInstance().getMap();
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("AbilityManager");
        }

        s.push(this.propagatingAbilities);
        s.push(this.cyclesToWaitMap.size());
        Set waitSet = this.cyclesToWaitMap.keySet();
        Iterator<Ability> waitIterator = waitSet.iterator();
        while (waitIterator.hasNext()) {
            Ability key = waitIterator.next();
            s.push(key, true);
            s.push(this.cyclesToWaitMap.get(key));
        }

        s.push(this.cyclesCompleted.size());
        Set completedSet = this.cyclesToWaitMap.keySet();
        Iterator<Ability> completedIterator = completedSet.iterator();
        while (completedIterator.hasNext()) {
            Ability key = completedIterator.next();
            s.push(key, true);
            s.push(this.cyclesCompleted.get(key));
        }

        if (notSuperClass) {
            s.close();
        }
    }

    public void registerForUpdate() {
        model.Model.getInstance().registerObserver(this);
    }

    public void unRegisterForUpdate() {
        model.Model.getInstance().removeObserver(this);
    }
}
//========================================================//

