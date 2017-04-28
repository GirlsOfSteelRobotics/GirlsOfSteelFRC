package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Capture;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Define the available values for the robot location UI on the dashboard
	// Each enum lists the possible choices for a radio button group
	public enum Sides {
		red, blue
	};

	public enum Lifts {
		boiler, center, loading
	};

	public enum Dists {
		close, medium, far
	};

	SendableChooser<Sides> side;
	SendableChooser<Lifts> lift;
	SendableChooser<Dists> dist;

	/**
	 * Construct the OI object. Creates the UI objects and posts them to the
	 * dashboard so they appear as soon as the program is running on the robot
	 */
	public OI() {
		// Build the location choosers, presenting a set of radio buttons
		side = new SendableChooser<>();
		side.addDefault("Red Side", Sides.red);
		side.addObject("Blue side", Sides.blue);
		SmartDashboard.putData("Side", side);

		lift = new SendableChooser<>();
		lift.addDefault("Boiler", Lifts.boiler);
		lift.addObject("Center", Lifts.center);
		lift.addObject("Loading Station", Lifts.loading);
		SmartDashboard.putData("Lift", lift);

		dist = new SendableChooser<>();
		dist.addDefault("Close", Dists.close);
		dist.addObject("Medium", Dists.medium);
		dist.addObject("Far", Dists.far);
		SmartDashboard.putData("Distance", dist);

		// Create a button that will start the Capture command
		SmartDashboard.putData("Capture Images", new Capture());

		// Initialize the progress bar to zero percent
		updateProgress(0);
	}

	/**
	 * Update the progress bar on the dashboard to this value
	 * 
	 * @param percent
	 *            0 for an empty bar, through 100 for a full bar
	 */
	public void updateProgress(int percent) {
		SmartDashboard.putNumber("Progress", percent);
	}

	/**
	 * Get the current selection of the Side radio buttons from the dashboard
	 * 
	 * @return The current selection as a string
	 */
	public String getSide() {
		return side.getSelected().toString();
	}

	/**
	 * Get the current selection of the Lift radio buttons from the dashboard
	 * 
	 * @return The current selection as a string
	 */
	public String getLift() {
		return lift.getSelected().toString();
	}

	/**
	 * Get the current selection of the Distance radio buttons from the
	 * dashboard
	 * 
	 * @return The current selection as a string
	 */
	public String getDist() {
		return dist.getSelected().toString();
	}
}
