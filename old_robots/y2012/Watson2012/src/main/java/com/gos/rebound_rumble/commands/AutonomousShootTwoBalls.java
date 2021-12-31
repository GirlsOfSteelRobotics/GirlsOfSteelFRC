package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

public class AutonomousShootTwoBalls extends CommandBase {

    private static final double timeToShootTwoBalls = 5.0; //TODO find how long shooting 2 balls takes

    private final Shooter m_shooter;
    private final Collector m_collector;
    private final Turret m_turret;
    private double m_cameraDistance;

    public AutonomousShootTwoBalls(Shooter shooter, Collector collector, Turret turret) {
        m_shooter = shooter;
        m_collector = collector;
        m_turret = turret;
        requires(m_shooter);
        requires(m_collector);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
        m_turret.initEncoder();
        m_turret.enablePID();
        m_cameraDistance = m_shooter.getDistance();
    }

    @Override
    protected void execute() {
        m_turret.autoTrack();
        m_shooter.autoShoot(m_cameraDistance);
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() > timeToShootTwoBalls;
    }

    @Override
    protected void end() {
        m_shooter.disablePID();
        m_shooter.topRollersOff();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
