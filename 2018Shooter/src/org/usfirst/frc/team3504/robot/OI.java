package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.Shoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick drivingStick;
	private Joystick operatingGamePad;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;
	
	private JoystickButton shoot;

	public OI() {
		// Define the joysticks
		drivingStick = new Joystick(1);
		operatingGamePad = new Joystick(0);
		
		//DRIVER BUTTONS
		// Buttons for shifters copied to both joysticks
		shifterDown = new JoystickButton(drivingStick, 2);
		shifterUp = new JoystickButton(drivingStick, 3);
		
		shoot = new JoystickButton(operatingGamePad, 3); //B
		
		// DRIVING BUTTONS
		// Buttons for shifters
		shifterDown.whenPressed(new ShiftDown());
		shifterUp.whenPressed(new ShiftUp());	
		
		shoot.whileHeld(new Shoot());
	}

	public double getDrivingJoystickY() {
		return drivingStick.getY();
	}
	
	public double getDrivingJoystickX() {
		return drivingStick.getX();
	}
}
