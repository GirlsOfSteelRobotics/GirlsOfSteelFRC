package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TESTChassis extends CommandBase {

    private final Chassis m_chassis;
    private double m_speed;

    public TESTChassis(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
        SmartDashboard.putBoolean("Right Jags", false);
        SmartDashboard.putBoolean("Left Jags", false);
        SmartDashboard.putNumber("Jag speed", 0.0);
        SmartDashboard.putNumber("Right Encoder:", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left Scale", 1.0);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
    }

    @Override
    protected void execute() {
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
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.stopJags();
        m_chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
