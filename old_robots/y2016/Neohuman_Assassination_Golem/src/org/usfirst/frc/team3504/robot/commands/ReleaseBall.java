package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


public class ReleaseBall extends Command {

    public ReleaseBall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        if(RobotMap.USING_CLAW) {
            requires(Robot.claw);
        }
        else {
            requires(Robot.shooter);
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(RobotMap.USING_CLAW) {
            Robot.claw.collectRelease(.8);
        }
        else {
            Robot.shooter.spinWheels(-1);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        if(RobotMap.USING_CLAW) {
            Robot.claw.stopCollecting();
        }
        else {
            Robot.shooter.stop();
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
