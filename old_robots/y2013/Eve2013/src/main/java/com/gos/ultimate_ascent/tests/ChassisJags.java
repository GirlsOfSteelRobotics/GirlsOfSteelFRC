package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.CommandBase;

public class ChassisJags extends CommandBase {

    private final Chassis m_chassis;
    private double m_speed;

    public ChassisJags(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Chassis Jags", true);
        SmartDashboard.putBoolean("Right Jag", false);
        SmartDashboard.putBoolean("Back Jag", false);
        SmartDashboard.putBoolean("Left Jag", false);
        SmartDashboard.putNumber("Jag Speed", 0.0);
    }

    @Override
    public void execute() {
        m_speed = SmartDashboard.getNumber("Jag Speed", 0.0);
        if (SmartDashboard.getBoolean("Right Jag", false)) {
            m_chassis.setRightJag(m_speed);
        } else {
            m_chassis.setRightJag(0.0);
        }
        if (SmartDashboard.getBoolean("Back Jag", false)) {
            m_chassis.setBackJag(m_speed);
        } else {
            m_chassis.setBackJag(0.0);
        }
        if (SmartDashboard.getBoolean("Left Jag", false)) {
            m_chassis.setLeftJag(m_speed);
        } else {
            m_chassis.setLeftJag(0.0);
        }
        SmartDashboard.putNumber("Right Rate", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Rate", m_chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Rate", m_chassis.getLeftEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false;
        //Eve:  return !SmartDashboard.getBoolean("Chassis Jags", true);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
    }



}
