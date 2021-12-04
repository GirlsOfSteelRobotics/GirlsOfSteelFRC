package org.usfirst.frc.team3504.robot.commands.drive;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByJoystick extends Command {

    Joystick chassisJoystick;

    public DriveByJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        chassisJoystick = Robot.oi.getChassisJoystick();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.chassis.printPositionsToSmartDashboard();
        Robot.chassis.moveByJoystick(chassisJoystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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
