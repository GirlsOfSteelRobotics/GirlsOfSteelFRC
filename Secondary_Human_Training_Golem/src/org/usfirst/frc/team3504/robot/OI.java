package org.usfirst.frc.team3504.robot;


import org.usfirst.frc.team3504.robot.commands.GearPistonOut;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.GearPistonIn;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	private JoystickButton shifterUp;
	private JoystickButton shifterDown;
	
	public OI()
	{
		//buttons for gears
		gearPistonOut = new JoystickButton(gamePad, 1); //TODO: set buttons
		gearPistonOut.whenPressed(new GearPistonOut());
		
		gearPistonIn = new JoystickButton(gamePad, 2); //TODO: set buttons
		gearPistonIn.whenPressed(new GearPistonIn());
		
		//buttons for shifters
		shifterUp = new JoystickButton(gamePad, 3); //TODO: set buttons
		shifterUp.whenPressed(new ShiftUp());
		
		shifterDown = new JoystickButton(gamePad, 4); //TODO: set buttons
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
}

