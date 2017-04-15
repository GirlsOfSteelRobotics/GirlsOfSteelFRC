package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Agitate;
import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.CreateMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
//import org.usfirst.frc.team3504.robot.commands.FlapDown;
//import org.usfirst.frc.team3504.robot.commands.FlapUp;
import org.usfirst.frc.team3504.robot.commands.IncrementHighShooter;
//import org.usfirst.frc.team3504.robot.commands.PivotDown;
//import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;
import org.usfirst.frc.team3504.robot.commands.TurnToGear;
import org.usfirst.frc.team3504.robot.commands.UnClimb;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoCenterGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoShooter;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public enum DriveDirection {
		kFWD, kREV
	};
	
	//IF ROZIE IS GAMEPAD; TURN TRUE. ELSE; TURN FALSE.
		boolean rozieDrive = false;
		
		//Rozie's Nonsense: Project Droperation.
		private Joystick roziePad = new Joystick(5);

	private Joystick drivingStickForward;
	private Joystick drivingStickBackward;
	private Joystick gamePad;
	private Joystick autonSelector;

	private DriveDirection driveDirection = DriveDirection.kFWD;

	private JoystickButton switchToForward;
	private JoystickButton switchToBackward;

	private JoystickButton shifterUp;
	private JoystickButton shifterDown;

	private JoystickButton shoot;
	private JoystickButton shootGear;
	private JoystickButton shootKey;

	private JoystickButton agitate;

	private JoystickButton climb;
	private JoystickButton unClimb;

	private JoystickButton incrementHighShooter;
	private JoystickButton decrementHighShooter;

	
	

	//ROZIE DECLARATIONS
	/**
	 * ROZIE 
	 */
	
	private JoystickButton rozieShiftDownButton;
	private JoystickButton rozieFlapUp;
	private JoystickButton rozieFlapDown;
	private JoystickButton roziePivotUp;
	private JoystickButton roziePivotDown;
	
	
	
	private JoystickButton driveByVision;

	public OI() {
		// Define the joysticks
		drivingStickForward = new Joystick(0);
		drivingStickBackward = new Joystick(1);
		gamePad = new Joystick(2);
		autonSelector = new Joystick(3);

		// DRIVING BUTTONS

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

		// OPERATOR BUTTONS

		// Drive by vision (plus back up a few inches when done)
		driveByVision = new JoystickButton(gamePad, 1);
		driveByVision.whenPressed(new CreateMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat"));

		// Shooter buttons
		shootKey = new JoystickButton(gamePad, 2);
		shootKey.whileHeld(new CombinedShootKey());
		shootGear = new JoystickButton(gamePad, 3);
		shootGear.whileHeld(new CombinedShootGear());
		shoot = new JoystickButton(gamePad, 4);
		shoot.whileHeld(new CombinedShoot());

		// Button for loader agitator
		agitate = new JoystickButton(gamePad, 5);
		agitate.whileHeld(new Agitate());

		// Increment/decrement shooter speed
		incrementHighShooter = new JoystickButton(gamePad, 6);
		incrementHighShooter.whenPressed(new IncrementHighShooter());
		decrementHighShooter = new JoystickButton(gamePad, 8);
		decrementHighShooter.whenPressed(new DecrementHighShooter());

		// Climber
		unClimb = new JoystickButton(gamePad, 9);
		unClimb.whileHeld(new UnClimb());
		climb = new JoystickButton(gamePad, 10);
		climb.whileHeld(new Climb());
		
		//ROZIE STUFF!!!!
				if (rozieDrive == true){
					rozieShiftDownButton = new JoystickButton(roziePad, 4); //Y
					rozieShiftDownButton.whenPressed(new ShiftDown());
					rozieFlapUp = new JoystickButton(roziePad, 8);//START
					rozieFlapUp.whileHeld(new Climb()); 
					rozieFlapDown = new JoystickButton(roziePad, 7);  // BACK
					rozieFlapDown.whileHeld(new UnClimb());
					roziePivotUp = new JoystickButton(roziePad, 3); // X
					roziePivotUp.whileHeld(new CombinedShoot());
					roziePivotDown = new JoystickButton(roziePad, 2); // B
					roziePivotDown.whileHeld(new ShiftUp());
				}
				
	}

	
	
	public Command getAutonCommand() {
		switch (getAutonSelector()) {
		case 0:
			return new DriveByDistance(75.5, Shifters.Speed.kHigh);
		case 1:
			return new DriveByDistance(112.0, Shifters.Speed.kHigh);
		case 2:
			return new AutoCenterGear();
		case 3:
			// red boiler
			return new AutoGear(75.5, TurnToGear.Direction.kLeft);
		case 4:
			// red loader
			return new AutoGear(70.5, TurnToGear.Direction.kRight);
		case 5:
			// blue boiler
			return new AutoGear(75.5, TurnToGear.Direction.kRight);
		case 6:
			// blue loader
			return new AutoGear(70.5, TurnToGear.Direction.kLeft);
		case 7:
			return new AutoShooter();
		case 8:
			return new DriveByDistance(-3, Shifters.Speed.kLow);
		case 9:
			return new DriveByMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat");
		case 15:
			return new AutoDoNothing();
		default:
			return new DriveByDistance(75.5, Shifters.Speed.kLow);
		}
	}

	public double getDrivingJoystickY() {
		if (rozieDrive == true){
			return roziePad.getY();
		}
		else if (driveDirection == DriveDirection.kFWD) {
			return drivingStickForward.getY();
		} else {
			return -drivingStickBackward.getY();
		}
	}

	public double getDrivingJoystickX() { // keep the redundancy, it breaks if
		if (rozieDrive == true){
			return roziePad.getX();
		}									// removed
		else if (driveDirection == DriveDirection.kFWD) {
			return drivingStickForward.getX();
		} else {
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

	/**
	 * Get Autonomous Mode Selector
	 * 
	 * Read a physical pushbutton switch attached to a USB gamepad controller,
	 * returning an integer that matches the current readout of the switch.
	 * 
	 * @return int ranging 0-15
	 */
	public int getAutonSelector() {
		// Each of the four "button" inputs corresponds to a bit of a binary
		// number encoding the current selection. To simplify wiring, buttons
		// 2-5 were used.
		int value = 1 * (autonSelector.getRawButton(2) ? 1 : 0)
				+ 2 * (autonSelector.getRawButton(3) ? 1 : 0)
				+ 4 * (autonSelector.getRawButton(4) ? 1 : 0)
				+ 8 * (autonSelector.getRawButton(5) ? 1 : 0);
		System.out.println("Auto Selector Number: " + value);
		return value;
	}
}
