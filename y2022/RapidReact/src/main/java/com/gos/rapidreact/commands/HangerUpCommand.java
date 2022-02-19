package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HangerSubsystem;


public class HangerUpCommand extends CommandBase {
    private final HangerSubsystem m_hanger;

    public HangerUpCommand(HangerSubsystem hangerSubsystem) {
        this.m_hanger = hangerSubsystem;
        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.setHangerSpeed(HangerSubsystem.HANGER_UP_SPEED);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_hanger.setHangerSpeed(0);

    }
}
