/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot; 

public class ClimberBackThird extends Command {
  public ClimberBackThird() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber); 
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.setGoalClimberPosition(Robot.climber.THIRD_GOAL_POS);
    System.out.println("init climber back third all up");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Robot.climber.holdClimberBackPosition();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.climber.checkCurrentBackPosition(Robot.climber.BACK_POSITION);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.climberStop(); 
    System.out.println("end Climber to third all up");

  }

  
}
