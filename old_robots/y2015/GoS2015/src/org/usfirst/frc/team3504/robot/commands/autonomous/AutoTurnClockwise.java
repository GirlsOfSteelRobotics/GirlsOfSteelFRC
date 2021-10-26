package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurnClockwise extends Command {

    double gyroInitial;

    public AutoTurnClockwise() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gyroInitial = Robot.chassis.getGyroAngle();

        // setTimeout(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.chassis.autoTurnClockwise();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.chassis.getGyroAngle() - gyroInitial) >= 90;

        // return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
