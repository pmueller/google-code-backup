package view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import util.ImageManager;

import util.StatsVisitor;

public class StatsView extends ViewPort {
    
    private controller.GamePlayState gps;
    //labels and buttons to display
    private JLabel HP;
    private JLabel MP;
    private JLabel maxHP;
    private JLabel maxMP;
    private JLabel GP;
    private JLabel lives;
    private JLabel level;
    private JLabel exp;
    private JLabel[] upgradableStat;
    private JButton[] upgradeStatButton;
    private JLabel[] nonUpgradableStat;
    private JLabel[] skillLabel;
    private JButton[] skillButton;
    private JLabel skillPointLabel;
    private JLabel statPointLabel;
    private JLabel[] abilityMappings;

    public StatsView (int componentWidth, int componentHeight, controller.GamePlayState gps) {
        //set size, register with gpstate
        this.setMaximumSize( new Dimension(componentWidth, componentHeight));
        this.setPreferredSize( new Dimension(componentWidth, componentHeight));
        this.gps = gps;
        gps.setStatsView(this);
        this.setSize(componentWidth, componentHeight);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.add(Box.createHorizontalBox());

        JTabbedPane display = new JTabbedPane();
        //make it so gp state doesnt lose focus
        display.setFocusable(false);
        Box stats = new Box(BoxLayout.Y_AXIS);
        Box skills = new Box(BoxLayout.Y_AXIS);
        Box abilities = new Box(BoxLayout.Y_AXIS);

        //get all the inital stats
        StatsVisitor sv = new StatsVisitor();
        View.getInstance().sendAvatarVisitorToModel(sv);

        //make globals and add them to screen
        Box globals = new Box(BoxLayout.X_AXIS);

        Box g1 = new Box(BoxLayout.Y_AXIS);

        Box foo = new Box(BoxLayout.X_AXIS);
        HP = new JLabel(sv.getHP());
        foo.add(new JLabel("HP: "));
        foo.add(HP);
        foo.add(new JLabel("/"));
        maxHP = new JLabel(sv.getMaxHP());
        foo.add(maxHP);

        g1.add(foo);

        foo = new Box(BoxLayout.X_AXIS);
        MP = new JLabel(sv.getMP());
        foo.add(new JLabel("MP: "));
        foo.add(MP);
        foo.add(new JLabel("/"));
        maxMP = new JLabel(sv.getMaxMP());
        foo.add(maxMP);

        g1.add(foo);

        foo = new Box(BoxLayout.X_AXIS);
        GP = new JLabel(sv.getGP() );
        foo.add(new JLabel("Gold: "));
        foo.add(GP);

        g1.add(foo);

        globals.add(g1);

        globals.add(Box.createRigidArea(new Dimension(30,10)));

        Box g2 = new Box(BoxLayout.Y_AXIS);

        foo = new Box(BoxLayout.X_AXIS);
        lives = new JLabel(sv.getLives());
        foo.add(new JLabel("Lives: "));
        foo.add(lives);

        g2.add(foo);

        foo = new Box(BoxLayout.X_AXIS);
        level = new JLabel(sv.getLevel());
        foo.add(new JLabel("Level: "));
        foo.add(level);

        g2.add(foo);

        foo = new Box(BoxLayout.X_AXIS);
        exp = new JLabel(sv.getExp());
        foo.add(new JLabel("Exp: "));
        foo.add(exp);

        g2.add(foo);

        globals.add(g2);

        //get the upgradable stats, nonupgradable stats
        //and skills
        List<String> uStatNames = sv.getUpgradableStatNames();
        List<String> nuStatNames = sv.getNonUpgradableStatNames();
        List<String> skillNames = sv.getSkillNames();

        List<String> uStatVals = sv.getUpgradableStatVals();
        List<String> nuStatVals = sv.getNonUpgradableStatVals();
        List<String> skillVals = sv.getSkillVals();

        upgradableStat = new JLabel[uStatNames.size()];
        upgradeStatButton = new JButton[uStatNames.size()];

        nonUpgradableStat = new JLabel[nuStatNames.size()];

        skillLabel = new JLabel[skillNames.size()];
        skillButton = new JButton[skillNames.size()];

        List<MouseListener> listeners = gps.getStatListeners(uStatNames);

        Box asdf = new Box(BoxLayout.X_AXIS);

        asdf.add(new JLabel("Stat Points: "));
        statPointLabel = new JLabel(String.valueOf(sv.getStatPoints()));
        asdf.add(statPointLabel);
        stats.add(asdf);
        
        stats.add(Box.createRigidArea(new Dimension(20,20)));

        //for each upgradable stat, add the stat and put the upgrade button
        for(int i = 0; i < uStatNames.size(); ++i) {
            Box someStat = new Box(BoxLayout.X_AXIS);
            someStat.add(new JLabel(uStatNames.get(i) + ": "));
            upgradableStat[i] = new JLabel(uStatVals.get(i));
            someStat.add(upgradableStat[i]);
            someStat.add(Box.createRigidArea(new Dimension(10,10)));
            upgradeStatButton[i] = new JButton();
            upgradeStatButton[i].setMaximumSize(new Dimension(15,15));
            upgradeStatButton[i].setPreferredSize(new Dimension(15,15));
            upgradeStatButton[i].addMouseListener(listeners.get(i));
            upgradeStatButton[i].setVisible(false);
            upgradeStatButton[i].setIcon(new ImageIcon(ImageManager.getInstance().getImage("AddButton")));
            someStat.add(upgradeStatButton[i]);
            stats.add(someStat);
        }

        //for each nonupgradable stat, add the stat
        for(int i = 0; i < nuStatNames.size(); ++i) {
            Box someStat = new Box(BoxLayout.X_AXIS);
            someStat.add(new JLabel(nuStatNames.get(i) + ": "));
            nonUpgradableStat[i] = new JLabel(nuStatVals.get(i));
            someStat.add(nonUpgradableStat[i]);
            stats.add(someStat);
        }

        List<MouseListener> skillListeners = gps.getSkillListeners(skillNames);

        Box zxcv = new Box(BoxLayout.X_AXIS);
        zxcv.add(new JLabel("Skill Points: "));
        skillPointLabel = new JLabel(String.valueOf(sv.getSkillPoints()));
        zxcv.add(skillPointLabel);
        skills.add(zxcv);

        skills.add(Box.createRigidArea(new Dimension(20,20)));
        //for each skill, add the name and value and upgrade button
        for(int i = 0; i < skillNames.size(); ++i) {
            Box someStat = new Box(BoxLayout.X_AXIS);
            someStat.add(new JLabel(skillNames.get(i) + ": "));
            skillLabel[i] = new JLabel(skillVals.get(i));
            someStat.add(skillLabel[i]);
            someStat.add(Box.createRigidArea(new Dimension(10,10)));
            skillButton[i] = new JButton();
            skillButton[i].setMaximumSize(new Dimension(15,15));
            skillButton[i].setPreferredSize(new Dimension(15,15));
            skillButton[i].addMouseListener(skillListeners.get(i));
            skillButton[i].setVisible(false);
            skillButton[i].setIcon(new ImageIcon(ImageManager.getInstance().getImage("AddButton")));
            someStat.add(skillButton[i]);
            skills.add(someStat);
        }

        List<String> abilityNames = sv.getAbilityNames();
        Integer[] keys = gps.getAbilityKeyCodes();
        abilityMappings = new JLabel[keys.length];

        //get and show all the abilities, along
        //with their respective key mappings
        int count = 0;
        for(int i = 0; i < abilityNames.size(); ++i) {
            Box someStat = new Box(BoxLayout.X_AXIS);
            abilityMappings[i] = new JLabel(String.valueOf(KeyEvent.getKeyText(keys[i])) + ": ");
            someStat.add(abilityMappings[i]);
            someStat.add(new JLabel(abilityNames.get(i)));
            abilities.add(someStat);
            count++;
        }

        //make the tabs in the tabbed pane
        //put in the relevent components
        display.addTab("Stats", stats);
        display.addTab("Skills", skills);
        display.addTab("Abilities", abilities);

        Box vertContainer = new Box(BoxLayout.Y_AXIS);
        vertContainer.add(globals);
        vertContainer.add(display);
        this.add(vertContainer);
        this.add(Box.createHorizontalBox());
    }

