package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.XboxController;

/**
 *
 */
public class DriveJoystickTankGamepad extends DriveJoystickBase {
    private final XboxController m_drivingStick;

    public DriveJoystickTankGamepad(XboxController drivingGamepad, Chassis chassis) {
        super(chassis);
        m_drivingStick = drivingGamepad;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.tankDriveTele(-m_drivingStick.getLeftY(), -m_drivingStick.getRightY());
    }
}
