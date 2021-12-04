package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class NudgeFlapUp extends Command {
    Timer time = new Timer();

    public NudgeFlapUp() {

        // Use requires() here to declare subsystem dependencies
        requires(Robot.flap);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.flap.setTalon(-.5);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return time.get() >= .5;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.flap.stopTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
