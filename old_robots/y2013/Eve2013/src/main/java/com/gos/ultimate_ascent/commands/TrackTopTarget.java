package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;

public class TrackTopTarget extends SequentialCommandGroup {

    public TrackTopTarget(Chassis chassis) {
        double turnTheta = ShooterCamera.getTopDiffAngle() + ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundTopTarget()) {
            addCommands(new Rotate(chassis, turnTheta, true));
        }
    }

}
