package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_fbi.robot.OI;
import com.gos.preseason2016.team_fbi.robot.subsystems.Drive;

/**
 *
 */
public class DriveCommand extends Command {

    private final OI m_oi;
    private final Drive m_drive;

    public DriveCommand(OI oi, Drive drive) {
        m_oi = oi;
        m_drive = drive;
        requires(m_drive);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_drive.moveByJoystick(m_oi.getChassisJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
