package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoReleaseBall extends Command {

    private Timer tim;
    private double time;

    public AutoReleaseBall(double time) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.claw);
        this.time = time;
        tim = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.claw.collectRelease(.9);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //tim.get() >= time;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.claw.collectRelease(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
