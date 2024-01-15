// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.TowerSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * An example command that uses an example subsystem.
 */
public class TowerDownCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final TowerSubsystem m_towerSubsystem;

    private final double m_speed = -0.5;

    /**
     * Creates a new TowerDownCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public TowerDownCommand(TowerSubsystem towerSubsystem) {
        m_towerSubsystem = towerSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(towerSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_towerSubsystem.setTowerSpeed(m_speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_towerSubsystem.setTowerSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