    /**
     * sends visitor to the model then
     * updates the relevant information
     */
    public void update() {
       //make visitor and send it
        StatsVisitor sv = new StatsVisitor();
        View.getInstance().sendAvatarVisitorToModel(sv);

        //set the globals
        HP.setText(sv.getHP());
        maxHP.setText(sv.getMaxHP());
        MP.setText(sv.getMP());
        maxMP.setText(sv.getMaxMP());
        GP.setText(sv.getGP());
        lives.setText(sv.getLives());
        level.setText(sv.getLevel());
        exp.setText(sv.getExp());

        int statPoints = sv.getStatPoints();
        int skillPoints = sv.getSkillPoints();

        //set skill points
        skillPointLabel.setText(String.valueOf(skillPoints));
        statPointLabel.setText(String.valueOf(statPoints));

        //show the buttons only if you have
        //points for them
        for(JButton b : upgradeStatButton) {
            if(statPoints > 0) {
                b.setVisible(true);
            }
            else {
                b.setVisible(false);
            }
        }

        for(JButton b : skillButton) {
            if(skillPoints > 0) {
                b.setVisible(true);
            }
            else {
                b.setVisible(false);
            }
        }
        


        List<String> uStatVals = sv.getUpgradableStatVals();
        List<String> nuStatVals = sv.getNonUpgradableStatVals();
        List<String> skillVals = sv.getSkillVals();

        //update the values of the stats and skill values
        for(int i = 0; i < uStatVals.size(); ++i) {
            upgradableStat[i].setText(uStatVals.get(i));
        }

        for(int i = 0; i < nuStatVals.size(); ++i) {
            nonUpgradableStat[i].setText(nuStatVals.get(i));
        }

        for(int i = 0; i < skillVals.size(); ++i) {
            skillLabel[i].setText(skillVals.get(i));
        }
    
    }
    
}
