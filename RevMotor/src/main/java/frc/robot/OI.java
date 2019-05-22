/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private Joystick drivingPad;

  public OI() {
    drivingPad = new Joystick(0);
  }

  public double getLeftUpAndDown() {
    return squaredInput(-drivingPad.getY());
  }

  public double getRightSideToSide() {
    return squaredInput(drivingPad.getRawAxis(4));
  }

  private double squaredInput(double speed) {
    return speed * Math.abs(speed);
  }
}