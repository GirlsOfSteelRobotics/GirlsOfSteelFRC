// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import java.io.IOException;
import java.nio.file.Path;

/** An example command that uses an example subsystem. */
public class TrajectoryCommand extends CommandBase {
    private final DrivetrainSubsystem m_subsystem;
    private Trajectory m_trajectory = null;
    private final Timer m_timer;
    private final RamseteController m_controller;

    /**
     * Creates a new TrajectoryCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public TrajectoryCommand(DrivetrainSubsystem subsystem, String trajectoryPathName) {
        m_subsystem = subsystem;
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryPathName);
            m_trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError(
                    "Unable to open trajectory: " + trajectoryPathName, ex.getStackTrace());
        }
        m_timer = new Timer();
        m_controller = new RamseteController();

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_timer.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Trajectory.State goal = m_trajectory.sample(m_timer.get());
        ChassisSpeeds adjustedSpeeds = m_controller.calculate(m_subsystem.getPose(), goal);
        m_subsystem.applyChassisSpeed(adjustedSpeeds);
    }

    @Override
    // Called once the command ends or is interrupted.
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(m_trajectory.getTotalTimeSeconds());
    }
}
