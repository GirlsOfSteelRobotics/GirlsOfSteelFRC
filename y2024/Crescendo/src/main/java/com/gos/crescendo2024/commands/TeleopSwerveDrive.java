package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class TeleopSwerveDrive extends BaseTeleopSwerve {

    public TeleopSwerveDrive(ChassisSubsystem subsystem, CommandXboxController joystick) {
        super(subsystem, joystick);
    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        m_subsystem.teleopDrive(
            -xLeft * TRANSLATION_DAMPING.getValue(),
            yLeft * TRANSLATION_DAMPING.getValue(),
            xRight * ROTATION_DAMPING.getValue(),
            true);
    }

}
