/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterIntake;

public class IntakeCells extends CommandBase {
  /**
   * Creates a new RotationControl.
   */

ShooterIntake shooterIntake;
boolean intake;

  public IntakeCells(ShooterIntake shooterIntake, boolean intake) {
    this.shooterIntake = shooterIntake;
    this.intake = intake;
    super.addRequirements(shooterIntake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (intake)
      shooterIntake.collectCells();
    else
      shooterIntake.decollectCells();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      shooterIntake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}