package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IncrementHighShooter extends Command {

    private final Shooter m_shooter;

    public IncrementHighShooter(Shooter shooter) {
        m_shooter = shooter;
        // doesn't need requires because that would steal shooter from shootball
    }


    @Override
    protected void initialize() {
        m_shooter.incrementHighShooterSpeed();
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
