package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveLeft extends Command {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoDriveLeft(Chassis chassis, double distance) {
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
        m_chassis.autoDriveLeft(m_distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_chassis.getDistanceLeft() > m_distance); // 107
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
