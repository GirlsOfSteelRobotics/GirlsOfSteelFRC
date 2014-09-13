package org.usfirst.frc3504.shifterbot;

import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// Another type of button you can create is a DigitalIOButton, which is
	// a button or switch hooked up to the cypress module. These are useful if
	// you want to build a customized operator interface.
	// Button button = new DigitalIOButton(1);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released  and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	public JoystickButton joystickButton3;
	public JoystickButton joystickButton2;
	public JoystickButton joystickButton6;
	public JoystickButton joystickButton7;
	public JoystickButton joystickButton11;
	public JoystickButton joystickButton10;
	public Joystick driveStick;

	public OI() {
		driveStick = new Joystick(1);

		joystickButton10 = new JoystickButton(driveStick, 10);
		joystickButton10.whileHeld(new AccessoryRightRev());
		joystickButton11 = new JoystickButton(driveStick, 11);
		joystickButton11.whileHeld(new AccessoryRightFwd());
		joystickButton7 = new JoystickButton(driveStick, 7);
		joystickButton7.whileHeld(new AccessoryLeftRev());
		joystickButton6 = new JoystickButton(driveStick, 6);
		joystickButton6.whileHeld(new AccessoryLeftFwd());
		joystickButton2 = new JoystickButton(driveStick, 2);
		joystickButton2.whenPressed(new ShiftDown());
		joystickButton3 = new JoystickButton(driveStick, 3);
		joystickButton3.whenPressed(new ShiftUp());

		// SmartDashboard Buttons
		SmartDashboard.putData("Autonomous Command", new AutonomousCommand());

		SmartDashboard.putData("DriveByJoystick", new DriveByJoystick());

		SmartDashboard.putData("Shift Up", new ShiftUp());

		SmartDashboard.putData("Shift Down", new ShiftDown());

		SmartDashboard.putData("Accessory Left Fwd", new AccessoryLeftFwd());

		SmartDashboard.putData("Accessory Left Rev", new AccessoryLeftRev());

		SmartDashboard.putData("Accessory Right Fwd", new AccessoryRightFwd());

		SmartDashboard.putData("Accessory Right Rev", new AccessoryRightRev());
	}

	public Joystick getDriveStick() {
		return driveStick;
	}
}
