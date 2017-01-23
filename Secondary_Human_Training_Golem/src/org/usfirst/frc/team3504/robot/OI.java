package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.DriveBackwards;
import org.usfirst.frc.team3504.robot.commands.DriveForward;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3504.robot.OI.DriveDirection;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
   
	public enum DriveDirection {kFWD, kREV}; 
	private Joystick drivingStickForward = new Joystick(0); //TODO:fix numbers
	private Joystick drivingStickBackward = new Joystick(1); //TODO:fix numbers
	
	private Joystick gamePad = new Joystick(2); //TODO:fix numbers
	
	private DriveDirection driveDirection = DriveDirection.kFWD; 
	
	private JoystickButton switchToForward; 
	private JoystickButton switchToBackward; 
	
	private JoystickButton shifterUp;
	private JoystickButton shifterDown; 
	
	public OI() {
		//buttons to switch drive joystick
		switchToForward = new JoystickButton(drivingStickForward, 1); 
		switchToForward.whenPressed(new DriveForward()); 
		
		switchToBackward = new JoystickButton(drivingStickBackward, 1);
		switchToBackward.whenPressed(new DriveBackwards());
		
				
		//buttons for shifters
		shifterUp = new JoystickButton(drivingStickForward, 3);
		shifterUp.whenPressed(new ShiftUp());
				
		shifterDown = new JoystickButton(drivingStickForward, 2);
		shifterDown.whenPressed(new ShiftDown());
		
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
	
	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV); 
	}
	
}

