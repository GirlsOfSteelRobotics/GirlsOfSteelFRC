package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.OI;
import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author Jisue
 */
public class ScaledArcadeDrive extends CommandBase {

    private final Joystick m_joystick;
    private final Chassis m_chassis;
    private final double m_scale;
    private double m_x;
    private double m_y;

    public ScaledArcadeDrive(OI oi, Chassis chassis, double scale) {
        m_chassis = chassis;
        m_joystick = oi.getChassisJoystick();
        requires(m_chassis);
        this.m_scale = scale;
    }

    @Override
    protected void initialize() {
        //SmartDashboard.putNumber("Scale", 1);

    }

    @Override
    protected void execute() {
        m_x = m_joystick.getX();
        m_y = m_joystick.getY();
        //scale = SmartDashboard.getNumber("Scale", 0);
        m_chassis.scaleArcadeDrive(m_x, m_y, m_scale);
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
