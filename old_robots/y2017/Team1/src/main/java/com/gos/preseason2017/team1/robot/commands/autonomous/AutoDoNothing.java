package com.gos.preseason2017.team1.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2017.team1.robot.subsystems.DriveSystem;

/**
 *
 */
public class AutoDoNothing extends Command {

    public AutoDoNothing(DriveSystem chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
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
    }
}
