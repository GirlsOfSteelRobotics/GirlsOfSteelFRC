package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristToCollect extends Command {

    public WristToCollect() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.wrist.setGoalWristPosition(Wrist.WRIST_COLLECT);
        System.out.println("WristToCollect initialized");
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
    }
}
