/**
 * Team Mexica Iteration 1
 * 
 * This class acts a container for the MapView and the InventoryStatsView.
 * It is what will actually be put directly into the frame.
 * 
 */

package view;

import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class GameView extends JPanel implements ViewPort {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5389302727930336098L;
	
	// the components that will get added to GameView
	private JPanel mapView;
	private JPanel invStatsView;

	/**
	 * Constructor. Sets up layout for the GameView
	 * 
	 * @param mapView
	 *            MapView to be added
	 * @param invStatsView
	 *            InventoryStatsView to be added
	 */
	public GameView(JPanel mapView, JPanel invStatsView) {
		this.setLayout(null);
		this.setSize(View.GAME_WIDTH, View.GAME_HEIGHT);
		this.mapView = mapView;
		this.invStatsView = invStatsView;
		this.add(this.mapView);
		mapView.setBounds(0, 0, View.GAME_HEIGHT, View.GAME_HEIGHT);
		this.add(this.invStatsView);
		invStatsView.setBounds(View.GAME_HEIGHT, 0, View.GAME_WIDTH
				- View.GAME_HEIGHT, View.GAME_HEIGHT);
	}

	/**
	 * Adds the KeyListener to the panel
	 * 
	 * @param k
	 *            KeyListener to be added to the panel
	 */
	public void addInputHandler(KeyListener k) {
		this.addKeyListener(k);
	}

	/**
	 * calls update on its constituent components
	 */
	public void update() {
		((ViewPort) mapView).update();
		((ViewPort) invStatsView).update();
	}

}
