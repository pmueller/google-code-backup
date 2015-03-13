/**
 * Team Mexica Iteration 1
 * 
 * An interface that makes it so all panels used in the system must 
 * implement an update() method which allows us to keep the panels 
 * updated in a synchronous fashion.
 * 
 */
package view;

public interface ViewPort {
	/**
	 * to be implemented by the main parts of the view
	 */
	public void update();
}
