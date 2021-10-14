package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotMiddle extends Command {

    private static final double EncoderValueUp = -30; //based on initial position
    private static final double EncoderValueMiddle = 0; //TODO: fix these depending on which way is positive for motor
    private static final double EncoderValueDown = 30; //TODO: fix values
    private double encoderToUse = 0;
    private double speed = 0;

    public PivotMiddle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.pivot);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (Robot.pivot.getPosition() == 1) {
            encoderToUse = EncoderValueUp;
            speed = -.1;}
        else if (Robot.pivot.getPosition() == 0)
            encoderToUse = EncoderValueMiddle;
        else {
            encoderToUse = EncoderValueDown;
            speed = .1; }

        Robot.pivot.resetDistance();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.pivot.tiltUpandDown(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (speed == 1)
            return Robot.pivot.getEncoderDistance() <= encoderToUse;
        else if (speed == -1)
            return Robot.pivot.getEncoderDistance() >= encoderToUse;
        else
            return true;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.pivot.tiltUpandDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
