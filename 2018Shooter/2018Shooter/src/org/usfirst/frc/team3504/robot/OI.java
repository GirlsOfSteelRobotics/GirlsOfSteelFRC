package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
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
	
	public enum JoystickScaling {
		linear, deadband, quadratic
	};
	
	
	//Drive Styles
	//gamepad: tank, split arcade
	//joystick: tank, one stick arcade
	public enum DriveStyle {
		oneStickArcade, gamePadArcade, twoStickTank, gamePadTank, droperation
	}; 
	
	public static DriveStyle driveStyle = DriveStyle.oneStickArcade;

	private Joystick drivingStickForward;
	private Joystick drivingStickBackward;

	private Joystick operatingGamePad;

	private DriveDirection driveDirection = DriveDirection.kFWD;
	
	private JoystickScaling joystickScale = JoystickScaling.linear;
	private static double DEADBAND = 0.3; //TODO: find a good value

	private JoystickButton switchToForward;
	private JoystickButton switchToBackward;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;

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
	}

	public double getDrivingJoystickY() {
		double unscaledValue;
		
		if (driveDirection == DriveDirection.kFWD) {
			unscaledValue = drivingStickForward.getY();
		} 
		else {
			unscaledValue = -drivingStickBackward.getY();
		}
	
		return getScaledJoystickValue(unscaledValue);
	}
	
	public double getDrivingJoystickX() {
		double unscaledValue;
		
		if (driveDirection == DriveDirection.kFWD) {
			unscaledValue = drivingStickForward.getX();
		} 
		else {
			unscaledValue =  -drivingStickBackward.getX();
		}
		
		return getScaledJoystickValue(unscaledValue);
	}

	
	public double getScaledJoystickValue(double input)
	{
		double output = 0;
		
		if (joystickScale == JoystickScaling.linear)
		{
			output = input;
		}
		else if (joystickScale == JoystickScaling.deadband)
		{
			if (Math.abs(input) < DEADBAND)
				output = 0;
			else
			{
				if (input > 0) output = input - DEADBAND;
				else output = input + DEADBAND;
			}
		}
		else if (joystickScale == JoystickScaling.quadratic)
		{
			if (input > 0) output = Math.pow(input, 2);
			else output = -1 * Math.pow(input, 2);
		}
		
		return output;
	}

	public void setDriveDirection(DriveDirection driveDirection) {
		this.driveDirection = driveDirection;
		System.out.println("Drive direction set to: " + driveDirection);
	}
	
	public void setJoystickScale(JoystickScaling joystickScale) {
		this.joystickScale = joystickScale;
		System.out.println("Joystick direction set to: " + joystickScale);
	}

	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV);
	}
}
