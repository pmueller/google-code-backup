import controller.*;
import model.*;
import view.*;

public class RunGame {

	static final long timePerFrame = (long) (1000 * (1.0 / 30.0));

	public static void main(String[] args) {
		System.out.println("Game has started! Hello World!");

		Model model = new Model();
		View view = new View(model);
		Controller controller = new Controller(model, view);

		while (true) {
			// get time before update call
			long timeBeforeUpdate = System.currentTimeMillis();

			// call update
			model.update();
			view.update();
			controller.update();

			// get time after update call
			long timeAfterUpdate = System.currentTimeMillis();

			// calculate duration of update() call
			long duration = timeAfterUpdate - timeBeforeUpdate;

			// if the update() call ran faster than the expected time for one
			// frame, sleep this Thread for the remaining milliseconds
			if (duration < timePerFrame) {
				// calculate the time to sleep this Thread
				long sleepTime = timePerFrame - duration;

				try {
					// sleep the Thread
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
