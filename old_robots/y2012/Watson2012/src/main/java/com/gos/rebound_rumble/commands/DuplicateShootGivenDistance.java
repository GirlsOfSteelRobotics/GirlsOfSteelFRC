package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class DuplicateShootGivenDistance extends GosCommandBaseBase {
    private final Shooter m_shooter;
    private final double m_speed;

    public DuplicateShootGivenDistance(Shooter shooter, double speed) {
        m_shooter = shooter;
        addRequirements(m_shooter);
        this.m_speed = speed;
    }

    @Override
    public void initialize() {
        m_shooter.initPID();
        m_shooter.initEncoder();
        System.out.println("Shooting initialized");
    }

    @Override
    public void execute() {
        m_shooter.shoot(m_speed);

    }

    @Override
    public boolean isFinished() {
        //If this is bridge shooting, wait a long time. Otherwise(key shooting) don't wait a long time
        if (m_speed > 25) {
            return timeSinceInitialized() > 10;
        } else {
            return timeSinceInitialized() > 5;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Shooting Finished");
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }


}
