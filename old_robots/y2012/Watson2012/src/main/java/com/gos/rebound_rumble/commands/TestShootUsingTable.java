package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class TestShootUsingTable extends GosCommandBase {
    private final Shooter m_shooter;
    private final OI m_oi;

    private double m_distance;

    public TestShootUsingTable(OI oi, Shooter shooter) {
        m_oi = oi;
        m_shooter = shooter;
        addRequirements(m_shooter);
        //        SmartDashboard.putNumber("Bank Addition", 0.0);
        SmartDashboard.putNumber("Distance", 0.0);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        //        addition = SmartDashboard.getNumber("Bank Addition", 0.0);
        //        shooter.TESTAutoShootBank(addition,cameraDistance);
        m_distance = SmartDashboard.getNumber("Distance", 0.0);
        m_shooter.autoShoot(m_distance);
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }


}
