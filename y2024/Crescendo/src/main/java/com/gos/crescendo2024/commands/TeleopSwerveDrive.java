package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class TeleopSwerveDrive extends BaseTeleopSwerve {

    public TeleopSwerveDrive(ChassisSubsystem subsystem, CommandXboxController joystick) {
        super(subsystem, joystick);
    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        m_subsystem.teleopDrive(
            yLeft * TRANSLATION_DAMPING.getValue(),
            xLeft * TRANSLATION_DAMPING.getValue(),
            xRight * ROTATION_DAMPING.getValue(),
            true);
    }

}
