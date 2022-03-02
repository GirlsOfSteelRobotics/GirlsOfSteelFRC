package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class IncrementHighShooter extends CommandBase {

    private final Shooter m_shooter;

    public IncrementHighShooter(Shooter shooter) {
        m_shooter = shooter;
        // doesn't need requires because that would steal shooter from shootball
    }


    @Override
    public void initialize() {
        m_shooter.incrementHighShooterSpeed();
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
