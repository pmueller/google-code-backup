package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuView extends JPanel {

    private JButton btnNewGame;
    private JButton btnLoadGame;
    private JButton btnExit;
    
    private final int btnHeight = 40;
    private final int btnWidth = 200;
    
    public MainMenuView( ActionListener forNewGame, ActionListener forLoadGame, ActionListener forExit) {
      //  BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        //this.setSize(1024,750);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.setBackground(Color.LIGHT_GRAY);

        //make and set settings for buttons
        btnNewGame = new JButton("New Game");
        btnLoadGame = new JButton("Load Game");
        btnExit = new JButton("Exit");
        
        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnNewGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnNewGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnLoadGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnLoadGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnExit.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnExit.setMaximumSize(new Dimension(btnWidth, btnHeight));

        //add listeners then add to this
        btnNewGame.addActionListener(forNewGame);
        btnLoadGame.addActionListener(forLoadGame);
        btnExit.addActionListener(forExit);
        
        this.add(Box.createVerticalGlue());
        this.add(btnNewGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnLoadGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnExit);
        this.add(Box.createVerticalGlue());
        
    }
    
}
