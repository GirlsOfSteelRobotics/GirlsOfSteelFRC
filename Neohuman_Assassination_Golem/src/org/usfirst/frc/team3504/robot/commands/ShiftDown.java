package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Shifters.Speed;

/**
 *
 */
public class ShiftDown extends Command {

    public ShiftDown() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shifters);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.shifters.shiftLeft(Speed.kLow);
		Robot.shifters.shiftRight(Speed.kLow);
		Robot.ledlights.redLight();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		// The solenoid setting commands should complete immediately
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
