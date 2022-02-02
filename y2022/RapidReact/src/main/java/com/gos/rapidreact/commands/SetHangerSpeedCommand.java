package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HangerSubsystem;


public class SetHangerSpeedCommand extends CommandBase {
    private final HangerSubsystem m_hanger;
    private final XboxController m_joystick;

    public SetHangerSpeedCommand(HangerSubsystem hangerSubsystem, XboxController joystick) {
        this.m_hanger = hangerSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_hanger);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_hanger.setHangerSpeed(m_joystick.getLeftY());

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_hanger.setHangerSpeed(0);

    }
}
