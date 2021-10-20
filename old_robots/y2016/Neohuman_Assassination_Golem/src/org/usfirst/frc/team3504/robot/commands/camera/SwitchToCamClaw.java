package org.usfirst.frc.team3504.robot.commands.camera;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchToCamClaw extends Command {

    public SwitchToCamClaw() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.camera.switchToCamClaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
