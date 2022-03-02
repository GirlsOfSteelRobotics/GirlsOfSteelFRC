package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class DecrementHighShooter extends CommandBase {

    private final Shooter m_shooter;

    public DecrementHighShooter(Shooter shooter) {
        m_shooter = shooter;
        // doesn't need requires because that would steal shooter from shootball
    }


    @Override
    public void initialize() {
        m_shooter.decrementHighShooterSpeed();
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
