package org.usfirst.frc.team3504.robot.commands.autonomous;


import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;


/**
 *
 */
public class AutoDriveForward extends Command {

    @SuppressWarnings("unused")
    private final double m_inches;
    private final double m_speed;
    private final Chassis m_chassis;


    public AutoDriveForward(Chassis chassis, double inches, double speed)
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(chassis);
        m_inches = inches;
        m_speed = speed;
        m_chassis = chassis;
    }


    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveSpeed(m_speed);

    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
        System.out.println("Stopped");
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
