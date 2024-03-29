package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Chassis;

public class DriveVelocityLinear extends GosCommandBase {
    private final Chassis m_chassis;
    private final Joystick m_joystick;

    private double m_xAxis;
    private double m_yAxis;

    private final double m_scale;

    public DriveVelocityLinear(Chassis chassis, Joystick driverJoystick, double scale) {
        m_chassis = chassis;
        m_joystick = driverJoystick;
        addRequirements(m_chassis);
        this.m_scale = scale;
    }

    @Override
    public void initialize() {

        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    public void execute() {
        m_chassis.setPIDsRate();
        m_xAxis = m_joystick.getX() * m_scale;
        m_yAxis = m_joystick.getY() * m_scale;
        m_chassis.driveVelocityLinear(m_xAxis, m_yAxis);
        System.out.println("R:" + m_chassis.getRightEncoderRate());
        System.out.println("L:" + m_chassis.getLeftEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disableRatePIDs();
        m_chassis.resetEncoders();
        m_chassis.endEncoders();
    }



}
