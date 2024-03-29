package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

public class CameraShooterTest extends GosCommandBase {
    private final Shooter m_shooter;
    private final Collector m_collector;
    private double m_speed;

    public CameraShooterTest(Shooter shooter, Collector collector) {
        m_shooter = shooter;
        m_collector = collector;
        addRequirements(m_shooter);
        addRequirements(m_collector);
        SmartDashboard.putNumber("CST,speed", 0.0);
        SmartDashboard.putBoolean("shoot", false);
        SmartDashboard.putBoolean("top rollers", false);
        SmartDashboard.putBoolean("collectors", false);
        SmartDashboard.putNumber("CST,P", 0.0);
        SmartDashboard.putNumber("CST,I", 0.0);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        m_shooter.setPIDValues(SmartDashboard.getNumber("CST,P", 0.0), SmartDashboard.getNumber("CST,I", 0.0), 0.0);
        m_speed = SmartDashboard.getNumber("CST,speed", 0.0);
        if (SmartDashboard.getBoolean("collectors", false)) {
            m_collector.reverseBrush();
            m_collector.reverseMiddleConveyor();
        } else {
            m_collector.stopBrush();
            m_collector.stopMiddleConveyor();
        }
        if (SmartDashboard.getBoolean("top rollers", false)) {
            m_shooter.topRollersForward();
        } else {
            m_shooter.topRollersOff();
        }
        if (SmartDashboard.getBoolean("shoot", false)) {
            m_shooter.setPIDSpeed(m_speed);
        } else {
            m_shooter.setPIDSpeed(0.0);
        }
        SmartDashboard.putBoolean("ready to shoot", m_shooter.isWithinSetPoint(m_speed));
        SmartDashboard.putNumber("Enc rate shoot: ", m_shooter.getEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.disablePID();
    }


}
