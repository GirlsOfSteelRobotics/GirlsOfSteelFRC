package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

public class AutonomousKeyShootTwoBalls extends GosCommand {

    private static final double TIME_TO_SHOOT_TWO_BALLS = 7.0;

    private final Collector m_collector;
    private final Shooter m_shooter;
    private final Turret m_turret;

    private final boolean m_autoTrack;

    public AutonomousKeyShootTwoBalls(Chassis chassis, Collector collector, Shooter shooter, Turret turret, boolean autoTrack) {
        m_collector = collector;
        m_shooter = shooter;
        m_turret = turret;
        addRequirements(m_shooter);
        addRequirements(m_collector);
        addRequirements(m_turret);
        addRequirements(chassis);
        this.m_autoTrack = autoTrack;
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        m_shooter.setPIDValues();
        if (m_autoTrack) {
            m_turret.autoTrack();
        }
        double velocity = 24.0;
        m_shooter.setPIDSpeed(velocity);
        if (m_shooter.isWithinSetPoint(velocity)) {
            m_shooter.topRollersForward();
            m_collector.forwardBrush();
            m_collector.forwardMiddleConveyor();
        }
    }

    @Override
    public boolean isFinished() {
        return timeSinceInitialized() > TIME_TO_SHOOT_TWO_BALLS;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
        m_shooter.topRollersOff();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }



}
