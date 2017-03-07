package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.CoverGear;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
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
import org.usfirst.frc.team3504.robot.commands.UncoverGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoBlueHopper;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoRedHopper;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private JoystickButton shootGear; 
	private JoystickButton shootKey; 

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
		driveByVision = new JoystickButton(gamePad, 1); 
		driveByVision.whileHeld(new DriveByVision());

		//shooter buttons
		shootGear = new JoystickButton(gamePad, 3);
		shootGear.whileHeld(new CombinedShootGear());
		shootKey = new JoystickButton(gamePad, 2);
		shootKey.whileHeld(new CombinedShootKey());
		shoot = new JoystickButton(gamePad, 4);
		shoot.whileHeld(new CombinedShoot());

		//Increment/decrement high shooter speed
		incrementHighShooter = new JoystickButton(gamePad, 6);
		incrementHighShooter.whenPressed(new IncrementHighShooter());
		decrementHighShooter = new JoystickButton(gamePad, 8);
		decrementHighShooter.whenPressed(new DecrementHighShooter());

		//Buttons for gear cover
		coverGear = new JoystickButton(gamePad, 5); //TODO: re-enable when the hardware is finished
		coverGear.whenPressed(new CoverGear());
		uncoverGear = new JoystickButton(gamePad, 7); 
		uncoverGear.whenPressed(new UncoverGear());

		//Climb
		climb = new JoystickButton(gamePad, 10); 
		climb.whileHeld(new Climb());
		unClimb = new JoystickButton(gamePad, 9); 
		unClimb.whileHeld(new UnClimb());

	}

	public void populateChooserMenu(SendableChooser<Command> chooser){
		chooser.addDefault("Do Nothing", new AutoDoNothing());
		chooser.addObject("Base Line", new DriveByDistance(20.0)); //inches TODO: change value
		chooser.addObject("Blue Alliance Hopper", new AutoBlueHopper()); //TODO: change name
		chooser.addObject("Red Alliance Hopper", new AutoRedHopper()); //TODO: change name
		chooser.addObject("Drive by Vision for gear", new DriveByVision());
		chooser.addObject("Auto RedLeftGear", new AutoGear("/home/lvuser/talonProfileLeftRedLeftGear.dat", "/home/lvuser/talonProfileRightRedLeftGear.dat"));
		chooser.addObject("Auto RedRightGear", new AutoGear("/home/lvuser/talonProfileLeftRedRightGear.dat", "/home/lvuser/talonProfileRightRedRightGear.dat"));
		chooser.addObject("Auto RedCenterGear", new AutoGear("/home/lvuser/talonProfileLeftRedCenterGear.dat", "/home/lvuser/talonProfileRightRedCenterGear.dat"));
		chooser.addObject("Auto BlueLeftGear", new AutoGear("/home/lvuser/talonProfileRightRedLeftGear.dat", "/home/lvuser/talonProfileLeftRedLeftGear.dat")); //blue autonomous with flipped left and right values should theoretically work, but TODO: test 
		chooser.addObject("Auto BlueRightGear", new AutoGear("/home/lvuser/talonProfileRightRedRightGear.dat", "/home/lvuser/talonProfileLeftRedRightGear.dat"));
		chooser.addObject("Auto BlueCenterGear", new AutoGear("/home/lvuser/talonProfileRightRedCenterGear.dat", "/home/lvuser/talonProfileLeftRedCenterGear.dat"));
		chooser.addObject("Motion Profile: RedGearBackToKey", new DriveByMotionProfile("/home/lvuser/talonProfileLeftRedGearBackToKey.dat", "/home/lvuser/talonProfileRightRedGearBackToKey.dat"));
		chooser.addObject("Drive by Distance", new DriveByDistance(75));
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
