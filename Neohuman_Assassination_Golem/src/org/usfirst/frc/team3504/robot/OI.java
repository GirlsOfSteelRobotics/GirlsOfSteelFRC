package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.*;
import org.usfirst.frc.team3504.robot.commands.buttons.*;
import org.usfirst.frc.team3504.robot.commands.camera.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public enum DriveDirection {kFWD, kREV}; 

	private Joystick drivingStickForward = new Joystick(0);
	private Joystick drivingStickBackward = new Joystick(1); 
	// The button board gets plugged into USB and acts like a Joystick
	private Joystick gamePad = new Joystick(2); 
	private Joystick buttonBoard = new Joystick(3); 
	// The autonomous command selector is uses buttons 2-5
	private Joystick autonSelector = new Joystick(4);

	//JOYSTICK BUTTONS
	private JoystickButton shiftUpButton;
	private JoystickButton shiftDownButton;

	private JoystickButton shiftUpButton2; //for backwards joystick
	private JoystickButton shiftDownButton2; //for backwards joystick

	private DriveDirection driveDirection = DriveDirection.kFWD; 

	private JoystickButton switchCam;
	private JoystickButton switchCam2; //for backwards joystick

	private JoystickButton switchToForward; 
	private JoystickButton switchToBackward; 

	//button board
	private JoystickButton collectBallButtonBoard;
	private JoystickButton releaseBallButtonBoard;
	private JoystickButton flapUpButtonBoard;
	private JoystickButton flapDownButtonBoard;
	private JoystickButton pivotUpButtonBoard;
	private JoystickButton pivotDownButtonBoard; 
	private JoystickButton shootBallButtonBoard;
	private JoystickButton shooterPistonsOutButtonBoard;
	private JoystickButton shooterPistonsInButtonBoard;
	private JoystickButton rollersInButtonBoard;
	private	JoystickButton rollersOutButtonBoard;
	
	//game pad 
	private JoystickButton collectBallButton;
	private JoystickButton releaseBallButton;
	private JoystickButton flapUp;
	private JoystickButton flapDown;
	private JoystickButton flapUpRocker;
	private JoystickButton flapDownRocker;
	private JoystickButton pivotUp;
	private JoystickButton pivotDown;
	private JoystickButton pivotMiddle;
	private JoystickButton testDesiredRotationAngle;  //for NavBoard
	private JoystickButton resetGyro;
	private JoystickButton shootBall;
	private JoystickButton shooterPistonsOut;
	private JoystickButton shooterPistonsIn;
	private JoystickButton resetEncoders;
	private JoystickButton shooterStop;
	
	
	private static final int AXIS_DPAD = 6;

	//Flap: Rocker (2 buttons) + 2 buttons, Pivot: 3 buttons, Claw: 2 Buttons, Other: 3 Buttons (defenses & scoring), Shooter: 2 buttons - total 12 buttons + rocker

	public OI() {
		
		//DriveStick Buttons
		
		//shiftUpButton = new JoystickButton(drivingStickForward, 4);
		//shiftUpButton.whenPressed(new ShiftUp());
		shiftDownButton = new JoystickButton(drivingStickForward, 3);
		shiftDownButton.whenPressed(new ShiftDown());

		//shiftUpButton2 = new JoystickButton (drivingStickBackward, 4);
		//shiftUpButton2.whenPressed (new ShiftUp());
		shiftDownButton2 = new JoystickButton (drivingStickBackward, 3);
		shiftDownButton2.whenPressed(new ShiftDown());

		switchCam = new JoystickButton(drivingStickForward, 10);
		switchCam.whenPressed(new SwitchCam());

		switchCam2 = new JoystickButton(drivingStickBackward, 10);
		switchCam2.whenPressed(new SwitchCam());

		switchToForward = new JoystickButton(drivingStickForward, 1); 
		switchToForward.whenPressed(new SwitchToForward()); 

		
		switchToBackward = new JoystickButton(drivingStickBackward, 1);
		switchToBackward.whenPressed(new SwitchToBackward());
		
		// Button board buttons
		if (buttonBoard.getButtonCount() > 0){
		shooterPistonsOut = new JoystickButton(buttonBoard, 3);
		shooterPistonsOut.whenPressed(new ShooterPistonsOut());
		shooterPistonsIn = new JoystickButton(buttonBoard, 4);
		shooterPistonsIn.whenPressed(new ShooterPistonsIn());
		shootBallButtonBoard = new JoystickButton(buttonBoard, 6); 
		shootBallButtonBoard.whenPressed(new ShootBall()); 
		pivotUpButtonBoard = new JoystickButton(buttonBoard, 7);
		pivotUpButtonBoard.whileHeld(new PivotUp());
		collectBallButtonBoard = new JoystickButton(buttonBoard, 8); 
		collectBallButtonBoard.whileHeld(new CollectBall());
		releaseBallButtonBoard = new JoystickButton(buttonBoard, 9); 
		releaseBallButtonBoard.whileHeld(new ReleaseBall());
		pivotDownButtonBoard = new JoystickButton(buttonBoard, 10);
		pivotDownButtonBoard.whileHeld(new PivotDown());
		flapUpButtonBoard = new JoystickButton(buttonBoard, 11);
		flapUpButtonBoard.whileHeld(new FlapUp());
		flapDownButtonBoard = new JoystickButton(buttonBoard, 12); 
		flapDownButtonBoard.whileHeld(new FlapDown());
		}
		
		//GamePad Buttons
		
		// these work for both claw and shooter
		collectBallButton = new JoystickButton(gamePad, 5);
		collectBallButton.whileHeld(new CollectBall());
		releaseBallButton = new JoystickButton(gamePad, 6);
		releaseBallButton.whileHeld(new ReleaseBall());
		shooterStop = new JoystickButton(gamePad, 1);
		shooterStop.whenPressed(new StopShooterWheels());
		

		if(!RobotMap.USING_CLAW) {
			// Put any claw-specific button assignments in here
		}

		//flap: rocker = drivers want to use to control movement of flap at full speed, w/o rocker goes until limit switch

		flapUp = new JoystickButton(gamePad, 8); //TODO: change to 3 for Cincy
		flapUp.whileHeld(new FlapUp()); //false because it is not rocker button
		flapDown = new JoystickButton(gamePad, 7); //TODO: change to 2 for Cincy
		flapDown.whileHeld(new FlapDown());
		//flapUpRocker = new JoystickButton(buttonBoard, 5);
		//flapUpRocker.whileHeld(new FlapUp(true); //true because using rocker
		//flapDownRocker = new JoystickButton(buttonBoard, 6);
		//flapDownRocker.whileHeld(new FlapUp(true));//^^

		//pivot
		pivotUp = new JoystickButton(gamePad, 3); //TODO: change to 7 for Cincy
		pivotUp.whileHeld(new PivotUp());
		pivotDown = new JoystickButton(gamePad, 2);// TODO: change to 8 for Cincy
		pivotDown.whileHeld(new PivotDown());
		pivotMiddle = new JoystickButton(gamePad, 4);
		pivotMiddle.whileHeld(new PivotMiddle());

		//test nav board
		testDesiredRotationAngle = new JoystickButton(drivingStickForward, 7);
		testDesiredRotationAngle.whenPressed(new RotateToDesiredAngle(.2, 90));

		resetEncoders = new JoystickButton(gamePad, 9);
		resetEncoders.whenPressed(new ResetGyro());
	}

	public double getDrivingJoystickY() {
		if (driveDirection == DriveDirection.kFWD){
			return drivingStickForward.getY();
		}
		else {
			return -drivingStickBackward.getY(); 
		}
	}

	public double getDrivingJoystickX() {
		if (driveDirection == DriveDirection.kFWD){
			return drivingStickForward.getX();
		}
		else {
			return drivingStickBackward.getX(); 
		}
	}

	public double getOperatorStickThrottle() {
		return gamePad.getThrottle();
	}

	public void setDriveDirection(DriveDirection driveDirection) {
		this.driveDirection = driveDirection; 
		System.out.println("Drive direction set to: " + driveDirection);
	}

	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV); 
	}

	public double getDPadX() {
		return gamePad.getRawAxis(AXIS_DPAD);
	}

	public boolean getDPadLeft() {
		double x = getDPadX();
		return (x < -0.5);
	}

	public boolean getDPadRight() {
		double x = getDPadX();
		return (x > 0.5);
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
		return value;
	}
}
