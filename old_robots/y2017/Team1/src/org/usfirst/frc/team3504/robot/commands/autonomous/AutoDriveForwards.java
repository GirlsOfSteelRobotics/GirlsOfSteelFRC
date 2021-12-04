package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveForwards extends Command {

    private final double inches;
    private final double speed;

    public AutoDriveForwards(double distance, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSystem);
        inches = distance;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSystem.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.driveSystem.driveSpeed(speed);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.driveSystem.getEncoderDistance() >= Math.abs(inches); //competition bot
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
