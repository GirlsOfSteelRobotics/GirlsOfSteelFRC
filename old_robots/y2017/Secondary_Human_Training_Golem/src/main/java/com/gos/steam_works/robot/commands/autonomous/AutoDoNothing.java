package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.steam_works.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDoNothing extends Command {

    public AutoDoNothing(Chassis chassis) {
        requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("AutoDoNothing Initialized");
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
        System.out.println("AutoDoNothing Finished.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
