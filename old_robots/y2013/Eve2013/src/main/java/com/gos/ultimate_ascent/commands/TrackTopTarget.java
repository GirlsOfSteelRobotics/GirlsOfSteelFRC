package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;

public class TrackTopTarget extends CommandGroup {

    public TrackTopTarget(Chassis chassis) {
        double turnTheta = ShooterCamera.getTopDiffAngle() +
            ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
