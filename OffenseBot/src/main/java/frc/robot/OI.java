/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.robot.commands.Shift;
import frc.robot.subsystems.Shifters;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private Joystick drivingPad;
  private Joystick operatingPad;
  private POVButton shiftUp;
  private POVButton shiftDown;

  public OI() {
    drivingPad = new Joystick(0);
    //operatingPad = new Joystick(1);

    shiftUp = new POVButton(drivingPad, 0);
    shiftDown = new POVButton(drivingPad, 180);

    shiftUp.whenPressed(new Shift(Shifters.Speed.kHigh));
    shiftDown.whenPressed(new Shift(Shifters.Speed.kLow));
  }

  // Return the negation of the Y axis value because 
  // tipping the joystick forward returns negative values (think of an airplane)
  public double getLeftUpAndDown() {
		return -drivingPad.getY();
	}	

	public double getRightSideToSide() {
		return drivingPad.getRawAxis(4);
	}
}
