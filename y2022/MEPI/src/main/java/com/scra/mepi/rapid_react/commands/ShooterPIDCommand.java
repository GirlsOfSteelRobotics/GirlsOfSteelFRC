// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.scra.mepi.rapid_react.TunableNumber;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;

/** An example command that uses an example subsystem. */
public class ShooterPIDCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsytem m_shooterSubsystem;

    private final TunableNumber m_tunableShooterGoal = new TunableNumber("Shooter Goal", 1000);

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShooterPIDCommand(ShooterSubsytem shooterSubsystem) {
        m_shooterSubsystem = shooterSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_shooterSubsystem.setPidRpm(m_tunableShooterGoal.get());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_shooterSubsystem.checkAtSpeed(m_tunableShooterGoal.get());
    }
}
