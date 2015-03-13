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

public class PauseMenuView extends JPanel {

    //buttons and size for them
    private JButton btnSaveGame;
    private JButton btnResumeGame;
    private JButton btnNewGame;
    private JButton btnLoadGame;
    private JButton btnOptionMenu;
    private JButton btnExit;
    private final int btnWidth = 200;
    private final int btnHeight = 40;
    
    public PauseMenuView ( ActionListener forSaveGame, ActionListener forResumeGame, ActionListener forNewGame, ActionListener forLoadGame, ActionListener forOptionMenu, ActionListener forExit ) {
        //set ayout and color
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.LIGHT_GRAY);

        //make all the buttons, set their layout settings
        //add their listeners then add them to this
        btnSaveGame = new JButton("Save Game");
        btnResumeGame = new JButton("Resume Game");
        btnNewGame = new JButton("New Game");
        btnLoadGame = new JButton("Load Game");
        btnOptionMenu = new JButton("Options");
        btnExit = new JButton("Exit");
        
        btnSaveGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResumeGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOptionMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnSaveGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnSaveGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnResumeGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnResumeGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnNewGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnNewGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnLoadGame.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnLoadGame.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnOptionMenu.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnOptionMenu.setMaximumSize(new Dimension(btnWidth, btnHeight));
        btnExit.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnExit.setMaximumSize(new Dimension(btnWidth, btnHeight));
        
        btnSaveGame.addActionListener( forSaveGame );
        btnResumeGame.addActionListener( forResumeGame );
        btnNewGame.addActionListener( forNewGame );
        btnLoadGame.addActionListener( forLoadGame );
        btnOptionMenu.addActionListener( forOptionMenu );
        btnExit.addActionListener( forExit );
        
        this.add(Box.createVerticalGlue());
        this.add(btnSaveGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnResumeGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnNewGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnLoadGame);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnOptionMenu);
        this.add( Box.createRigidArea(new Dimension(btnWidth, btnHeight/3)) );
        this.add(btnExit);
        this.add(Box.createVerticalGlue());
        
    }
    
}
