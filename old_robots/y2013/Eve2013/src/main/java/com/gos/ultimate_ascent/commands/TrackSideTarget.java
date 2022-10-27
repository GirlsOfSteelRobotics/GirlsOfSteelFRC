package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;

public class TrackSideTarget extends SequentialCommandGroup {

    public TrackSideTarget(Chassis chassis) {
        double turnTheta = ShooterCamera.getSideDiffAngle() + ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundSideTarget()) {
            addCommands(new Rotate(chassis, turnTheta, true));
        }
    }

}
