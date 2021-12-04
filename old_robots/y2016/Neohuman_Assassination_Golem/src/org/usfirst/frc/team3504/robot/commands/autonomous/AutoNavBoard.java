package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoNavBoard extends Command {

    private double inches;

    public AutoNavBoard(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        inches = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.chassis.driveSpeed(-.4);
        Robot.chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
         return Robot.chassis.getEncoderDistance() >= inches;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
