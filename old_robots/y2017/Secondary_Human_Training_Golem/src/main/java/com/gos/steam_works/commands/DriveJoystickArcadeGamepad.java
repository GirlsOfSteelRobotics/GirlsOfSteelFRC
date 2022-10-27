package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.XboxController;

/**
 *
 */
public class DriveJoystickArcadeGamepad extends DriveJoystickBase {
    private final XboxController m_drivingGamePad;

    public DriveJoystickArcadeGamepad(XboxController drivingGamepad, Chassis chassis) {
        super(chassis);
        m_drivingGamePad = drivingGamepad;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.arcadeDriveTele(-m_drivingGamePad.getLeftY(), m_drivingGamePad.getRightX());
    }
}
