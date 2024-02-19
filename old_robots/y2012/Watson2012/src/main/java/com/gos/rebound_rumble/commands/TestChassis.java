package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestChassis extends GosCommandBase {

    private final Chassis m_chassis;
    private double m_speed;

    public TestChassis(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        SmartDashboard.putBoolean("Right Jags", false);
        SmartDashboard.putBoolean("Left Jags", false);
        SmartDashboard.putNumber("Jag speed", 0.0);
        SmartDashboard.putNumber("Right Encoder:", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left Scale", 1.0);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
    }

    @Override
    public void execute() {
        m_speed = SmartDashboard.getNumber("Jag speed", 0.0);
        SmartDashboard.putNumber("Right Encoder:", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder", m_chassis.getLeftEncoderDistance());
        if (SmartDashboard.getBoolean("Right Jags", false)) {
            m_chassis.setRightJags(m_speed);
        }
        if (SmartDashboard.getBoolean("Left Jags", false)) {
            double leftScale = SmartDashboard.getNumber("Left Scale", 1.0);
            m_chassis.setLeftJags(m_speed * leftScale);
        }
        System.out.println("R:" + m_chassis.getRightEncoderDistance());
        System.out.println("L:" + m_chassis.getLeftEncoderDistance());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
        m_chassis.endEncoders();
    }



}
