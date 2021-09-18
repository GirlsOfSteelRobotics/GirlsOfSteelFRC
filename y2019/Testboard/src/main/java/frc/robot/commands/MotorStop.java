/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MotorStop extends Command {
  public MotorStop() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.motor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init MotorStop");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.motor.stop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end MotorStop");
  }
}
