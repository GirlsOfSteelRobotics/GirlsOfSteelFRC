package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoPivotDown extends Command {

    private final Timer tim;
    private final double time;

    public AutoPivotDown(double time) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.pivot);
        this.time = time;
        tim = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.pivot.tiltUpandDown(-0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return tim.get() >= time;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.pivot.tiltUpandDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
