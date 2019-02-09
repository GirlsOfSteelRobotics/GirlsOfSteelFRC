/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command means that the entire robot goes UP

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimberAllUp extends Command {
  public ClimberAllUp() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("All Up");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.climber.holdClimberPosition();
    Robot.climber.incrementClimber();
    System.out.println("Front Position: " + Robot.climber.getFrontPosition() + " Back Position: " + Robot.climber.getBackPosition());
    System.out.println("Goal Position: " + Robot.climber.getGoalClimberPosition());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean isFinished = (/*Robot.climber.getGoalClimberPosition() <= Robot.climber.getFrontPosition() + 500 
    && Robot.climber.getGoalClimberPosition() >= Robot.climber.getFrontPosition()-500)
    && (*/Robot.climber.getGoalClimberPosition() <= Robot.climber.getBackPosition()+ 500 
    && Robot.climber.getGoalClimberPosition() >= Robot.climber.getBackPosition()-500);
    System.out.println("isFinished: " + isFinished);
    return (/*Robot.climber.getGoalClimberPosition() <= Robot.climber.getFrontPosition() + 500 
      && Robot.climber.getGoalClimberPosition() >= Robot.climber.getFrontPosition()-500)
      && (*/Robot.climber.getGoalClimberPosition() <= Robot.climber.getBackPosition()+ 500 
      && Robot.climber.getGoalClimberPosition() >= Robot.climber.getBackPosition()-500);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.climberStop(); 
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
