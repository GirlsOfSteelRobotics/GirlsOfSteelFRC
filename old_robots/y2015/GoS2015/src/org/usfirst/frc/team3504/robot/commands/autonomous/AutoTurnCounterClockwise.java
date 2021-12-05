package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurnCounterClockwise extends Command {

    private double gyroInitial;

    public AutoTurnCounterClockwise() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        gyroInitial = Robot.chassis.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.chassis.autoTurnCounterclockwise();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (gyroInitial - Robot.chassis.getGyroAngle()) >= 90;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
