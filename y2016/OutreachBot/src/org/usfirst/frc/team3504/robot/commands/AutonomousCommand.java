package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.AccessoryMotors.Direction;

/**
 *
 */
public class AutonomousCommand extends Command {

    public AutonomousCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.accessoryMotors);
        requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.driveSystem.resetDistance();
		setTimeout(1.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.accessoryMotors.startLeft(Direction.kFwd);
		Robot.accessoryMotors.startRight(Direction.kRev);
		Robot.driveSystem.forward();
		SmartDashboard.putNumber("Encoder Distance", Robot.driveSystem.getEncoderDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.accessoryMotors.stopLeft();
		Robot.accessoryMotors.stopRight();
		Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
