package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;

public class TrackSideTarget extends CommandGroup {

    public TrackSideTarget(Chassis chassis) {
        double turnTheta = ShooterCamera.getSideDiffAngle() + ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundSideTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
