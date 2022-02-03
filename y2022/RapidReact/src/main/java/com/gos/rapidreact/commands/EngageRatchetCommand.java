package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HangerSubsystem;


public class EngageRatchetCommand extends CommandBase {
    private final HangerSubsystem m_hanger;

    public EngageRatchetCommand(HangerSubsystem hangerSubsystem) {
        this.m_hanger = hangerSubsystem;
        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.engageRatchet();

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
