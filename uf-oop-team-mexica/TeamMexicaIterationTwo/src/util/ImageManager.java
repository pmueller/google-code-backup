package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {

    private HashMap<String, BufferedImage> imgs;
    private static ImageManager instance;
    private static final String FILE_EXT = ".png";
    private static final String FILE_PATH = "src/resources/";
    
    /**
     * Constructor
     */
    private ImageManager() {
        imgs = new HashMap<String, BufferedImage>();
        try {
            imgs.put("Blank", ImageIO.read(new File(FILE_PATH + "Blank" + FILE_EXT)));
        }
        catch(Exception e) {
            System.out.println("There goes the neighborhood...");
        }
    }

    /**
     * get the sole instance of the imagemanager because it's a singleton
     * @return
     */
    public static ImageManager getInstance() {
        if(instance == null ) {
            instance = new ImageManager();

        }
        return instance;
    }

    /**
     * get the image with file name "name"
     * @param name
     * @return image with filename name
     */
    public Image getImage(String name) {
        if( !imgs.containsKey(name) ) {
            try {
                imgs.put(name, ImageIO.read(new File(FILE_PATH + name + FILE_EXT)));
            } catch(Exception e) {
                System.out.println(name + FILE_EXT + " has failed to load. Might wanna check this");
                return imgs.get("Blank");
            }
        }
        return imgs.get(name);
    }
    
}
