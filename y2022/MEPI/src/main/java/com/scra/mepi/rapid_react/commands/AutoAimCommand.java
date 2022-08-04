// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.TunableNumber;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.LimelightSubsystem;

/** An example command that uses an example subsystem. */
public class AutoAimCommand extends CommandBase {
  private final DrivetrainSubsystem m_driveTrainSubsystem;
  private final LimelightSubsystem m_limelightSubsystem;
  private final ProfiledPIDController m_controller;
  private final TunableNumber m_P = new TunableNumber("AutoAim(kP)", 0);
  private final TunableNumber m_I = new TunableNumber("AutoAim(kI)", 0);
  private final TunableNumber m_D = new TunableNumber("AutoAim(kD)", 0);

  /**
   * Creates a new AutoAimCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoAimCommand(
      DrivetrainSubsystem driveTrainSubsystem, LimelightSubsystem limelightSubsystem) {
    m_driveTrainSubsystem = driveTrainSubsystem;
    m_limelightSubsystem = limelightSubsystem;
    m_controller =
        new ProfiledPIDController(
            m_P.get(),
            m_I.get(),
            m_D.get(),
            new TrapezoidProfile.Constraints(
                Constants.MAX_TURN_VELOCITY, Constants.MAX_TURN_ACCELERATION));
    m_controller.setTolerance(5);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrainSubsystem);
    addRequirements(limelightSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnSpeed = m_controller.calculate(m_limelightSubsystem.limelightAngle());
    ChassisSpeeds chassisSpeed = new ChassisSpeeds(0, 0, turnSpeed);
    m_driveTrainSubsystem.applyChassisSpeed(chassisSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_controller.atGoal();
  }
}
