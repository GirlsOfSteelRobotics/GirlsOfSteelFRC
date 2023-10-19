package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class StartGyro extends GosCommandBase {

    private final Chassis m_chassis;
    private int m_angle;

    public StartGyro(Chassis chassis, int angle) {
        m_chassis = chassis;
        this.m_angle = angle;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_angle -= m_chassis.getGyroAngle();
        m_chassis.setFieldAdjustment(m_angle);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
