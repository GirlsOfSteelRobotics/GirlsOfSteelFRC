package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

public class TestAutonomousShootTwoBalls extends GosCommandBase {

    private final Shooter m_shooter;
    private final Collector m_collector;

    private double m_timeToShootTwoBalls;

    public TestAutonomousShootTwoBalls(Chassis chassis, Bridge bridge, Shooter shooter, Collector collector, Turret turret) {

        m_shooter = shooter;
        m_collector = collector;

        SmartDashboard.putNumber("ASTB,speed", 0.0);
        SmartDashboard.putNumber("ASTB,time", 0.0);
        addRequirements(m_shooter);
        addRequirements(chassis);
        addRequirements(m_collector);
        addRequirements(turret);
        addRequirements(bridge);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        m_timeToShootTwoBalls = SmartDashboard.getNumber("ASTB,time", 0.0);
        double velocity = SmartDashboard.getNumber("ASTB,speed", 0.0);
        //        velocity = shooter.getBallVelocityFrTable(xDistance);
        //        this.ballVelocity = shooter.getImmobileBallVelocity(xDistance);
        m_shooter.setPIDSpeed(velocity);
        if (/*shooter.isWithinSetPoint(this.ballVelocity,range) &&*/ timeSinceInitialized() > 1.0) {
            m_shooter.topRollersForward();
            //            collector.forwardBrush();
            //            collector.forwardMiddleConveyor();
            m_collector.reverseBrush();
            m_collector.reverseMiddleConveyor();
        }
    }

    @Override
    public boolean isFinished() {
        return timeSinceInitialized() > m_timeToShootTwoBalls;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
        m_shooter.topRollersOff();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }



}
