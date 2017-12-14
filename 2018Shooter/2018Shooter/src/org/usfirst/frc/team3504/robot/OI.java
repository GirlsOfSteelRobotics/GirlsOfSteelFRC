package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.Shoot;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public enum DriveDirection {
		kFWD, kREV
	};


	private Joystick drivingStickForward;
	private Joystick drivingStickBackward;

	private Joystick operatingGamePad;

	private DriveDirection driveDirection = DriveDirection.kFWD;
	

	private JoystickButton switchToForward;
	private JoystickButton switchToBackward;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;
	
	private JoystickButton shoot;

	public OI() {
		// Define the joysticks
		drivingStickForward = new Joystick(1);
		drivingStickBackward = new Joystick(2);
		operatingGamePad = new Joystick(0);
		
		//DRIVER BUTTONS
		// Button to change between drive joysticks on trigger of both joysticks
		switchToForward = new JoystickButton(drivingStickForward, 1);
		switchToBackward = new JoystickButton(drivingStickBackward, 1); 
		// Buttons for shifters copied to both joysticks
		shifterDown = new JoystickButton(drivingStickForward, 2);
		shifterUp = new JoystickButton(drivingStickForward, 3);
		
		shoot = new JoystickButton(operatingGamePad, 3); //B
		
		// BACKWARDS BUTTONS
		if (drivingStickBackward.getName() != "") {
			//DRIVER BUTTONS
			// Button to change between drive joysticks on trigger
			switchToBackward = new JoystickButton(drivingStickBackward, 1);
			// Buttons for shifters copied to both joysticks
			shifterDown = new JoystickButton(drivingStickBackward, 2);
			shifterUp = new JoystickButton(drivingStickBackward, 3);
		}
		
		// DRIVING BUTTONS
		// Button to change between drive joysticks on trigger of both joysticks
		switchToForward.whenPressed(new SwitchForward());
		switchToBackward.whenPressed(new SwitchBackward());
		// Buttons for shifters
		shifterDown.whenPressed(new ShiftDown());
		shifterUp.whenPressed(new ShiftUp());	
		
		shoot.whenPressed(new Shoot(500));
	}

	public double getDrivingJoystickY() {
		if (driveDirection == DriveDirection.kFWD) {
			return drivingStickForward.getY();
		} 
		else {
			return -drivingStickBackward.getY();
		}
	}
	
	public double getDrivingJoystickX() {

		if (driveDirection == DriveDirection.kFWD) {
			return drivingStickForward.getX();
		} 
		else {
			return -drivingStickBackward.getX();
		}
	}



	public void setDriveDirection(DriveDirection driveDirection) {
		this.driveDirection = driveDirection;
		System.out.println("Drive direction set to: " + driveDirection);
	}

	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV);
	}
}
