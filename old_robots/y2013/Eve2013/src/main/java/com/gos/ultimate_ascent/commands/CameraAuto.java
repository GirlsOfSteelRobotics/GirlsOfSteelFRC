package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CameraAuto extends SequentialCommandGroup {
    private static final double ANGLE_ADJUST = 7;

    public CameraAuto(Chassis chassis) {
        double turnTheta = ShooterCamera.getTopDiffAngle() + ANGLE_ADJUST;
        if (ShooterCamera.foundTopTarget()) {
            addCommands(new Rotate(chassis, turnTheta, true));
        }
    }

}
