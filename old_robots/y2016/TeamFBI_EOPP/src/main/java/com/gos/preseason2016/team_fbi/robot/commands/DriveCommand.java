package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Drive;

/**
 *
 */
public class DriveCommand extends Command {

    private final Joystick m_chassisJoystick;
    private final Drive m_drive;

    public DriveCommand(Joystick chassisJoystick, Drive drive) {
        m_chassisJoystick = chassisJoystick;
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
        m_drive.moveByJoystick(m_chassisJoystick);
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
