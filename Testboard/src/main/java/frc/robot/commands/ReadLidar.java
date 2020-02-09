/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReadLidar extends Command {
  public ReadLidar() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.lidar);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Init ReadLidar");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("ReadLidar distance " + Robot.lidar.getDistance());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("End  ReadLidar");
  }
}
