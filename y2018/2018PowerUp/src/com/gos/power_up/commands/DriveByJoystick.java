package com.gos.power_up.commands;

import com.gos.power_up.OI;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public DriveByJoystick(Chassis chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }


    @Override
    protected void initialize() {
        m_oi.setDriveStyle();
    }


    @Override
    protected void execute() {
        boolean highGear = true;

        //  if (Robot.shifters.getGearSpeed().equals("kHigh")){
        //    highGear = true;
        //  }
        double throttleFactor;


        //throttle runs .225 speed
        //speedy is 100%
        //regular is 90% speed

        if (highGear) {
            if (m_oi.isThrottle()) {
                m_chassis.getLeftTalon().configOpenloopRamp(0.37, 10); //blinky numbers
                m_chassis.getRightTalon().configOpenloopRamp(0.37, 10);
                throttleFactor = .2;
            } else if (m_oi.isSpeedy()) {
                m_chassis.getLeftTalon().configOpenloopRamp(0.7, 10); //blinky numbers
                m_chassis.getRightTalon().configOpenloopRamp(0.7, 10);
                throttleFactor = 1;
            } else {
                throttleFactor = .65;
            }
        } else {
            if (m_oi.isThrottle()) {
                throttleFactor = .225;
            } else if (m_oi.isSpeedy()) {
                throttleFactor = 1;
            } else {
                throttleFactor = .9;
            }
        }


        m_chassis.curvatureDrive(m_oi.getAmazonLeftUpAndDown() * throttleFactor,
            m_oi.getAmazonRightSideToSide() * throttleFactor, true);


    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_chassis.stop();
    }


}
