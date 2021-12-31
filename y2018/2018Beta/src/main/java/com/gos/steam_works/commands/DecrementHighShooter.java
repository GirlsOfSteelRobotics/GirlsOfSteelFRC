package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DecrementHighShooter extends Command {

    private final Shooter m_shooter;

    public DecrementHighShooter(Shooter shooter) {
        m_shooter = shooter;
        // doesn't need requires because that would steal shooter from shootball
    }


    @Override
    protected void initialize() {
        m_shooter.decrementHighShooterSpeed();
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }
}
