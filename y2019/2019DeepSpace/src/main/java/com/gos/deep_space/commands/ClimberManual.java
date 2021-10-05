/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Climber;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.deep_space.Robot;

public class ClimberManual extends Command {

  private boolean directionExtend;
  private Climber.ClimberType type;

  public ClimberManual(boolean directionExtend, Climber.ClimberType climberType){
    this.directionExtend = directionExtend;
    type = climberType;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.climber.holdClimberPosition(type);
    Robot.climber.extendClimber(directionExtend, type);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.climberStop();
  }

}
