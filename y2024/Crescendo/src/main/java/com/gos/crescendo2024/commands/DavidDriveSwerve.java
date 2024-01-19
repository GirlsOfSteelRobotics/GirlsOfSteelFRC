package com.gos.crescendo2024.commands;

import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class DavidDriveSwerve extends BaseTeleopSwerve {
        private final ChassisSubsystem m_swerve;

    public DavidDriveSwerve(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        super(chassisSubsystem, joystick);
        m_swerve = chassisSubsystem;

    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        double joyStickAngle = Math.atan2(yRight, xRight);

        m_subsystem.davidDrive(
            -xLeft * TRANSLATION_DAMPING.getValue(),
            yLeft * TRANSLATION_DAMPING.getValue(),
            joyStickAngle,
            true);
    }

    protected void davidDrive(double x, double y, double angle) {


    }
}
