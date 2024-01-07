package com.gos.rapidreact.commands.debug;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.HangerSubsystem;


public class LeftHangerDownCommand extends Command {
    private final HangerSubsystem m_hanger;

    public LeftHangerDownCommand(HangerSubsystem hangerSubsystem) {
        this.m_hanger = hangerSubsystem;

        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.setLeftHangerSpeed(HangerSubsystem.HANGER_DOWN_SPEED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_hanger.setLeftHangerSpeed(0);
    }
}
