package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class DriveJoystickArcadeOneStick extends DriveJoystickBase {
    private final Joystick m_drivingGamePad;

    public DriveJoystickArcadeOneStick(Joystick drivingGamepad, Chassis chassis) {
        super(chassis);
        m_drivingGamePad = drivingGamepad;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.arcadeDriveTele(-m_drivingGamePad.getY(), m_drivingGamePad.getX());
    }
}
