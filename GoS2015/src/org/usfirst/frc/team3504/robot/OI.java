package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3504.robot.commands.AngleSuckerIn;
import org.usfirst.frc.team3504.robot.commands.AngleSuckerOut;
import org.usfirst.frc.team3504.robot.commands.CameraSwitch;
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
import org.usfirst.frc.team3504.robot.commands.StopCollection;
import org.usfirst.frc.team3504.robot.commands.StopSuckerAngle;
import org.usfirst.frc.team3504.robot.commands.autonomous.plow.*;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoSucker;

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
	
	
	//Auto Buttons
	private JoystickButton autoDriveForward;
	private JoystickButton autoDriveForwardUltra;
	private JoystickButton autoFirstPickup;
	private JoystickButton autoInterval;
	private JoystickButton autoLiftUp;
	private JoystickButton autoPlow;
	private JoystickButton autoSucker;
	private JoystickButton autoTurnLeft;
	private JoystickButton autoTurnLeftTimer;
	
	//Drive Buttons
	private JoystickButton driveForward;
	private JoystickButton driveBackward;
	private JoystickButton driveRight;
	private JoystickButton driveLeft;
	
	//Forklift Buttons
	private JoystickButton liftUp;
	private JoystickButton liftDown;
	
	//Sucker buttons
	private JoystickButton suckIn;
	private JoystickButton pushOut;
	private JoystickButton angleIn;
	private JoystickButton angleOut;
	private JoystickButton stopCollection;
	private JoystickButton stopSuckerAngle;
	
	//Door buttons
	private JoystickButton doorIn;
	private JoystickButton doorOut;
	
	private JoystickButton switchCamera;
	
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
		
		//Lifter buttons initialization
		liftUp = new JoystickButton(operatorJoystick, 5);	// FIXME: make sure this is for the correct Joystick and port
		liftDown = new JoystickButton(operatorJoystick, 6);	// FIXME: make sure this is for the correct Joystick and port
		
	//	liftUp.whenPressed(new LiftUp());
	//	liftDown.whenPressed(new LiftDown());
		
		//Sucker buttons initialization
		suckIn = new JoystickButton(operatorJoystick, 7);     		// FIXME: make sure this is for the correct Joystick and port
		pushOut = new JoystickButton(operatorJoystick, 8);  	  	// FIXME: make sure this is for the correct Joystick and port
		angleIn = new JoystickButton(operatorJoystick, 9);    		// FIXME: make sure this is for the correct Joystick and port
		angleOut = new JoystickButton(operatorJoystick, 10);  		// FIXME: make sure this is for the correct Joystick and port
		stopCollection = new JoystickButton(operatorJoystick, 11); 	// FIXME: make sure this is for the correct Joystick and port
		stopSuckerAngle = new JoystickButton(operatorJoystick, 12); //FIXME: make sure this is for the correct Joystick and port
		
		switchCamera = new JoystickButton(chassisJoystick, 7);
	
		//Autonomous 
		autoDriveForward = new JoystickButton(chassisJoystick, 9);
		autoFirstPickup = new JoystickButton(chassisJoystick, 10);
		autoSucker = new JoystickButton(chassisJoystick, 11);
		autoTurnLeft = new JoystickButton(chassisJoystick, 12);
		
		autoDriveForward.whenPressed(new AutoDriveForward());
		autoFirstPickup.whenPressed(new AutoFirstPickup());
		autoSucker.whenPressed(new AutoSucker()); 
		autoTurnLeft.whenPressed(new AutoTurnLeft());
		
		
		switchCamera.whenPressed (new CameraSwitch());
	//	suckIn.whenPressed (new CollectTote());
	//	pushOut.whenPressed (new ReleaseTote());
	//	angleIn.whenPressed (new AngleSuckerIn());
	//	angleOut.whenPressed (new AngleSuckerOut());
	//	stopCollection.whenPressed (new StopCollection());
	//	stopSuckerAngle.whenPressed(new StopSuckerAngle());
		
		//Door Buttons
		doorIn = new JoystickButton(chassisJoystick,7);
		doorOut = new JoystickButton(chassisJoystick,8);
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

