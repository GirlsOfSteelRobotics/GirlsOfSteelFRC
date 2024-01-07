// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * An example command that uses an example subsystem.
 */
public class SetShooterSpeedCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsytem m_shooterSubsystem;

    private final double m_goal;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public SetShooterSpeedCommand(ShooterSubsytem shooterSubsystem, double goal) {
        m_shooterSubsystem = shooterSubsystem;
        m_goal = goal;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_shooterSubsystem.setShooterSpeed(m_goal);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
