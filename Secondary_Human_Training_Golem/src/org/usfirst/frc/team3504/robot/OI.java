package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.CoverGear;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.commands.IncrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;
import org.usfirst.frc.team3504.robot.commands.TurnToGear;
import org.usfirst.frc.team3504.robot.commands.UnClimb;
import org.usfirst.frc.team3504.robot.commands.UncoverGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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
	private JoystickButton turnLeftToGear;


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
		//driveByVision = new JoystickButton(gamePad, 1); 
		//driveByVision.whileHeld(new DriveByVision());

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
		
		//Turn to Gear
		turnLeftToGear = new JoystickButton(gamePad, 1);
		turnLeftToGear.whenPressed(new TurnToGear(TurnToGear.Direction.kLeft));
	}

	public void populateChooserMenu(SendableChooser<Command> chooser){
		chooser.addObject("Do Nothing", new AutoDoNothing());
		chooser.addDefault("Base Line", new DriveByDistance(112.0)); //inches TODO: change value
		chooser.addObject("Center Gear", new DriveByDistance(75.5));
		//chooser.addObject("Blue Alliance Hopper", new AutoBlueHopper()); //TODO: change name
		//chooser.addObject("Red Alliance Hopper", new AutoRedHopper()); //TODO: change name
		//chooser.addObject("Drive by Vision for gear", new DriveByVision());
		chooser.addObject("Red Loader Gear", new AutoGear(115.5, TurnToGear.Direction.kLeft));
		chooser.addObject("Red Boiler Gear", new AutoGear(115.5, TurnToGear.Direction.kRight));
		chooser.addObject("Red Center Gear", new DriveByVision());
		//chooser.addObject("Red MP: Loader Gear", new DriveByMotionProfile("/home/lvuser/leftLoaderGear.dat", "/home/lvuser/rightLoaderGear.dat"));
		//chooser.addObject("Red MP: Boiler Gear", new DriveByMotionProfile("/home/lvuser/leftBoilerGear.dat", "/home/lvuser/rightBoilerGear.dat"));
		//chooser.addObject("Red MP: Center Gear", new DriveByMotionProfile("/home/lvuser/leftCenterGear.dat", "/home/lvuser/rightCenterGear.dat"));
		chooser.addObject("Blue Loader Gear", new AutoGear(115.5, TurnToGear.Direction.kRight));
		chooser.addObject("Blue Boiler Gear", new AutoGear(115.5, TurnToGear.Direction.kLeft));
		chooser.addObject("Blue Center Gear", new DriveByVision());
		//chooser.addObject("Red MP: Back to Key", new DriveByMotionProfile("/home/lvuser/leftBackToKey.dat", "/home/lvuser/rightBackToKey.dat"));
		//chooser.addObject("Blue MP: Back to Key", new DriveByMotionProfile("/home/lvuser/rightBackToKey.dat", "/home/lvuser/leftBackToKey.dat"));
		//chooser.addObject("Blue MP: Loader Gear", new DriveByMotionProfile("/home/lvuser/rightLoaderGear.dat", "/home/lvuser/leftLoaderGear.dat"));
		//chooser.addObject("Blue MP: Center Gear", new DriveByMotionProfile("/home/lvuser/rightCenterGear.dat", "/home/lvuser/leftCenterGear.dat"));
		//chooser.addObject("Blue MP: Boiler Normal", new DriveByMotionProfile("/home/lvuser/rightBoilerNormal.dat", "/home/lvuser/leftBoilerNormal.dat"));
		//chooser.addObject("Blue MP: Boiler Corrected", new DriveByMotionProfile("/home/lvuser/rightBoilerCorrected.dat", "/home/lvuser/leftBoilerCorrected.dat"));
		//chooser.addObject("Drive by Distance 75in fwd", new DriveByDistance(75));
		//chooser.addObject("Drive by Distance 4in bkwd", new DriveByDistance(-4));
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
