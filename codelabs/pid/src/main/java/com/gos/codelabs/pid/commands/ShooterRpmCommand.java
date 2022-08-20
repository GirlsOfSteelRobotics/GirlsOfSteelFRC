package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ShooterRpmCommand extends CommandBase {
    private final ShooterSubsystem m_shooterSubsystem;
    private final double m_rpm;
    private final boolean m_stopWhenAtSpeed;

    public ShooterRpmCommand(ShooterSubsystem shooterSubsystem, double rpm) {
        this(shooterSubsystem, rpm, false);
    }

    public ShooterRpmCommand(ShooterSubsystem shooterSubsystem, double rpm, boolean stopWhenAtSpeed) {
        m_shooterSubsystem = shooterSubsystem;
        m_rpm = rpm;
        m_stopWhenAtSpeed = stopWhenAtSpeed;
        addRequirements(this.m_shooterSubsystem);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.spinAtRpm(m_rpm);
    }

    @Override
    public boolean isFinished() {
        if (!m_stopWhenAtSpeed) {
            return false; // NOPMD
        }

        return m_shooterSubsystem.isAtRpm(m_rpm);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterSubsystem.stop();
    }
}
