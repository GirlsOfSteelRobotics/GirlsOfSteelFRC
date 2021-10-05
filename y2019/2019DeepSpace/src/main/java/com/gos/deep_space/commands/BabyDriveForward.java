/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.deep_space.Robot;


public class BabyDriveForward extends Command {
  private final double BABYDRIVE_SPEED = -0.4;

  public BabyDriveForward() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.babyDrive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init BabyDriveForward");
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end BabyDriveForward");
    Robot.babyDrive.babyDriveStop();
  }

}
