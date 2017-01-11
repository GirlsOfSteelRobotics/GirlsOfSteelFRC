package org.usfirst.frc.team3504.robot;


import org.usfirst.frc.team3504.robot.commands.GearPistonOut;
import org.usfirst.frc.team3504.robot.commands.GearPistonIn;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3504.robot.OI.DriveDirection;

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
	
	public enum DriveDirection {kFWD, kREV}; 
	private Joystick drivingStickForward = new Joystick(0); //TODO:fix numbers
	private Joystick drivingStickBackward = new Joystick(1); //TODO:fix numbers
	
	private Joystick gamePad = new Joystick(2); //TODO:fix numbers
	
	private DriveDirection driveDirection = DriveDirection.kFWD; 
	
	private JoystickButton gearPistonOut;
	private JoystickButton gearPistonIn;
	
	public OI()
	{
		gearPistonOut = new JoystickButton(gamePad, 1);
		gearPistonOut.whenPressed(new GearPistonOut());
		
		gearPistonIn = new JoystickButton(gamePad, 1);
		gearPistonIn.whenPressed(new GearPistonIn());
	}

	public double getDrivingJoystickY() {
		// TODO Auto-generated method stub
		if (driveDirection == DriveDirection.kFWD){
			return drivingStickForward.getY();
		}
		else {
			return -drivingStickBackward.getY(); 
		}
	}
	
	public double getDrivingJoystickX()
	{
		if (driveDirection == DriveDirection.kFWD){
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
}

