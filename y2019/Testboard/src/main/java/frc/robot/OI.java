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

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick drivingPad;
	
	private JoystickButton blinkinTest;
	private JoystickButton readLidar;
	private JoystickButton spinByLidar;
	private JoystickButton driveByVision;

	public OI() {
		drivingPad = new Joystick(0);

		blinkinTest = new JoystickButton(drivingPad, 1);
		blinkinTest.whenPressed(new TryBlinkin());

		readLidar = new JoystickButton(drivingPad, 2);
		readLidar.whenPressed(new ReadLidar());

		spinByLidar = new JoystickButton(drivingPad, 4);
		spinByLidar.whenPressed(new SpinByLidar());

		driveByVision = new JoystickButton(drivingPad, 5);
		driveByVision.whenPressed(new DriveByVision());
	}
}
