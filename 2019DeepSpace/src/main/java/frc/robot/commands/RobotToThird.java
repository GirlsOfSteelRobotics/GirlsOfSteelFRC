/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command means that the entire robot goes UP

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class RobotToThird extends CommandGroup {

    private final double BABY_LIDAR_FORWARD_1 = 100;
    private final double BABY_LIDAR_FORWARD_2 = 20;
    private final double FULL_LIDAR_DRIVE_FORWARD = 150;

  public RobotToThird() {
    addSequential(new ClimberAllUp());
    addSequential(new BabyLidarForward(BABY_LIDAR_FORWARD_1)); //make this into a constant
    addSequential(new ClimberFrontUp());
    addSequential(new BabyLidarForward(BABY_LIDAR_FORWARD_2));
    addSequential(new ClimberBackUp());
    addSequential(new LidarDriveForward(FULL_LIDAR_DRIVE_FORWARD));
  }

}