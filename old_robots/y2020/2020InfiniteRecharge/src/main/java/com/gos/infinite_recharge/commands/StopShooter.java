package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.Shooter;


public class StopShooter extends CommandBase {

    private final Shooter m_shooter;

    public StopShooter(Shooter shooter) {
        this.m_shooter = shooter;
        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.stop();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
