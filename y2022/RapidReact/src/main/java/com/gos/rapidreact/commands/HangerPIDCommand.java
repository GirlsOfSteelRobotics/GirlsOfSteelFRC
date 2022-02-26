package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HangerSubsystem;


public class HangerPIDCommand extends CommandBase {
    private final HangerSubsystem m_hanger;
    private final double m_hangerHeight;

    public HangerPIDCommand(HangerSubsystem hangerSubsystem, double hangerHeight) {
        this.m_hanger = hangerSubsystem;
        m_hangerHeight = hangerHeight;

        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.hangerToHeight(m_hangerHeight);
    }

    @Override
    public boolean isFinished() {
        double error = Math.abs(m_hangerHeight - m_hanger.getHangerHeight());
        return error < HangerSubsystem.ALLOWABLE_ERROR;
    }

    @Override
    public void end(boolean interrupted) {
        //hold position when ended
    }
}
