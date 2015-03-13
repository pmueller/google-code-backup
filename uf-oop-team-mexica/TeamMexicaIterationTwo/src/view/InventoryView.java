package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import util.InventoryVisitor;

public class InventoryView extends ViewPort {

    //labels for equipment and inventory
    private JLabel weapon;
    private JLabel armor;
    private JLabel[] inventory;
    private JLabel[] invLabels;
   
    public InventoryView(int componentWidth, int componentHeight, controller.GamePlayState gps) {
        this.setSize(componentWidth, componentHeight);
        this.setMaximumSize( new Dimension(componentWidth, componentHeight));
        this.setPreferredSize( new Dimension(componentWidth, componentHeight));

        //this.setBackground(Color.green);
        
        InventoryVisitor iv = new InventoryVisitor();
        View.getInstance().sendAvatarVisitorToModel(iv);

        inventory = new JLabel[ iv.getInventory().length ];        
        invLabels = new JLabel[ inventory.length ];
        
        List<MouseListener> listeners = gps.getInventoryListeners(inventory.length);
        
        for(int i = 0; i < inventory.length; ++i) {
            
            invLabels[i] = new JLabel( (i+1) + ": ");
            inventory[i] = new JLabel(iv.getInventory()[i]);
            inventory[i].addMouseListener(listeners.get(i));
            
        }
        
        
        weapon = new JLabel(iv.getWeapon());
        weapon.addMouseListener(gps.getWeaponListener());
        armor = new JLabel(iv.getArmor());
        armor.addMouseListener(gps.getArmorListener());
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        this.add(Box.createHorizontalBox());

        Box container = new Box(BoxLayout.Y_AXIS);

        this.add(container);


        container.add(new JLabel("Equipment"));
        
        Box equipmentW = new Box(BoxLayout.X_AXIS);
        equipmentW.add( Box.createRigidArea(new Dimension(1,25)));
        equipmentW.add( new JLabel("Weapon: "));
        equipmentW.add(weapon);
        equipmentW.add(Box.createHorizontalGlue());
        container.add(equipmentW);
        
        Box equipmentA = new Box(BoxLayout.X_AXIS);
        equipmentA.add( Box.createRigidArea(new Dimension(1,25)));
        equipmentA.add( new JLabel("Armor: "));
        equipmentA.add(armor);
        equipmentA.add(Box.createHorizontalGlue());
        container.add(equipmentA);
        
        container.add(Box.createRigidArea(new Dimension(20, 20)));
        
        container.add( new JLabel("Inventory") );

        Box inv = new Box(BoxLayout.Y_AXIS);

        for(int i = 0; i < inventory.length; ++i) {
            Box temp = new Box(BoxLayout.X_AXIS);
            temp.add(Box.createRigidArea(new Dimension(25,1)));
            temp.add(invLabels[i]);
            temp.add(inventory[i]);
            temp.add(Box.createHorizontalGlue());
            inv.add( temp );
        }

        JScrollPane sp = new JScrollPane(inv);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        container.add(sp);

        this.add(Box.createHorizontalBox());
    }

    /**
     * send a visitor to the model, update inventory
     */
    public void update() {
        //make the visitor and send it to the model
        InventoryVisitor iv = new InventoryVisitor();
        View.getInstance().sendAvatarVisitorToModel(iv);
        
        //update the inventory based on visitor state
        for(int i = 0; i < iv.getInventory().length; ++i) {
            inventory[i].setText(iv.getInventory()[i]);
        }

        //update equipment based on visitor state
        weapon.setText(iv.getWeapon());
        armor.setText(iv.getArmor()); 
    }
    
}
