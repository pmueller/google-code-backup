package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionMenuView extends JPanel {

    //keycodes that the stuff is mapped to
    private Integer[] keys;
    //textfields where users input new mappings
    private JTextField[] remappingText;
    //labels that display the current mapped key
    private JLabel[] keyLabel;

    public OptionMenuView( ActionListener returnButton, List<ActionListener> listeners, Integer[] keys, controller.OptionMenuState oms) {
        //set the instance variables
        this.keys = keys;
        oms.setView(this);

        //set the layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalGlue());
        
        Box vertContainer = new Box(BoxLayout.Y_AXIS);

        //make and populate the arraylist with all the names of the mappings
        //...baddddd connascence
        ArrayList<String> functions = new ArrayList<String>();
        functions.add("Move North: ");
        functions.add("Move South: ");
        functions.add("Move West: ");
        functions.add("Move East: ");
        functions.add("Move NorthWest: ");
        functions.add("Move NorthEast: ");
        functions.add("Move SouthWest: ");
        functions.add("Move SouthEast: ");
        functions.add("Pause: ");
        functions.add("Attack: ");
        functions.add("Ability 1: ");
        functions.add("Ability 2: ");
        functions.add("Ability 3: ");
        functions.add("Ability 4: ");
        functions.add("Ability 5: ");
        functions.add("Ability 6: ");
        functions.add("Ability 7: ");
        functions.add("Ability 8: ");
        functions.add("Ability 9: ");
        functions.add("Ability 0: ");

        //boxes for layout
        Box horizontal = new Box(BoxLayout.X_AXIS);
        horizontal.add(Box.createHorizontalGlue());
        Box names = new Box(BoxLayout.Y_AXIS);
        names.setMaximumSize(new Dimension(600,600));
        remappingText = new JTextField[listeners.size()];
        keyLabel = new JLabel[keys.length];

        //for each mapping, add in the name
        //then the current mapping, then the remap button
        //then the textfield for the new mapping
        for(int i = 0; i < functions.size(); ++i) {
            Box bar = new Box(BoxLayout.X_AXIS);
            JLabel jl = new JLabel(functions.get(i));
            jl.setMaximumSize(new Dimension(60, 25));
            jl.setPreferredSize(new Dimension(60, 25));
            bar.add(new JLabel(functions.get(i)));
            keyLabel[i] = new JLabel(KeyEvent.getKeyText(keys[i]));
            bar.add(keyLabel[i]);
            JButton foo = new JButton("Remap");
            foo.addActionListener(listeners.get(i));
            bar.add(foo);
            remappingText[i] = new JTextField();
            remappingText[i].setColumns(1);
            remappingText[i].setMaximumSize(new Dimension(25,25));
            bar.add(remappingText[i]);
            names.add(bar);
        }

        horizontal.add(names);
        horizontal.add(Box.createHorizontalGlue());
        vertContainer.add(horizontal);

        //make back button, set size and add
        JButton back = new JButton("Back");
        back.setMaximumSize(new Dimension(150,30));
        back.setPreferredSize(new Dimension(150,30));
        back.addActionListener(returnButton);
        vertContainer.add(back);

        this.add(vertContainer);

        this.add(Box.createHorizontalGlue());
    }

    /**
     * only called by the controller state on
     * when a user hits the remap button associated
     * with mapping i
     * @param i the keymapping index you want
     * @return new key at that spot
     */
    public int getKeyCodeAt(int i) {
        //if it's a valid text
        if(!remappingText[i].getText().equals("")) {
            //set the new keycode
            keys[i] = (int)remappingText[i].getText().toUpperCase().charAt(0);
            //changed the label for the new map
            keyLabel[i].setText(KeyEvent.getKeyText(keys[i]));
            return keys[i];
        }
        else //default... bad
            return ' ';
        
    }
    
}
