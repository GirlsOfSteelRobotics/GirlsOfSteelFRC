package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ArmMiddleRetractCommand extends CommandBase {

    private final ArmSubsystem m_armSubsystem;

    public ArmMiddleRetractCommand(ArmSubsystem armSubsystem) {
        m_armSubsystem = armSubsystem;
        addRequirements(this.m_armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_armSubsystem.middleRetract();

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
