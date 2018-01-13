/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick drivingJoystick = new Joystick (0);
	
	private JoystickButton shifterUp;
	private JoystickButton shifterDown;
	private JoystickButton driveByDistanceLow;
	private JoystickButton driveByDistanceHigh;
	
	public OI() {
		shifterDown = new JoystickButton(drivingJoystick, 2);
		shifterUp = new JoystickButton(drivingJoystick, 3);
		driveByDistanceLow = new JoystickButton(drivingJoystick, 9);
		driveByDistanceHigh = new JoystickButton(drivingJoystick, 10);
		
		shifterDown.whenPressed(new ShiftDown());
		shifterUp.whenPressed(new ShiftUp());
		driveByDistanceLow.whenPressed(new DriveByDistance(12, Shifters.Speed.kLow));
		driveByDistanceHigh.whenPressed(new DriveByDistance(12, Shifters.Speed.kHigh));
	}

	
	public double getDrivingJoystickX() {
		return drivingJoystick.getX();
	}
	
	public double getDrivingJoystickY() {
		return drivingJoystick.getY();
	}
	
	public double getCurrentThrottle() {
		return drivingJoystick.getThrottle();
	}
}

