package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.DriveFlag;
import edu.wpi.first.wpilibj.Joystick;

public class Drive extends GosCommand {

    private final double m_turningScale;
    private final double m_scale;

    private final Joystick m_joystick;
    private final Chassis m_chassis;
    private double m_x;
    private double m_y;
    private double m_th;
    private final boolean m_gyroOn;

    public Drive(Joystick joystick, Chassis chassis, DriveFlag drive, double scale, double turningScale, boolean gyroOn) {
        addRequirements(drive);
        m_chassis = chassis;
        this.m_scale = scale;
        this.m_turningScale = turningScale;
        this.m_gyroOn = gyroOn;
        m_joystick = joystick;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetGyro();
        m_chassis.startManualRotation();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_x = m_joystick.getX() * m_scale;
        m_y = m_joystick.getY() * m_scale;
        m_th = m_joystick.getZ() * m_turningScale;
        m_chassis.driveVoltage(m_x, m_y, m_th, 1.0, m_gyroOn);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
    }


}
