package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToSwitch extends Command {


    public LiftToSwitch() {
        requires(Robot.m_lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_lift.setLiftToSwitch();
        System.out.println("LiftToSwitch initialized");
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
