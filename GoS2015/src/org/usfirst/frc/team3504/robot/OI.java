package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3504.robot.commands.AngleSuckerIn;
import org.usfirst.frc.team3504.robot.commands.AngleSuckerOut;
import org.usfirst.frc.team3504.robot.commands.CollectTote;
import org.usfirst.frc.team3504.robot.commands.DriveBackward;
import org.usfirst.frc.team3504.robot.commands.DriveForward;
import org.usfirst.frc.team3504.robot.commands.ExampleCommand;
import org.usfirst.frc.team3504.robot.commands.ReleaseTote;
import org.usfirst.frc.team3504.robot.commands.DriveLeft;
import org.usfirst.frc.team3504.robot.commands.DriveRight;
import org.usfirst.frc.team3504.robot.commands.LiftDown;
import org.usfirst.frc.team3504.robot.commands.LiftUp;
//import org.usfirst.frc.team3504.robot.commands.ExampleCommand;

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
	
	private JoystickButton driveForward;
	private JoystickButton driveBackward;
	private JoystickButton driveRight;
	private JoystickButton driveLeft;
	
	private JoystickButton liftUp;
	private JoystickButton liftDown;
	//Put Sucker buttons here
	
	private JoystickButton suckIn;
	private JoystickButton pushOut;
	private JoystickButton angleIn;
	private JoystickButton angleOut;
	
	//Put Manipulator buttons here
	
	public OI()
	{
		operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
		chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);
		driveForward = new JoystickButton(chassisJoystick, 5);
		driveBackward = new JoystickButton(chassisJoystick, 6);
		driveRight = new JoystickButton(chassisJoystick, 4);
		driveLeft = new JoystickButton(chassisJoystick, 3);
		liftUp = new JoystickButton(operatorJoystick, 5);
		liftDown = new JoystickButton(operatorJoystick, 6);
		
		
		liftUp.whenPressed(new LiftUp());
		liftDown.whenPressed(new LiftDown());
		driveForward.whenPressed(new DriveForward());
		driveBackward.whenPressed(new DriveBackward());
		
		driveRight.whileHeld(new DriveRight());
		driveLeft.whileHeld(new DriveLeft());
		
		//sucker buttons are being initialized here
		suckIn = new JoystickButton(operatorJoystick, 7);     // FIXME: make sure this is for the correct Joystick and port
		pushOut = new JoystickButton(operatorJoystick, 8);    // FIXME: make sure this is for the correct Joystick and port
		angleIn = new JoystickButton(operatorJoystick, 9);    // FIXME: make sure this is for the correct Joystick and port
		angleOut = new JoystickButton(operatorJoystick, 10);  // FIXME: make sure this is for the correct Joystick and port
		
		suckIn.whenPressed (new CollectTote());
		pushOut.whenPressed (new ReleaseTote());
		angleIn.whenPressed (new AngleSuckerIn());
		angleOut.whenPressed (new AngleSuckerOut());
		
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

