package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.Agitate;
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
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoCenterGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public enum DriveDirection {kFWD, kREV}; 
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
		autonSelector = new Joystick(3); 

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
		driveByVision.whenPressed(new AutoCenterGear());

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
		agitate = new JoystickButton(gamePad, 5); //TODO: re-enable when the hardware is finished
		agitate.whileHeld(new Agitate());

		//Climb
		climb = new JoystickButton(gamePad, 10); 
		climb.whileHeld(new Climb());
		unClimb = new JoystickButton(gamePad, 9); 
		unClimb.whileHeld(new UnClimb());
		
		//Turn to Gear
		//turnLeftToGear = new JoystickButton(gamePad, 1);
		//turnLeftToGear.whenPressed(new TurnToGear(TurnToGear.Direction.kRight));
	}

	
	public Command getAutonCommand() {
		  switch (getAutonSelector()) {
		    case 0: return new DriveByDistance (75.5);
		    case 1: return new DriveByDistance(112.0);
		    case 2: return new AutoCenterGear();
		    case 3: return new AutoGear (75.5, TurnToGear.Direction.kLeft); //red boiler
		    case 4: return new AutoGear(70.5, TurnToGear.Direction.kRight); //red loader
		    case 5: return new AutoGear (75.5, TurnToGear.Direction.kRight); //blue boiler
		    case 6: return new AutoGear (70.5, TurnToGear.Direction.kLeft);  //blue loader
		    case 7: return new AutoShooter();
		    case 8: return new DriveByDistance(-3);
		    case 15: return new AutoDoNothing(); 
		   default: return new DriveByDistance (75.5);
		}
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

	/** Get Autonomous Mode Selector
	 * 
	 * Read a physical pushbutton switch attached to a USB gamepad controller,
	 * returning an integer that matches the current readout of the switch.
	 * @return int ranging 0-15
	 */
	public int getAutonSelector() {
		// Each of the four "button" inputs corresponds to a bit of a binary number
		// encoding the current selection. To simplify wiring, buttons 2-5 were used.
		int value = 
				1 * (autonSelector.getRawButton(2) ? 1 : 0) +
				2 * (autonSelector.getRawButton(3) ? 1 : 0) +
				4 * (autonSelector.getRawButton(4) ? 1 : 0) +
				8 *	(autonSelector.getRawButton(5) ? 1 : 0);
		System.out.println("Auto Selector Number: " + value);
		return value;
	}
}
