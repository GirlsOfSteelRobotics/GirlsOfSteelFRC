package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.objects.ShooterCamera;
import com.gos.ultimate_ascent.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CameraAuto extends CommandGroup {
    private static final double angleAjust = 7;

    public CameraAuto(Chassis chassis) {
        double turnTheta = ShooterCamera.getTopDiffAngle() + angleAjust;
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
