// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.WpiProfiledPidPropertyBuilder;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.LimelightSubsystem;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class AutoAimCommand extends CommandBase {
    private final DrivetrainSubsystem m_driveTrainSubsystem;
    private final LimelightSubsystem m_limelightSubsystem;
    private final ProfiledPIDController m_controller;
    private final PidProperty m_pidProperties;

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
                0,
                0,
                0,
                new TrapezoidProfile.Constraints(
                    Constants.MAX_TURN_VELOCITY, Constants.MAX_TURN_ACCELERATION));
        m_pidProperties = new WpiProfiledPidPropertyBuilder("AutoAim", false, m_controller)
            .addP(0)
            .addI(0)
            .addD(0)
            .build();
        m_controller.setTolerance(5);
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveTrainSubsystem);
        addRequirements(limelightSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_pidProperties.updateIfChanged();

        double turnSpeed = m_controller.calculate(m_limelightSubsystem.limelightAngle());
        ChassisSpeeds chassisSpeed = new ChassisSpeeds(0, 0, turnSpeed);
        m_driveTrainSubsystem.applyChassisSpeed(chassisSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_controller.atGoal();
    }
}
