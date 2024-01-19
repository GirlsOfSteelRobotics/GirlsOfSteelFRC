package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class DavidDriveSwerve extends BaseTeleopSwerve {
    public DavidDriveSwerve(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        super(chassisSubsystem, joystick);

    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        double joyStickAngle = Math.atan2(yRight, xRight);

        m_subsystem.davidDrive(
            yLeft * TRANSLATION_DAMPING.getValue(),
            xLeft * TRANSLATION_DAMPING.getValue(),
            joyStickAngle);
    }


}
