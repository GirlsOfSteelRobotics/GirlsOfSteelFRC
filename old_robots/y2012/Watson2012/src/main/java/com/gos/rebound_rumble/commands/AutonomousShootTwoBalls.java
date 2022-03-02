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
        addRequirements(m_shooter);
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
        m_turret.initEncoder();
        m_turret.enablePID();
        m_cameraDistance = m_shooter.getDistance();
    }

    @Override
    public void execute() {
        m_turret.autoTrack();
        m_shooter.autoShoot(m_cameraDistance);
    }

    @Override
    public boolean isFinished() {
        return timeSinceInitialized() > timeToShootTwoBalls;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
        m_shooter.topRollersOff();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }



}
