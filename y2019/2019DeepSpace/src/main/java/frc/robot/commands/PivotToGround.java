/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Pivot;

public class PivotToGround extends Command {
  public PivotToGround() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.pivot);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("init PivotToGround"); 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.pivot.setGoalPivotPosition(Pivot.PIVOT_GROUND);
    Robot.pivot.holdPivotPosition();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.pivot.checkCurrentPivotPosition(Pivot.PIVOT_GROUND));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.pivot.pivotStop();
    System.out.println("end PivotToGround");
  }
}
