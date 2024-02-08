package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class DavidDriveSwerve extends BaseTeleopSwerve {

    private double m_lastAngle;

    public DavidDriveSwerve(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        super(chassisSubsystem, joystick);
    }

    @Override
    public void initialize() {
        m_lastAngle = m_subsystem.getPose().getRotation().getDegrees();
    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        double joyStickAngle;
        if (Math.sqrt(xRight * xRight + yRight * yRight) > 0.75) {
            joyStickAngle = Math.toDegrees(Math.atan2(xRight, yRight));
            m_lastAngle = joyStickAngle;
        } else {
            joyStickAngle = m_lastAngle;
        }

        m_subsystem.davidDrive(
            yLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
            xLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
            joyStickAngle);
    }
}
