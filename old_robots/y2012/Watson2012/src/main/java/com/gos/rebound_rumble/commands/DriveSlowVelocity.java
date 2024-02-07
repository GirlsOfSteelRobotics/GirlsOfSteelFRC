package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Chassis;

public class DriveSlowVelocity extends GosCommandBaseBase {
    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xAxis;
    private double m_yAxis;

    public DriveSlowVelocity(Chassis chassis, Joystick driverJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = driverJoystick;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    @Override
    public void execute() {
        m_chassis.setPIDsRate();
        m_xAxis = m_driverJoystick.getX();
        m_yAxis = m_driverJoystick.getY();
        m_chassis.driveSlowVelocity(m_xAxis, m_yAxis);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }



}
