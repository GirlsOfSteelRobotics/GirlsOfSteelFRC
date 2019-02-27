/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command means that the entire robot goes UP

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RobotToPlatform extends CommandGroup {

    private final double BABY_LIDAR_FORWARD_1 = 110.6;
    private final double BABY_LIDAR_FORWARD_2 = 66.15;

  public RobotToPlatform(int platform) {
    
    if (platform == 3)
      addSequential(new ClimberToThirdUp());
    else
      addSequential(new ClimberToSecondUp());

    addSequential(new BabyLidarForward(BABY_LIDAR_FORWARD_1)); //make this into a constant
    addSequential(new ClimberFrontToZero());
    addSequential(new BabyLidarForward(BABY_LIDAR_FORWARD_2));
    addSequential(new ClimberBackToZero());
  }

}