package org.usfirst.frc.team3504.robot.commands.autonomous;


import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.Robot;


/**
 *
 */
public class AutoDriveForward extends Command {

    @SuppressWarnings("unused")
    private final double inches;
    private final double speed;


    public AutoDriveForward(double inches, double speed)
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.chassis);
        this.inches = inches;
        this.speed = speed;

    }


    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.chassis.driveSpeed(speed);

    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
        System.out.println("Stopped");
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
