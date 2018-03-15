/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// Joysticks
	private Joystick amazonGamePad;
	
	// Buttons
	private JoystickButton driveMotionMagic; 
	
	public OI() {
		// Leave Joystick(0) unallocated since that's where we put the operator gamepad
		amazonGamePad = new Joystick(1);
		amazonGamePad.setTwistChannel(4);

		driveMotionMagic = new JoystickButton(amazonGamePad, 6); // Right button (RB)
		driveMotionMagic.whenPressed(new DriveByMotionMagic(12));
	}

	public double getAmazonLeftUpAndDown() {
		return -amazonGamePad.getY();
	}
	
	public double getAmazonRightSideToSide() {
		return amazonGamePad.getTwist();
	}
}
