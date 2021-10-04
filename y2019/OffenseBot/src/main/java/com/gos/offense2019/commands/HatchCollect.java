/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.offense2019.Robot;
import com.gos.offense2019.subsystems.HatchCollector;

public class HatchCollect extends Command {
  private HatchCollector.HatchState hatchState;

  public HatchCollect(HatchCollector.HatchState hatchState) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.hatchCollector);
    this.hatchState = hatchState;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Shift(" + hatchState + ") init");
    Robot.hatchCollector.driveHatch(hatchState);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }
}
