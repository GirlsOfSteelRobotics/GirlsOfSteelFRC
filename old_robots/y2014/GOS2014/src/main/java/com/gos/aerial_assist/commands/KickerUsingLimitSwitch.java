/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.subsystems.Kicker;

/**
 * @author the programmers
 */
public class KickerUsingLimitSwitch extends CommandBase {

    private final Kicker m_kicker;
    private int m_loadingOrShooting;
    private boolean m_isLoaded;
    private static final boolean m_testingLimit = false;
    private static final boolean m_testingMotor = false;
    private final boolean m_smartDashboard;
    private double m_startTime;
    private double m_changeInTime;

    //0 = loading; 1 = shooting
    public KickerUsingLimitSwitch(Kicker kicker, int position, boolean usingSD) {
        m_kicker = kicker;
        addRequirements(m_kicker);
        m_loadingOrShooting = position;
        m_smartDashboard = usingSD;
    }

    @Override
    public void initialize() {
        if (!m_testingLimit) {
            if (m_smartDashboard) {
                SmartDashboard.putNumber("Position", -1);
            }
            //the shooter is already loaded
            if (m_loadingOrShooting == 1 && m_kicker.getLimitSwitch()) {
                m_isLoaded = true;
            }
            m_startTime = System.currentTimeMillis();
        }
        if (m_testingMotor) {
            SmartDashboard.putNumber("Turn Kicker Jag", 0.0);
        }
    }

    @Override
    public void execute() {
        if (!m_testingLimit) {
            m_changeInTime = System.currentTimeMillis() - m_startTime;
            if (m_smartDashboard) {
                m_loadingOrShooting = (int) SmartDashboard.getNumber("Position", 0);
            }
            //loading
            if (m_loadingOrShooting == 0) {
                if (!m_kicker.getLimitSwitch()) {
                    //kicker.setJag(1.0);
                    m_kicker.setTalon(1.0);
                    System.out.println("In loading, sent 1 to the jag");
                } else {
                    System.out.println("Not sending a signal");
                }
                //Comment this out if using smart dashboard
            } else if (m_loadingOrShooting == 1 && m_kicker.getLimitSwitch()) {
                //kicker.setJag(1.0);
                m_kicker.setTalon(1.0);
            }

        }
        if (m_testingMotor) {
            double motorSpeed = SmartDashboard.getNumber("Turn Kicker Jag", 0);
            System.out.println("Sending: " + motorSpeed);
            //kicker.setJag(motorSpeed);
            m_kicker.setTalon(motorSpeed);
        }

        SmartDashboard.putBoolean("Limit Switch", m_kicker.getLimitSwitch());

    }

    @Override
    public boolean isFinished() {
        System.out.println("Is the limit hit: " + m_kicker.getLimitSwitch());
        if (!m_testingLimit) {
            if (m_changeInTime > 5000) {
                return true; //If more than 5 seconds have passed, stop trying! It probably means the battery is burned out... OR motor is burned out...
            }
            if (m_loadingOrShooting == 0) {
                return m_kicker.getLimitSwitch();
            } else if (m_loadingOrShooting == 1) {
                System.out.println("IS loaded " + m_isLoaded);
                //comment this out if using SmartDashboard
                //trying to shoot but the shooter is not loaded
                if (!m_isLoaded && !m_smartDashboard)  {
                    return true;
                }
                return !m_kicker.getLimitSwitch();
            }
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //kicker.stopJag();
        m_kicker.stopTalon();
    }



}
