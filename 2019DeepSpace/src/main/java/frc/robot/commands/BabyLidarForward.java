/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.*; 
import frc.robot.Robot; 
import edu.wpi.first.wpilibj.command.Command;

public class BabyLidarForward extends Command {

  private double goalLidar;
  public BabyLidarForward(double goalLidar) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.babyDrive);
    requires(Robot.lidar);

    goalLidar = this.goalLidar;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("BabyLidarForward init");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.babyDrive.babyDriveForward(); 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
   return (Robot.lidar.getDistance() <= goalLidar + Robot.lidar.LIDAR_TOLERANCE 
    && Robot.lidar.getDistance() >= goalLidar - Robot.lidar.LIDAR_TOLERANCE);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.babyDrive.babyDriveStop();
    System.out.println("BabyLidarForwards end");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
