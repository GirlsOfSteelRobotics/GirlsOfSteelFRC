package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveLeft extends CommandBase {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoDriveLeft(Chassis chassis, double distance) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        this.m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.autoDriveLeft(m_distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_chassis.getDistanceLeft() > m_distance); // 107
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
