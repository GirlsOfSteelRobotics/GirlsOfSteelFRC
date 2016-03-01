package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.*;
import org.usfirst.frc.team3504.robot.commands.autonomous.*;
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

	Joystick buttonBoard = new Joystick(2); //the button board gets plugged into USB and acts like a Joystick
	Joystick drivingStickForward = new Joystick(0);
	Joystick drivingStickBackward = new Joystick(1); 

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

	private JoystickButton collectBallButton;
	private JoystickButton releaseBallButton;
	private JoystickButton flapUp;
	private JoystickButton flapDown;
	private JoystickButton flapUpRocker;
	private JoystickButton flapDownRocker;
	private JoystickButton pivotUp;
	private JoystickButton pivotDown;
	private JoystickButton pivotMiddle;
	private JoystickButton portcullis;
	private JoystickButton chevalDeFrise;
	private JoystickButton testDesiredRotationAngle;  //for NavBoard
	private JoystickButton resetGyro;
	private JoystickButton shooterStop;


	private static final int AXIS_DPAD = 6;

	//Flap: Rocker (2 buttons) + 2 buttons, Pivot: 3 buttons, Claw: 2 Buttons, Other: 3 Buttons (defenses & scoring), Shooter: 2 buttons - total 12 buttons + rocker

	public OI() {


		shiftUpButton = new JoystickButton(drivingStickForward, 3);
		shiftUpButton.whenPressed(new ShiftUp());
		shiftDownButton = new JoystickButton(drivingStickForward, 4);
		shiftDownButton.whenPressed(new ShiftDown());

		shiftUpButton2 = new JoystickButton (drivingStickBackward, 3);
		shiftUpButton2.whenPressed (new ShiftUp());
		shiftDownButton2 = new JoystickButton (drivingStickBackward, 4);
		shiftDownButton2.whenPressed(new ShiftDown());

		switchCam = new JoystickButton(drivingStickForward, 10);
		switchCam.whenPressed(new SwitchCam());

		switchCam2 = new JoystickButton(drivingStickBackward, 10);
		switchCam2.whenPressed(new SwitchCam());

		switchToForward = new JoystickButton(drivingStickForward, 1); 
		switchToForward.whenPressed(new SwitchToForward()); 

		switchToBackward = new JoystickButton(drivingStickBackward, 1);
		switchToBackward.whenPressed(new SwitchToBackward());

		//works for both claw and shooter
		collectBallButton = new JoystickButton(buttonBoard, 5);
		collectBallButton.whileHeld(new CollectBall());
		releaseBallButton = new JoystickButton(buttonBoard, 6);
		releaseBallButton.whileHeld(new ReleaseBall());
		
		//button board buttons
		if(!RobotMap.USING_CLAW) {
			//shooter
			shooterStop = new JoystickButton(buttonBoard, 1);
			shooterStop.whenPressed(new StopShooterWheels());
		}

		//flap: rocker = drivers want to use to control movement of flap at full speed, w/o rocker goes until limit switch
		flapUp = new JoystickButton(buttonBoard, 7);
		flapUp.whileHeld(new FlapUp()); //false because it is not rocker button
		flapDown = new JoystickButton(buttonBoard, 8);
		flapDown.whileHeld(new FlapDown());
		//flapUpRocker = new JoystickButton(buttonBoard, 5);
		//flapUpRocker.whileHeld(new FlapUp(true)); //true because using rocker
		//flapDownRocker = new JoystickButton(buttonBoard, 6);
		//flapDownRocker.whileHeld(new FlapUp(true));//^^

		//pivot
		pivotUp = new JoystickButton(buttonBoard, 3);
		pivotUp.whileHeld(new PivotUp());
		pivotDown = new JoystickButton(buttonBoard, 2);
		pivotDown.whileHeld(new PivotDown());
		pivotMiddle = new JoystickButton(buttonBoard, 4);
		pivotMiddle.whenPressed(new PivotMiddle());

		//defenses: skipped 2 numbers for shooter
		//portcullis = new JoystickButton(buttonBoard, 12);
		//portcullis.whenPressed(new Portcullis());
		//chevalDeFrise = new JoystickButton(buttonBoard, 13);
		//chevalDeFrise.whenPressed(new ChevalDeFrise());

		//test nav board
		testDesiredRotationAngle = new JoystickButton(drivingStickForward, 7);
		testDesiredRotationAngle.whenPressed(new RotateToDesiredAngle(.2, 90));

		resetGyro = new JoystickButton(drivingStickForward, 8);
		resetGyro.whenPressed(new ResetGyro());

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
		return buttonBoard.getThrottle();
	}

	public void setDriveDirection(DriveDirection driveDirection) {
		this.driveDirection = driveDirection; 
		System.out.println("Drive direction set to: " + driveDirection);
	}

	public boolean isJoystickReversed() {
		return (driveDirection == DriveDirection.kREV); 
	}

	public double getDPadX() {
		return buttonBoard.getRawAxis(AXIS_DPAD);
	}

	public boolean getDPadLeft() {
		double x = getDPadX();
		return (x < -0.5);
	}

	public boolean getDPadRight() {
		double x = getDPadX();
		return (x > 0.5);
	}

}
