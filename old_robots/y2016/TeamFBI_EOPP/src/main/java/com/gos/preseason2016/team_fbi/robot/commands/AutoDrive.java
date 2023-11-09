package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Drive;

public class AutoDrive extends Command {

    private final Drive m_drive;
    private final Timer m_timer;

    public AutoDrive(Drive drive) {
        m_drive = drive;
        m_timer = new Timer();
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        m_drive.driveAuto();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return m_timer.hasElapsed(3);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        m_drive.stop();
        m_timer.stop();
    }



}
