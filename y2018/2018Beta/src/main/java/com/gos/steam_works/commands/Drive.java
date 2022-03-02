package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class Drive extends CommandBase {
    private final Chassis m_chassis;
    private final OI m_oi;

    public Drive(OI oi, Chassis chassis) {
        m_chassis = chassis;
        m_oi = oi;
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {
        // Change mode to Percent Vbus
        // V per sec; 12 = zero to full speed in 1 second
        //leftTalon.configVoltageCompSaturation(24.0, 0);
        //rightTalon.configVoltageCompSaturation(24.0, 0);
        //leftTalon.setVoltageRampRate(24.0);  IS THIS THE SAME AS ABOVE???
        //rightTalon.setVoltageRampRate(24.0);
        m_oi.setDriveStyle();
        System.out.println("Squared Units: " + m_oi.isSquared());
    }


    @Override
    public void execute() {
        if (m_oi.getDriveStyle() == OI.DriveStyle.joystickArcade || m_oi.getDriveStyle() == OI.DriveStyle.gamePadArcade) {
            m_chassis.arcadeDrive(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX(), m_oi.isSquared());
        } else if (m_oi.getDriveStyle() == OI.DriveStyle.gamePadTank || m_oi.getDriveStyle() == OI.DriveStyle.joystickTank) {
            m_chassis.tankDrive(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX(), m_oi.isSquared());
        }
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    public void stop() {
        m_chassis.stop();
    }


    @Override
    public void end(boolean interrupted) {
        stop();
    }


}
