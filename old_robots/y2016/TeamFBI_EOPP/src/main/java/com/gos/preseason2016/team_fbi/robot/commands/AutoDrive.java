package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2016.team_fbi.robot.subsystems.Drive;

public class AutoDrive extends CommandBase {

    private final Drive m_drive;

    public AutoDrive(Drive drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        setTimeout(3);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        m_drive.driveAuto();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        m_drive.stop();
    }



}
