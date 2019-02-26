/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot; 
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Blinkin.LightPattern;;

public class HatchRelease extends Command {
  public HatchRelease() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatch); 
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init hatch release");
    Robot.hatch.setServoOut();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    Robot.blinkin.setLightPattern(LightPattern.TELEOP_DEFAULT);
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end hatch release");
  }
}
