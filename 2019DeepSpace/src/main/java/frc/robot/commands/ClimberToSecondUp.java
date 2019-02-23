/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot; 

public class ClimberToSecondUp extends Command {
  public ClimberToSecondUp() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climber); 

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climber.setGoalClimberPosition(Robot.climber.SECOND_GOAL_POS);
    System.out.println("init Climber To Second Up");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("goal front position: " + Robot.climber.goalFrontPosition + "actual front position: " + Robot.climber.getFrontPosition());
    System.out.println("goal back position" + Robot.climber.goalBackPosition + "actual back position: " + Robot.climber.getBackPosition());

    Robot.climber.holdClimberPosition();
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.climber.checkCurrentPosition(Robot.climber.SECOND_GOAL_POS);
  
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.climberStop(); 
    System.out.println("end Climber To Second Up");

  }

 
}
