package org.usfirst.frc.team3504.robot.commands.camera;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchCam extends Command {

    public SwitchCam() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.camera.switchCam();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
