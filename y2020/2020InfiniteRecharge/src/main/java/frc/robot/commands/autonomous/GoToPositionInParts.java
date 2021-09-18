package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Chassis;

public class GoToPositionInParts extends SequentialCommandGroup {

    public GoToPositionInParts(Chassis chassis, double finalPositionX, double finalPositionY, double allowableAngleError, double allowableDistanceError) {

        double dx = finalPositionX - chassis.getX();
        double dy = finalPositionY - chassis.getY();
        double hyp = Math.sqrt((dx * dx) + (dy * dy));
        double angle = Math.toDegrees(Math.acos(dx / hyp));

        System.out.println("angle" + angle);

        super.addCommands(new TurnToAngle(chassis, angle, allowableAngleError));
        super.addCommands(new DriveDistance(chassis, hyp, allowableDistanceError));
    }
}
