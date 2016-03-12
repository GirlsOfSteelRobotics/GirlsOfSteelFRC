package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveSlowly extends Command {

	private double inches;
	
    public AutoDriveSlowly(double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        inches = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetEncoderDistance();
    	System.out.println("Encoder distance initially: " + Robot.chassis.getEncoderDistance());
    	System.out.println("Inches: " + inches);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.driveSpeed(-.4);

    	System.out.println("Encoder distance: " + Robot.chassis.getEncoderDistance());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.chassis.getEncoderDistance() <= -inches;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    	System.out.println("Stopped");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}