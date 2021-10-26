package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveRight extends Command {

    private double distance;

    public AutoDriveRight(double distance) {
        requires(Robot.chassis);
        //this.distance = distance;
        // distance = 50;//107
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.chassis.autoDriveRight(distance);
        Robot.chassis.printPositionsToSmartDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.chassis.getDistanceRight() >= distance);
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
