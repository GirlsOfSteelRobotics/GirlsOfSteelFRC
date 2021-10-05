package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristToSwitch extends Command {

    public WristToSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_wrist);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_wrist.setGoalWristPosition(Wrist.WRIST_IN_BOUND);
        System.out.println("WristToSwitch initialized");
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
