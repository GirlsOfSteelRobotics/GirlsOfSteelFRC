package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Chassis;

public class Nudge extends GosCommandBaseBase {

    private final Chassis m_chassis;
    private final Joystick m_driverJoystick;

    private double m_xValue;

    public Nudge(Chassis chassis, Joystick driverJoystick) {
        m_chassis = chassis;
        this.m_driverJoystick = driverJoystick;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initHoldPosition();
    }

    @Override
    public void execute() {
        m_xValue = m_driverJoystick.getX();
        if (-0.2 > m_xValue || m_xValue > 0.2) {
            m_chassis.nudge(m_xValue);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }



}
