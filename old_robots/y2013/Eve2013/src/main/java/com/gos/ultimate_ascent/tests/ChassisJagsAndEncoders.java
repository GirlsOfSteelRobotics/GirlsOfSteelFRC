/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.commands.GosCommandBaseBase;
import com.gos.ultimate_ascent.subsystems.Chassis;

/**
 * @author sophia
 */
public class ChassisJagsAndEncoders extends GosCommandBaseBase {
    //copied from KiwiDrive code

    private final Chassis m_chassis;
    private double m_speed;

    public ChassisJagsAndEncoders(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        //        SmartDashboard.putBoolean("Right Jag", false);
        //        SmartDashboard.putBoolean("Back Jag", false);
        //        SmartDashboard.putBoolean("Left Jag", false);
        //        SmartDashboard.putNumber("Jag speed", 0.0);
        //        SmartDashboard.putNumber("Right Encoder:", chassis.getRightEncoderDistance());
        //        SmartDashboard.putNumber("Back Encoder:", chassis.getBackEncoderDistance());
        //        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
    }

    @Override
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void execute() {
        //        speed = SmartDashboard.getNumber("Jag speed", 0.0);
        //        SmartDashboard.putNumber("Right Encoder", chassis.getRightEncoderDistance());
        //        SmartDashboard.putNumber("Back Encoder:", chassis.getBackEncoderDistance());
        //        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
        //        if(SmartDashboard.getBoolean("Right Jag", false)){
        //            chassis.setRightJag(speed);
        //        }
        //        if(SmartDashboard.getBoolean("Back Jag", false)){
        //            chassis.setBackJag(speed);
        //        }
        //        if(SmartDashboard.getBoolean("Left Jag", false)){
        //            chassis.setLeftJag(speed);
        //        }

        //tests to see if Jags goes to set speed
        double allowedError = 0.05;
        double desiredSpeed = 0.2;
        m_chassis.setRightJag(desiredSpeed);
        try {
            wait(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(); // NOPMD
        }
        m_speed = m_chassis.getRightEncoderRate();
        if (m_speed > desiredSpeed - allowedError && m_speed < desiredSpeed + allowedError) {
            System.out.println("Passed Right Jag speed test");
        }
        //System.out.println("R Rate: " + chassis.getRightEncoderRate());
        desiredSpeed = 0.3;
        m_chassis.setBackJag(desiredSpeed);
        try {
            wait(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(); // NOPMD
        }
        //System.out.println("B Rate: " + chassis.getBackEncoderRate());
        m_speed = m_chassis.getBackEncoderRate();
        if (m_speed > desiredSpeed - allowedError && m_speed < desiredSpeed + allowedError) {
            System.out.println("Passed Back Jag speed test");
        }
        desiredSpeed = 0.4;
        m_chassis.setLeftJag(desiredSpeed);
        try {
            wait(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(); // NOPMD
        }
        m_speed = m_chassis.getLeftEncoderRate();
        if (m_speed > desiredSpeed - allowedError && m_speed < desiredSpeed + allowedError) {
            System.out.println("Passed Left Jag speed test");
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
        m_chassis.stopEncoders();
    }




}
