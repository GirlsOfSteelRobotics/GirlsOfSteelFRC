package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveRight extends Command {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoDriveRight(Chassis chassis, double distance) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        //this.distance = distance;
        // distance = 50; //107
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
        m_chassis.autoDriveRight(m_distance);
        m_chassis.printPositionsToSmartDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_chassis.getDistanceRight() >= m_distance);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
