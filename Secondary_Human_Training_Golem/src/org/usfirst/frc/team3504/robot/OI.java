package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.commands.LoadBall;
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
   
	public enum DriveDirection {kFWD, kREV}; 
	private Joystick drivingStickForward;
	private Joystick drivingStickBackward;
	private Joystick gamePad;
	
	private DriveDirection driveDirection = DriveDirection.kFWD; 
	
	private JoystickButton switchToForward; 
	private JoystickButton switchToBackward; 
	
	private JoystickButton shifterUp;
	private JoystickButton shifterDown; 
	
	private JoystickButton shoot; 
	
	private JoystickButton coverGear;
	private JoystickButton uncoverGear;
	
	private JoystickButton loadBall; 
	
	private JoystickButton climb; 
	
	public JoystickButton motionProfile;
	
	private JoystickButton driveByVision;
	
	public OI() {
		// Define the joysticks
		drivingStickForward = new Joystick(0);
		drivingStickBackward = new Joystick(1);
		gamePad = new Joystick(2);
		
		// Button to change between drive joysticks on trigger of both joysticks
		switchToForward = new JoystickButton(drivingStickForward, 1); 
		switchToForward.whenPressed(new SwitchForward()); 
		
		switchToBackward = new JoystickButton(drivingStickBackward, 1);
		switchToBackward.whenPressed(new SwitchBackward());
			
		// Buttons for shifters copied to both joysticks
		shifterDown = new JoystickButton(drivingStickForward, 2);
		shifterDown.whenPressed(new ShiftDown());
		shifterDown = new JoystickButton(drivingStickBackward, 2);
		shifterDown.whenPressed(new ShiftDown());

		shifterUp = new JoystickButton(drivingStickForward, 3);
		shifterUp.whenPressed(new ShiftUp());
		shifterUp = new JoystickButton(drivingStickBackward, 3);
		shifterUp.whenPressed(new ShiftUp());
		
		//operator buttons
		//vision
		driveByVision = new JoystickButton(gamePad, 9); 
		driveByVision.whileHeld(new DriveByVision());
		
		//shooter buttons
		loadBall = new JoystickButton(gamePad, 2);
		loadBall.whileHeld(new LoadBall());
		shoot = new JoystickButton(gamePad, 3);
		shoot.whileHeld(new Shoot());
		
		//Climb
		climb = new JoystickButton(gamePad, 7); 
		climb.whileHeld(new Climb());
		
		motionProfile = new JoystickButton(gamePad, 8);
		motionProfile.whenPressed(new DriveByMotionProfile("/home/lvuser/talonProfileLeft.csv", "/home/lvuser/talonProfileRight.csv"));

		motionProfile = new JoystickButton(gamePad, 9);
		motionProfile.whenPressed(new DriveByVision());
	}

	public double getDrivingJoystickY() {
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
	
	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV); 
	}
	
}
