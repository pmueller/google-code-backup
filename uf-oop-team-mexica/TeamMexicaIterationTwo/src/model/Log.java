package model;

import java.io.*;
import java.util.*;
import util.*;

public class Log implements Updateable, Saveable {

    private List<String> combatLog, statLog, dialogueLog, inventoryLog, itemLog, generalLog;
    private List<String> log; // the whole log

    /**
     * Constructor: creates new ArrayList<String> for all ivars
     */
    public Log() {
        combatLog = new ArrayList<String>();
        statLog = new ArrayList<String>();
        dialogueLog = new ArrayList<String>();
        inventoryLog = new ArrayList<String>();
        itemLog = new ArrayList<String>();
        generalLog = new ArrayList<String>();

        log = new ArrayList<String>();
    }

    /**
     * Originally made to add additional information, such as the Date (time)
     * of the message.
     * 
     * @param logMessage
     * @return
     */
    private String log(String logMessage) {
        log.add(logMessage);
        return logMessage;
    }

    /**
     * Called by the victim when it encounters the Effect AoE
     * that was dropped by the creator, and the Effect AoE should
     * have a field that is the name of the creator and the name of
     * the effect itself should just be the Effect AoE's toString().
     *
     * @param attacker
     * @param defender
     */
    public void logCombat(String creator, String victim, String effect) {
        String message = log("COMBAT:  " + creator + " p "
                + effect + " on " + victim);
        combatLog.add(message);
    }
    public void logCombatDodge(String victim) {
        String message = log("COMBAT: "+victim+" dodges!");
        combatLog.add(message);
    }


    public void logGeneral(String str) {
        String message = log(str);
        generalLog.add(message);
    }


    public void logStatChangedByDelta(String victim, String effect, int delta) {
        String message = log("STAT CHANGE:  " + victim + (delta >= 0 ? " gained " :
                                    " lost ") + Math.abs(delta) + " " + effect);
        statLog.add(message);
    }



    /**
     * Called from within an Entity when any of its Stats. Pass in the name of the
     * entity, its stat, the oldValue of the stat in String format, and the newValue
     * in String format. Do not call this operation when there was no change.
     *
     * @param entity
     * @param stat
     * @param oldValue
     * @param newValue
     */
    public void logStatChange(String entity, String stat, String oldValue, String newValue) {
        String message = log("STAT CHANGE:  " + entity + "'s " + stat
                + " changed from " + oldValue + " to " + newValue);
        statLog.add(message);
    }

    /**
     * Called from within an NPC when it speaks.
     *
     * @param speaker
     * @param dialogue
     */
    public void logDialogue(String speaker, String dialogue) {
        String message = log("DIALOGUE:  " + speaker + " says: "
                + dialogue);
        dialogueLog.add(message);
        //log.add( message );
    }

    /**
     * Called from within an Entity when an InventoryItem has been added or
     * removed. The boolean added is true if item was added and false if it was
     * removed.
     *
     * @param item
     * @param added
     */
    public void logInventoryChange(String item, boolean added) {
        String message = log("INVENTORY CHANGE:  " + item + " was "
                + (added ? "added" : "removed"));
        inventoryLog.add(message);
    }

    /**
     * Called from within an Entity when an Item is used.
     *
     * @param item
     */
    public void logItemUsed(String item) {
        String message = log("ITEM USED:  " + item + " was used");
        itemLog.add(message);
    }

    /**
     * Returns the entire log where all logged messages are in the order
     * they were added in.
     * 
     * @return the log
     */
    public List<String> getLog() {
        return log;
    }

    /**
     * Returns the last n number of messages, if available, in the collective
     * log. If there are less than n messages, returns all available messages.
     *
     * @param n number of log messages
     * @return
     */
    public List<String> getLog(int n) {
        if (n >= log.size()) {
            return log.subList(log.size() - n, log.size());
        } else {
            return log;
        }
    }

    /**
     * @return the combatLog
     */
    public List<String> getCombatLog() {
        return combatLog;
    }

    /**
     * @return the statLog
     */
    public List<String> getStatLog() {
        return statLog;
    }

    /**
     * @return the dialogueLog
     */
    public List<String> getDialogueLog() {
        return dialogueLog;
    }

    /**
     * @return the inventoryLog
     */
    public List<String> getInventoryLog() {
        return inventoryLog;
    }

    /**
     * @return the itemLog
     */
    public List<String> getItemLog() {
        return itemLog;
    }

    /**
     * Erases everything from all logs
     */
    public void clear() {
        combatLog.clear();
        statLog.clear();
        inventoryLog.clear();
        dialogueLog.clear();
        itemLog.clear();
        generalLog.clear();

        log.clear();
    }

    /**
     * LogVisitor accept method
     * @param v
     */
    public void accept(LogVisitor v) {
        v.visit(this);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        // combat log
        int combatSize = s.pullInt();
        this.combatLog = new ArrayList<String>(combatSize);
        for (int i = 0; i < combatSize; i++) {
            this.combatLog.add(s.pullString());
        }

        // stat log
        int statSize = s.pullInt();
        this.statLog = new ArrayList<String>(statSize);
        for (int i = 0; i < statSize; i++) {
            this.statLog.add(s.pullString());
        }

        // dialogue log
        int dialogueSize = s.pullInt();
        this.dialogueLog = new ArrayList<String>(dialogueSize);
        for (int i = 0; i < dialogueSize; i++) {
            this.dialogueLog.add(s.pullString());
        }

        // inventory log
        int inventorySize = s.pullInt();
        this.inventoryLog = new ArrayList<String>(inventorySize);
        for (int i = 0; i < inventorySize; i++) {
            this.inventoryLog.add(s.pullString());
        }

        // item log
        int itemSize = s.pullInt();
        this.itemLog = new ArrayList<String>(itemSize);
        for (int i = 0; i < itemSize; i++) {
            this.itemLog.add(s.pullString());
        }

        // (whole) log
        int logSize = s.pullInt();
        this.log = new ArrayList<String>(logSize);
        for (int i = 0; i < logSize; i++) {
            this.log.add(s.pullString());
        }

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Log");
        }

        // combat log
        s.push(this.combatLog.size());
        Iterator<String> combatIterator = this.combatLog.iterator();
        while (combatIterator.hasNext()) {
            s.push(combatIterator.next());
        }

        // stat log
        s.push(this.statLog.size());
        Iterator<String> statIterator = this.statLog.iterator();
        while (statIterator.hasNext()) {
            s.push(statIterator.next());
        }

        // dialogue log
        s.push(this.dialogueLog.size());
        Iterator<String> dialogueIterator = this.dialogueLog.iterator();
        while (dialogueIterator.hasNext()) {
            s.push(dialogueIterator.next());
        }

        // inventory log
        s.push(this.inventoryLog.size());
        Iterator<String> inventoryIterator = this.inventoryLog.iterator();
        while (inventoryIterator.hasNext()) {
            s.push(inventoryIterator.next());
        }

        // item log
        s.push(this.itemLog.size());
        Iterator<String> itemIterator = this.itemLog.iterator();
        while (itemIterator.hasNext()) {
            s.push(itemIterator.next());
        }

        // (whole) log
        s.push(this.log.size());
        Iterator<String> logIterator = this.log.iterator();
        while (logIterator.hasNext()) {
            s.push(logIterator.next());
        }


        if (notSuperClass) {
            s.close();
        }
    }

    public void registerForUpdate() {
        Model.getInstance().registerObserver(this);
    }

    public void update() {
        //um not sure if update is necessary
    }

    public void unRegisterForUpdate() {
        Model.getInstance().removeObserver(this);
    }
}
