// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import com.gos.lib.properties.PropertyManager;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ShooterPIDCommand extends CommandBase {
    private final ShooterSubsytem m_shooterSubsystem;

    private final PropertyManager.IProperty<Double> m_tunableShooterGoal = PropertyManager.createDoubleProperty(false, "Shooter Goal", 1000);

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public ShooterPIDCommand(ShooterSubsytem shooterSubsystem) {
        m_shooterSubsystem = shooterSubsystem;
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
        m_shooterSubsystem.setPidRpm(m_tunableShooterGoal.getValue());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_shooterSubsystem.checkAtSpeed(m_tunableShooterGoal.getValue());
    }
}
