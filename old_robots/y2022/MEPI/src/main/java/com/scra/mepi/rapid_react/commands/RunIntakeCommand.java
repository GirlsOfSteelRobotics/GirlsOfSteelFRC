// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.HopperSubsystem;
import com.scra.mepi.rapid_react.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class RunIntakeCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final IntakeSubsystem m_intakeSubsystem;

    private final HopperSubsystem m_hopperSubsystem;
    private final TowerSubsystem m_towerSubsystem;

    private final double m_intakeSpeed;
    private final double m_towerSpeed;

    /**
     * Creates a new IntakeCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public RunIntakeCommand(IntakeSubsystem intakeSubsystem, double intakeSpeed, HopperSubsystem hopperSubsystem, TowerSubsystem towerSubsystem, double towerSpeed) {
        m_intakeSubsystem = intakeSubsystem;
        m_hopperSubsystem = hopperSubsystem;
        m_towerSubsystem = towerSubsystem;
        m_intakeSpeed = intakeSpeed;
        m_towerSpeed = towerSpeed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
        addRequirements(hopperSubsystem);
        addRequirements(towerSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intakeSubsystem.set(m_intakeSpeed);
        m_hopperSubsystem.setHopperSpeed(m_intakeSpeed);
        if (m_towerSubsystem.getBeamBreak() || m_intakeSpeed < 0) {
            m_towerSubsystem.setTowerSpeed(m_towerSpeed);
            m_towerSubsystem.setKickerSpeed(-0.5);
        } else {
            m_towerSubsystem.setTowerSpeed(0);
            m_towerSubsystem.setKickerSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intakeSubsystem.set(0);
        m_hopperSubsystem.setHopperSpeed(0);
        m_towerSubsystem.setTowerSpeed(0);
        m_towerSubsystem.setKickerSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
