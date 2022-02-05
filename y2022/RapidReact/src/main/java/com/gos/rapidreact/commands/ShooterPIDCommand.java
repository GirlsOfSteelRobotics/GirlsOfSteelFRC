package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;


public class ShooterPIDCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private final double m_goalRPM;

    public ShooterPIDCommand(ShooterSubsystem shooterSubsystem, double goalRPM) {
        this.m_shooter = shooterSubsystem;
        m_goalRPM = goalRPM;

        addRequirements(this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterPIDSpeed(m_goalRPM);
    }

    @Override
    public boolean isFinished() {
        double error = Math.abs(m_goalRPM - m_shooter.getEncoder());
        return error < ShooterSubsystem.ALLOWABLE_ERROR;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
