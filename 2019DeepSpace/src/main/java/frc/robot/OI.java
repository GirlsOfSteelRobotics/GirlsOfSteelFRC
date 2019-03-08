/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick drivingPad;
	public Joystick operatingPad;

	private JoystickButton backUp;
	private JoystickButton backDown;

	private JoystickButton frontUp;
	private JoystickButton frontDown;

	private JoystickButton allUp;
	private JoystickButton allDown;

	private POVButton toSecondUp; 
	private POVButton toThirdUp; 

	private JoystickButton allToZero;
	private JoystickButton frontToZero;
	private JoystickButton backToZero;

	private POVButton robotToThird;
	private POVButton robotToSecond;

	private JoystickButton lidarDrive; // testing purposes only

	private JoystickButton babyDriveForward; 
	private JoystickButton babyDriveBackward;

	private JoystickButton collect;
	private JoystickButton release;

	private JoystickButton hatchCollect;
	private JoystickButton hatchRelease;

	private JoystickButton pivotUp;
	private JoystickButton pivotDown;
	private JoystickButton pivotGround;
	private JoystickButton pivotRocket;
	private JoystickButton pivotShip;

	public OI() {
		drivingPad = new Joystick(0);
		operatingPad = new Joystick(1);

		// Climber buttons
		frontUp = new JoystickButton(drivingPad, 1);
		frontUp.whileHeld(new ClimberFrontUp());
		
		frontDown = new JoystickButton(drivingPad, 2);
		frontDown.whileHeld(new ClimberFrontDown());
		
		backUp = new JoystickButton(drivingPad, 3);
		backUp.whileHeld(new ClimberBackUp());
		
		backDown = new JoystickButton(drivingPad, 4);
		backDown.whileHeld(new ClimberBackDown());
		
		allUp = new JoystickButton(drivingPad, 5);
		allUp.whileHeld(new ClimberAllUp());
		
		allDown = new JoystickButton(drivingPad, 6);
		allDown.whileHeld(new ClimberAllDown());
	
		//backToZero = new JoystickButton(drivingPad, 3);
		//backToZero.whenPressed(new ClimberBackToZero());
		
		//frontToZero = new JoystickButton(drivingPad, 4);
		//frontToZero.whenPressed(new ClimberFrontToZero());

		toSecondUp = new POVButton(drivingPad, 270);
		toSecondUp.whenPressed(new ClimberToSecondUp());

		toThirdUp = new POVButton(drivingPad, 90);
		toThirdUp.whenPressed(new ClimberToThirdUp());
	
		// allToZero = new JoystickButton(drivingPad, 6);
		// allToZero.whenPressed(new ClimberAllToZero());

		// robotToSecond = new POVButton(drivingPad, 270);
		// robotToSecond.whenPressed(new RobotToPlatform(2));

		// robotToThird = new POVButton(drivingPad, 90);
		// robotToThird.whenPressed(new RobotToPlatform(3));

		// Lidar button (testing purposes only)
		// lidarDrive = new JoystickButton(drivingPad, 9);
		// lidarDrive.whenPressed(new LidarDriveForward(30, true));

		// BabyDrive buttons
		babyDriveForward = new JoystickButton(drivingPad, 8);
		babyDriveForward.whileHeld(new BabyDriveForward());

		babyDriveBackward = new JoystickButton(drivingPad, 7);
		babyDriveBackward.whileHeld(new BabyDriveBackwards());

		// Collector buttons
		collect = new JoystickButton(operatingPad, 5);
		collect.whileHeld(new CollectorCollect());

		release = new JoystickButton(operatingPad, 6);
		release.whileHeld(new CollectorRelease());

		// Hatch buttons
		hatchCollect = new JoystickButton(operatingPad, 7);
		hatchCollect.whenPressed(new HatchCollect());

		hatchRelease = new JoystickButton(operatingPad, 8);
		hatchRelease.whenPressed(new HatchRelease());

		// Pivot buttons
		// negative is down, positive is up
		// must start up
		pivotUp = new JoystickButton(operatingPad, 3);
		pivotUp.whileHeld(new PivotUp());
		
		pivotDown = new JoystickButton(operatingPad, 1);
		pivotDown.whileHeld(new PivotDown());

		pivotGround = new JoystickButton(operatingPad, 2);
		pivotGround.whenPressed(new PivotToGround());
		
		pivotRocket = new JoystickButton (operatingPad, 4);
		pivotRocket.whenPressed(new PivotToRocket());

		pivotShip = new JoystickButton (operatingPad, 10);
		pivotShip.whenPressed(new PivotToShip());

	}
	public double getLeftUpAndDown() {
		return drivingPad.getY();
	}	

	public double getRightSideToSide() {
		return -drivingPad.getRawAxis(4);
	}
	
}




