package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Chassis;

/**
 *
 */
public class AutoDriveBackwards extends CommandBase {

    private final Chassis m_chassis;
    private final double m_distance;

    public AutoDriveBackwards(Chassis chassis, double distance) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        // m_chassis.r(m_chassis.getFrontLeftEncoderDistance());
        // come back to because encoder distance is not being printed on smart
        // dashboard
        // need to make this method
        m_chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.autoDriveBackward(m_distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_chassis.getDistanceBackwards() > m_distance); // 50
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
