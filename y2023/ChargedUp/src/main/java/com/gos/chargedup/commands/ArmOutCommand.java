package com.gos.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmSubsystem;


public class ArmOutCommand extends CommandBase {
    private final ArmSubsystem m_armSubsystem;


    public ArmOutCommand(ArmSubsystem armSubsystem) {
        this.m_armSubsystem = armSubsystem;
        addRequirements(this.m_armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_armSubsystem.out();


    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
