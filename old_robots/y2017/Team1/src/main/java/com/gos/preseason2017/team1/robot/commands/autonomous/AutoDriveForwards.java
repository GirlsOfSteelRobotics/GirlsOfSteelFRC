package com.gos.preseason2017.team1.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2017.team1.robot.subsystems.DriveSystem;

/**
 *
 */
public class AutoDriveForwards extends Command {

    private final DriveSystem m_driveSystem;
    private final double m_inches;
    private final double m_speed;

    public AutoDriveForwards(DriveSystem driveSystem, double distance, double speed) {
        m_driveSystem = driveSystem;
        m_inches = distance;
        m_speed = speed;

        requires(m_driveSystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_driveSystem.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_driveSystem.driveSpeed(m_speed);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_driveSystem.getEncoderDistance() >= Math.abs(m_inches); //competition bot
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
