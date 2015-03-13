package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import util.ImageManager;

public class TileView extends JPanel {

    //size of the tile
    private final int tileWidth = View.TILE_SIZE;
    private final int tileHeight = View.TILE_SIZE;

    //list of strings that are names of images
    private List<String> toRender;
    
    public TileView(List<String> toRender) {
        //no layout, set the list and size
        this.setLayout(null);
        this.toRender = toRender;
        this.setSize(tileWidth, tileHeight);
    }

    /**
     * sets the torender list
     * @param toRender list of strings that are names (of imgs) to be rendered on this tile
     */
    public void setToRender(List<String> toRender) {
        this.toRender = toRender;
    }

    /**
     * paints every image in the torender list in order
     * @param g the gfx
     */
    protected void paintComponent(Graphics g) {
        //make a new image that takes up the tile
        //use its gfx and make it able to be transparent
        BufferedImage appearance = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = appearance.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        //bad connascence...
        //draw each in order
        for(String img : toRender) {
            g2.drawImage(ImageManager.getInstance().getImage(img), 0, 0, null);
        }
        //kill the graphics so mem doesnt leak then draw
        g2.dispose();
        g.drawImage(appearance, 0, 0, this);
    }
    
}
