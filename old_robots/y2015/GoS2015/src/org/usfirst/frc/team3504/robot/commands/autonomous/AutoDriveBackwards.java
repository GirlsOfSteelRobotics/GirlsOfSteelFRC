package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**

 */
public class AutoDriveBackwards extends Command {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoDriveBackwards(Chassis chassis, double distance) {
        m_chassis = chassis;
        requires(m_chassis);
        m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // m_chassis.r(m_chassis.getFrontLeftEncoderDistance());
        // come back to because encoder distance is not being printed on smart
        // dashboard
        // need to make this method
        m_chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.autoDriveBackward(m_distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_chassis.getDistanceBackwards() > m_distance); // 50
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
