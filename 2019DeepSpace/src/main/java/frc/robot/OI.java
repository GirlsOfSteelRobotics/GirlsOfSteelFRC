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

import edu.wpi.first.wpilibj.GamepadBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.AllDown;
import frc.robot.commands.AllUp;
import frc.robot.commands.BackDown;
import frc.robot.commands.BackUp;
import frc.robot.commands.Collect;
import frc.robot.commands.FrontDown;
import frc.robot.commands.FrontUp;
import frc.robot.commands.Release;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick drivingPad;

	private JoystickButton backUp;
	private JoystickButton backDown;

	private JoystickButton frontUp;
	private JoystickButton frontDown;

	private JoystickButton allUp;
	private JoystickButton allDown;

	private JoystickButton collect;
	private JoystickButton release;

	public OI() {
		drivingPad = new Joystick(0);

		// Climber Buttons
		backUp = new JoystickButton(drivingPad, 1);
		backUp.whenPressed(new BackUp());
		
		backDown = new JoystickButton(drivingPad, 2);
		backDown.whenPressed(new BackDown());
		
		frontUp = new JoystickButton(drivingPad, 3);
		frontUp.whenPressed(new FrontUp());
		
		frontDown = new JoystickButton(drivingPad, 4);
		frontDown.whenPressed(new FrontDown());
		
		allUp = new JoystickButton(drivingPad, 5);
		allUp.whenPressed(new AllUp());
		
		allDown = new JoystickButton(drivingPad, 6);
		allDown.whenPressed(new AllDown());

		//collector buttons
		collect = new JoystickButton(drivingPad, 7);
		collect.whileHeld(new Collect());

		release = new JoystickButton(drivingPad, 8);
		release.whileHeld(new Release());

  	}


	public double getLeftUpAndDown() {
		return -drivingPad.getY();
	}	

	public double getRightSideToSide() {
		return -drivingPad.getTwist();
	}
	
}




