package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.GosCommand;

public class ChassisPID extends GosCommand {

    private final Chassis m_chassis;
    private double m_rate;

    private double m_rightP;
    private double m_rightI;
    private double m_rightD;
    private double m_backP;
    private double m_backI;
    private double m_backD;
    private double m_leftP;
    private double m_leftI;
    private double m_leftD;

    public ChassisPID(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
        SmartDashboard.putNumber("PID rate", 0.0);
        SmartDashboard.putBoolean("Right PID", false);
        SmartDashboard.putBoolean("Back PID", false);
        SmartDashboard.putBoolean("Left PID", false);
        SmartDashboard.putBoolean("Click When Done Testing Chassis PID", false);
        SmartDashboard.putNumber("Right P", 0.0);
        SmartDashboard.putNumber("Right I", 0.0);
        SmartDashboard.putNumber("Right D", 0.0);
        SmartDashboard.putNumber("Back P", 0.0);
        SmartDashboard.putNumber("Back I", 0.0);
        SmartDashboard.putNumber("Back D", 0.0);
        SmartDashboard.putNumber("Left P", 0.0);
        SmartDashboard.putNumber("Left I", 0.0);
        SmartDashboard.putNumber("Left D", 0.0);
        SmartDashboard.putNumber("Right Encoder", m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder", m_chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder", m_chassis.getLeftEncoderRate());
    }

    @Override
    public void execute() {
        //get rate
        m_rate = SmartDashboard.getNumber("PID rate", 0.0);
        //get P, I, & D's
        //SmartDashboard.getBoolean("Click When Done Testing Chassis PID", false);
        m_rightP = SmartDashboard.getNumber("Right P", 0.0);
        m_rightI = SmartDashboard.getNumber("Right I", 0.0);
        m_rightD = SmartDashboard.getNumber("Right D", 0.0);
        m_backP = SmartDashboard.getNumber("Back P", 0.0);
        m_backI = SmartDashboard.getNumber("Back I", 0.0);
        m_backD = SmartDashboard.getNumber("Back D", 0.0);
        m_leftP = SmartDashboard.getNumber("Left P", 0.0);
        m_leftI = SmartDashboard.getNumber("Left I", 0.0);
        m_leftD = SmartDashboard.getNumber("Left D", 0.0);
        //set P, I, D values
        m_chassis.setRightPIDRateValues(m_rightP, m_rightI, m_rightD);
        System.out.println("Right P: " + m_rightP + " I:" + m_rightI + " D: " + m_rightD);
        m_chassis.setBackPIDRateValues(m_backP, m_backI, m_backD);
        m_chassis.setLeftPIDRateValues(m_leftP, m_leftI, m_leftD);
        //get rate
        m_rate = SmartDashboard.getNumber("PID rate", 0.0);
        //set the rate if enabled
        //right setting
        if (SmartDashboard.getBoolean("Right PID", false)) {
            m_chassis.setRightPIDRate(m_rate);
        } else {
            m_chassis.setRightPIDRate(0.0);
        }
        //back setting
        if (SmartDashboard.getBoolean("Back PID", false)) {
            m_chassis.setBackPIDRate(m_rate);
        } else {
            m_chassis.setBackPIDRate(0.0);
        }
        //left setting
        if (SmartDashboard.getBoolean("Left ", false)) {
            m_chassis.setLeftPIDRate(m_rate);
        } else {
            m_chassis.setLeftPIDRate(0.0);
        }
        //print encoder rates
        SmartDashboard.putNumber("Right Encoder",
            m_chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder",
            m_chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder",
            m_chassis.getLeftEncoderRate());
    }

    @Override
    public boolean isFinished() {
        return false; //from KiwiDrive
        //Eve:  return !SmartDashboard.getBoolean("Click When Done Testing Chassis PID", true);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopRatePIDs();
        m_chassis.stopEncoders();
        m_chassis.stopJags();
    }



}
