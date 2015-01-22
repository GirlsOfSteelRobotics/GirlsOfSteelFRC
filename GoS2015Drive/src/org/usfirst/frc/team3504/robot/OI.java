package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.DriveBackward;
import org.usfirst.frc.team3504.robot.commands.DriveForward;
import org.usfirst.frc.team3504.robot.commands.DriveLeft;
import org.usfirst.frc.team3504.robot.commands.DriveRight;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	private Joystick operatorJoystick;
	private Joystick chassisJoystick;
	
	//Drive Buttons
	private JoystickButton driveForward;
	private JoystickButton driveBackward;
	private JoystickButton driveRight;
	private JoystickButton driveLeft;
	
	/*
	 * Add pusher buttons
	 * Add Triangle pegs buttons (fingers)
	 */
	
	public OI()
	{
		operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
		chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);
				
		//Drive buttons initialization
		driveForward = new JoystickButton(chassisJoystick, 5); 	// FIXME: fix port
		driveBackward = new JoystickButton(chassisJoystick, 6); // FIXME: fix port
		driveRight = new JoystickButton(chassisJoystick, 4); 	// FIXME: fix port
		driveLeft = new JoystickButton(chassisJoystick, 3); 	// FIXME: fix port
		
		driveForward.whileHeld(new DriveForward());
		driveBackward.whileHeld(new DriveBackward());
		driveRight.whileHeld(new DriveRight());
		driveLeft.whileHeld(new DriveLeft());
	}
	
	public Joystick getOperatorJoystick()
	{
		return operatorJoystick;
	}
	
	public Joystick getChassisJoystick()
	{
		return chassisJoystick;
	}
}

