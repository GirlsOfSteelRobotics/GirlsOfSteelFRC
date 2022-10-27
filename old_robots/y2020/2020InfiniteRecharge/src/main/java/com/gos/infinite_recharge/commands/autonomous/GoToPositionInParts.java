package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToPositionInParts extends SequentialCommandGroup {

    public GoToPositionInParts(Chassis chassis, double finalPositionX, double finalPositionY, double allowableAngleError, double allowableDistanceError) {

        double dx = finalPositionX - chassis.getXInches();
        double dy = finalPositionY - chassis.getYInches();
        double hyp = Math.sqrt((dx * dx) + (dy * dy));
        double angle = Math.toDegrees(Math.acos(dx / hyp));

        System.out.println("angle" + angle);

        super.addCommands(new TurnToAngle(chassis, angle, allowableAngleError));
        super.addCommands(new DriveDistance(chassis, hyp, allowableDistanceError));
    }
}
