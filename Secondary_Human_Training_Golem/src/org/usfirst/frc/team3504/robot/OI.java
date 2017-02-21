package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.commands.IncrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.LoadBall;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.Shoot;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;
import org.usfirst.frc.team3504.robot.commands.UnClimb;

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
	private JoystickButton unClimb; 
	
	private JoystickButton incrementHighShooter;
	private JoystickButton decrementHighShooter;
	
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
		shoot.whileHeld(new CombinedShoot());
		
		//Increment/decrement high shooter speed
		incrementHighShooter = new JoystickButton(gamePad, 6);
		incrementHighShooter.whenPressed(new IncrementHighShooter());
		
		decrementHighShooter = new JoystickButton(gamePad, 8);
		decrementHighShooter.whenPressed(new DecrementHighShooter());
		
		/* Buttons for gear cover
		coverGear = new JoystickButton(gamePad, 5); //TODO: get number
		coverGear.whenPressed(new CoverGear());
		uncoverGear = new JoystickButton(gamePad, 6); //TODO: get number
		uncoverGear.whenPressed(new UncoverGear());
		*/
		
		//Climb
		climb = new JoystickButton(gamePad, 7); 
		climb.whileHeld(new Climb());
		unClimb = new JoystickButton(gamePad, 10); 
		unClimb.whileHeld(new UnClimb());
		
		motionProfile = new JoystickButton(gamePad, 5); //was 8, find value later
		motionProfile.whenPressed(new DriveByMotionProfile("/home/lvuser/talonProfileLeftWM03.dat", "/home/lvuser/talonProfileRightWM03.dat"));

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
	
	public double getDrivingJoystickX() {  //keep the redundancy, it breaks if removed
		if (driveDirection == DriveDirection.kFWD){
			return drivingStickForward.getX();
		}
		else {
			return drivingStickBackward.getX(); 
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
