package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.infinite_recharge.subsystems.Shooter;


public class RunShooterRPM extends Command {

    private final Shooter m_shooter;
    private final double m_goalRPM;

    public RunShooterRPM(Shooter shooter, double goalRPM) {
        this.m_shooter = shooter;
        this.m_goalRPM = goalRPM;

        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.setRPM(m_goalRPM);
    }

    @Override
    public void end(boolean interrupted) {
    // leave motor running
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isAtFullSpeed();
    }
}
