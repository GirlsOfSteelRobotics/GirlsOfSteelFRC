package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class Turn180 extends Command {

    public Turn180(Chassis chassis) {
        addRequirements(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
