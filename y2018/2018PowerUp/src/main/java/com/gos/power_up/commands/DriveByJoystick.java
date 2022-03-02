package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class DriveByJoystick extends CommandBase {

    private final Chassis m_chassis;
    private final Joystick m_amazonGamePad;

    public DriveByJoystick(Chassis chassis, Joystick amazonGamePad) {
        m_chassis = chassis;
        m_amazonGamePad = amazonGamePad;
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        boolean highGear = true;

        //  if (Robot.shifters.getGearSpeed().equals("kHigh")){
        //    highGear = true;
        //  }
        double throttleFactor;


        //throttle runs .225 speed
        //speedy is 100%
        //regular is 90% speed

        if (highGear) {
            if (isThrottle()) {
                m_chassis.getLeftTalon().configOpenloopRamp(0.37, 10); //blinky numbers
                m_chassis.getRightTalon().configOpenloopRamp(0.37, 10);
                throttleFactor = .2;
            } else if (isSpeedy()) {
                m_chassis.getLeftTalon().configOpenloopRamp(0.7, 10); //blinky numbers
                m_chassis.getRightTalon().configOpenloopRamp(0.7, 10);
                throttleFactor = 1;
            } else {
                throttleFactor = .65;
            }
        } else {
            if (isThrottle()) {
                throttleFactor = .225;
            } else if (isSpeedy()) {
                throttleFactor = 1;
            } else {
                throttleFactor = .9;
            }
        }


        m_chassis.curvatureDrive(-m_amazonGamePad.getY() * throttleFactor,
            m_amazonGamePad.getTwist() * throttleFactor, true);


    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }

    private boolean isThrottle() {
        return m_amazonGamePad.getRawAxis(3) > .5;
    }

    private boolean isSpeedy() {
        return m_amazonGamePad.getRawAxis(2) > .5;
    }

}
