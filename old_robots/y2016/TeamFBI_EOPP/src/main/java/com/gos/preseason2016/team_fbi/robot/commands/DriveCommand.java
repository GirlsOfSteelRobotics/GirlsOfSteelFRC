package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2016.team_fbi.robot.OI;
import com.gos.preseason2016.team_fbi.robot.subsystems.Drive;

/**
 *
 */
public class DriveCommand extends CommandBase {

    private final OI m_oi;
    private final Drive m_drive;

    public DriveCommand(OI oi, Drive drive) {
        m_oi = oi;
        m_drive = drive;
        addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_drive.moveByJoystick(m_oi.getChassisJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_drive.stop();
    }


}
