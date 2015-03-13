package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class GameView extends ViewPort {

    //the 4 views
    private ViewPort mapView;
    private ViewPort statsView;
    private ViewPort inventoryView;
    private ViewPort logView;
    
    public GameView( int componentWidth, int componentHeight, KeyListener kl, MapView mv, StatsView sv, InventoryView iv, LogView lv ) {

        //set layout, size, color and listener
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.LIGHT_GRAY);
        this.setSize(componentWidth, componentHeight);
        this.addKeyListener(kl);
        mapView = mv;
        statsView = sv;
        inventoryView = iv;
        logView = lv;

        //add map and log view
        Box mapAndLog = new Box(BoxLayout.Y_AXIS);
        mapAndLog.setMaximumSize( new Dimension(componentHeight, componentHeight));
        Box map = new Box(BoxLayout.X_AXIS);
        map.add(Box.createHorizontalGlue());
        Box map2 = new Box(BoxLayout.X_AXIS);
        map2.setMaximumSize( new Dimension(componentHeight, componentHeight-(4*View.TILE_SIZE)));
        map2.setPreferredSize( new Dimension(componentHeight, componentHeight-(4*View.TILE_SIZE)));
        map2.add(mv);
        map.add(map2);
        map.add(Box.createHorizontalGlue());
        mapAndLog.add(map);
        mapAndLog.add(lv);
        this.add(mapAndLog);

        //add inventory and stats view
        Box invAndStats = new Box(BoxLayout.Y_AXIS);
        invAndStats.setMaximumSize(new Dimension(componentWidth-componentHeight, componentHeight));
        invAndStats.add(sv);
        invAndStats.add(iv);
        
        this.add(invAndStats);
    }

    /**
     * make sure we have the focus, update all components inside
     */
    public void update() {
        this.requestFocus();
        mapView.update();
        statsView.update();
        inventoryView.update();
        logView.update();
    }
    
}
