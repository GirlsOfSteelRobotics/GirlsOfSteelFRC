package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Drive;

public class AutoDrive extends Command {

    private final Drive m_drive;

    public AutoDrive(Drive drive) {
        m_drive = drive;
        requires(m_drive);
    }

    @Override
    protected void initialize() {
        setTimeout(3);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        m_drive.driveAuto();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub
        m_drive.stop();
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub
        end();
    }

}
