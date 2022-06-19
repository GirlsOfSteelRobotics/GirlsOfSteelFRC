package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class DriveJoystickTankTwoStick extends DriveJoystickBase {
    private final Joystick m_drivingStickRight;
    private final Joystick m_drivingStickLeft;

    public DriveJoystickTankTwoStick(Joystick drivingStickRight, Joystick drivingStickLeft, Chassis chassis) {
        super(chassis);
        m_drivingStickRight = drivingStickRight;
        m_drivingStickLeft = drivingStickLeft;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.arcadeDriveTele(-m_drivingStickLeft.getY(), -m_drivingStickRight.getY());
    }
}
