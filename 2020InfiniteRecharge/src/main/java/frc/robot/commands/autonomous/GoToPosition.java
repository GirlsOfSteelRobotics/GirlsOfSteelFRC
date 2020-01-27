package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Chassis;

public class GoToPosition extends SequentialCommandGroup {
    Chassis chassis;

    private double m_finalPositionX;
    private double m_finalPositionY;

	public GoToPosition(Chassis chassis, double finalPositionX, double finalPositionY) {
        this.chassis = chassis;

        m_finalPositionX = finalPositionX;
        m_finalPositionY = finalPositionY;

        double dx;
        double dy; 
        double hyp;
        double angle;

        dx = finalPositionX - chassis.getX();
        dy = finalPositionY - chassis.getY();
        
        hyp = Math.sqrt((dx * dx) + (dy * dy));
        angle = Math.toDegrees(Math.acos(dx / hyp));

        System.out.println("hypotenuse" + hyp + "angle" + angle);

        super.addCommands(new TurnToAngle(chassis, angle, 5));
        super.addCommands(new DriveDistance(chassis, hyp, 1));

		// Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
	}
}