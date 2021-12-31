package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Chassis;

public class DriveSlowTurning extends CommandBase {
    private final Chassis m_chassis;
    private final Joystick m_joystick;

    private final double m_scale;
    private final double m_turningScale;
    private double m_xAxis;
    private double m_yAxis;

    public DriveSlowTurning(Chassis chassis, Joystick driverJoystick, double scale, double turningScale) {
        m_chassis = chassis;
        m_joystick = driverJoystick;
        requires(m_chassis);
        this.m_scale = scale;
        this.m_turningScale = turningScale;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        m_xAxis = m_joystick.getX() * m_scale;
        m_yAxis = m_joystick.getY() * m_scale;
        m_chassis.driveJagsLinearSlowTurning(m_xAxis, m_yAxis, m_turningScale);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
