package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class AutoFirstPickup extends Command {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoFirstPickup(Chassis chassis, double distance) {
        m_chassis = chassis;
        requires(m_chassis);
        this.m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.autoDriveRight(m_distance); // 22.25
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_chassis.getDistanceLeft() > m_distance);
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
