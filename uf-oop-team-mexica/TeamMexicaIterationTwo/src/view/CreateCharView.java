package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CreateCharView extends JPanel {


    //define private fields.
    private JButton btnCreateChar;
    private JTextField txtName;
    private ButtonGroup bgGroup;
    private JRadioButton rdoSmasher;
    private JRadioButton rdoSummoner;
    private JRadioButton rdoSneak;
    //dimensions for buttons on this
    private final int btnWidth = 200;
    private final int btnHeight = 40;
    //dimensions for content area of this
    private final int showWidth = 350;
    private final int showHeight = 500;
    
    public CreateCharView( ActionListener forCreateChar, controller.CreateCharState ccs ) {
        //register with the controller state
        ccs.setView(this);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.LIGHT_GRAY);

        //create button
        btnCreateChar = new JButton("Create Character");
        btnCreateChar.addActionListener(forCreateChar);

        //add it to the container
        Box container = new Box(BoxLayout.Y_AXIS);
        container.setMaximumSize(new Dimension(showWidth, showHeight));
        container.add(Box.createVerticalGlue());

        //container for all the input (textfield and radiobuttons)
        Box input = new Box(BoxLayout.X_AXIS);
        input.add(new JLabel("Character Name:"));

        //set size of textfield
        txtName = new JTextField();
        txtName.setColumns(20);

        //add in textfield
        input.add(txtName);
        input.setMaximumSize(new Dimension(showWidth,40));

        container.add(input);
        
        container.add( Box.createRigidArea(new Dimension(btnWidth, 20)) );
        
        Box options = new Box(BoxLayout.Y_AXIS);

        //make rdos with Smasher as default
        rdoSmasher = new JRadioButton("Smasher");
        rdoSmasher.setSelected(true);
        rdoSummoner = new JRadioButton("Summoner");
        rdoSneak = new JRadioButton("Sneak");

        //add all the radio buttons into the group
        bgGroup = new ButtonGroup();
        bgGroup.add(rdoSmasher);
        bgGroup.add(rdoSneak);
        bgGroup.add(rdoSummoner);
        //allign to the left
        rdoSmasher.setAlignmentX(LEFT_ALIGNMENT);
        //add them into the container
        options.add(rdoSmasher);
        options.add( Box.createRigidArea(new Dimension(50, 5)) );
        options.add(rdoSneak);
        options.add( Box.createRigidArea(new Dimension(50, 5)) );
        options.add(rdoSummoner);
        
        //add into a container to center
       Box b = new Box(BoxLayout.X_AXIS);
       
       b.add(options);
       b.add(Box.createHorizontalGlue());
        
       container.add(b);
       container.add( Box.createRigidArea(new Dimension(btnWidth, 20)) );
       
       btnCreateChar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       btnCreateChar.setPreferredSize(new Dimension(btnWidth, btnHeight));
       btnCreateChar.setMaximumSize(new Dimension(btnWidth, btnHeight));

       //add overall container to this
       container.add(btnCreateChar);
       container.add(Box.createVerticalGlue());
       this.add(container);
    }

    /**
     * get the name entered by user
     * @return String, name of char selected
     */
    public String getCharName() {
        return txtName.getText();
    }

    /**
     * get occupation selected
     * @return String of selected occ
     */
    public String getSelectedOccupation() {

        //go through all the buttons
        //if one is selected, return that text (the occupation name)
        for( Enumeration<AbstractButton> e = bgGroup.getElements(); e.hasMoreElements(); ) {
            AbstractButton ab = e.nextElement();
            if(ab.isSelected()) {
                return ab.getText();
            }
        }
        
        throw new RuntimeException("No Occupation Selected on CreateCharView");
    }
  
}
