package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlapDown extends Command {

    public FlapDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.flap);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.flap.setTalon(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        /*if (Robot.flap.getTopLimitSwitch() == true)
            return true;
        else */
            return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.flap.setTalon(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
