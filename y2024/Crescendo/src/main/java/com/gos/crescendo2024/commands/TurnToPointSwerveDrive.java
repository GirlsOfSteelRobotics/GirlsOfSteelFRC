package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.Supplier;


public class TurnToPointSwerveDrive extends BaseTeleopSwerve {
    private final Pose2d m_point;
    private final boolean m_faceButt;
    private final Supplier<Pose2d> m_supplier;

    public TurnToPointSwerveDrive(ChassisSubsystem chassisSubsystem, CommandXboxController joystick, Pose2d point, boolean faceButt, Supplier<Pose2d> supplier) {
        super(chassisSubsystem, joystick);
        m_point = point;
        m_faceButt = faceButt;
        m_supplier = supplier;
    }

    @Override
    protected void handleJoystick(double xLeft, double yLeft, double xRight, double yRight) {
        if (!m_faceButt) {
            m_subsystem.turnToFacePoint(
                m_point, yLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
                xLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED
            );
        }
        else {
            m_subsystem.turnButtToFacePoint(
                m_supplier.get(),
                m_point,
                yLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED,
                xLeft * TRANSLATION_DAMPING.getValue() * ChassisSubsystem.MAX_TRANSLATION_SPEED
            );
        }

    }
}
