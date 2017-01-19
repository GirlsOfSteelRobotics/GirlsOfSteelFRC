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
		
		gearPistonIn = new JoystickButton(gamePad, 2);
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

