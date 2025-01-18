package com.gos.reefscape.subsystems.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface GOSSwerveDrive extends Subsystem {
    void driveWithJoystick(double xJoystick, double yJoystick, double rotationalJoystick);

    void resetPose(Pose2d pose);
}
