package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TurnToPointSwerveDrive extends BaseTeleopSwerve {

    private final Pose2d m_point;

    public TurnToPointSwerveDrive(ChassisSubsystem chassisSubsystem, CommandXboxController joystick, Pose2d point) {
        super(chassisSubsystem, joystick);
        m_point = point;
    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        m_subsystem.turnToPointDrive(
            yLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
            xLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
            m_point);
    }
}
