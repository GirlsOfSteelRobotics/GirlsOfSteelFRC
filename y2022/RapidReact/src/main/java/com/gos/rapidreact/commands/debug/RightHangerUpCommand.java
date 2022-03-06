package com.gos.rapidreact.commands.debug;

import com.gos.rapidreact.subsystems.HangerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class RightHangerUpCommand extends CommandBase {
    private final HangerSubsystem m_hanger;

    public RightHangerUpCommand(HangerSubsystem hangerSubsystem) {
        this.m_hanger = hangerSubsystem;
        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.setRightHangerSpeed(HangerSubsystem.HANGER_UP_SPEED);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_hanger.setRightHangerSpeed(0);

    }
}
