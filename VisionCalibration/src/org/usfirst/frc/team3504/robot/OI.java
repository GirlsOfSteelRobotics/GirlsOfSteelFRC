package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.commands.Capture;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public enum Sides {red, blue};
	public enum Lifts {boiler, center, loading};
	public enum Dists {close, medium, far};
	
	SendableChooser<Sides> side;
	SendableChooser<Lifts> lift;
	SendableChooser<Dists> dist;

	public OI() {
		// Build the location choosers and input fields
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
	}

	public String getSide() {
		return side.getSelected().toString();
	}

	public String getLift() {
		return lift.getSelected().toString();
	}

	public String getDist() {
		return dist.getSelected().toString();
	}
}
