// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.HopperSubsystem;
import com.scra.mepi.rapid_react.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * An example command that uses an example subsystem.
 */
public class StopIntakeCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final IntakeSubsystem m_intakeSubsystem;
    private final HopperSubsystem m_hopperSubsystem;


    /**
     * Creates a new IntakeCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public StopIntakeCommand(
            IntakeSubsystem intakeSubsystem,
            HopperSubsystem hopperSubsystem) {
        m_intakeSubsystem = intakeSubsystem;
        m_hopperSubsystem = hopperSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
        addRequirements(hopperSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intakeSubsystem.set(0);
        m_hopperSubsystem.setHopperSpeed(0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intakeSubsystem.set(0);
        m_hopperSubsystem.setHopperSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
