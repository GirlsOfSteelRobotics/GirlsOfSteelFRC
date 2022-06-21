package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;

public class ConveyorAdvanceOneUnit extends CommandBase {
    private final ShooterConveyor m_shooterConveyor;

    public ConveyorAdvanceOneUnit(ShooterConveyor shooterConveyor) {
        this.m_shooterConveyor = shooterConveyor;
        super.addRequirements(shooterConveyor);
    }

    @Override
    public void initialize() {
        m_shooterConveyor.advanceBall();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterConveyor.stop();
    }

    @Override
    public boolean isFinished() {
        return m_shooterConveyor.isAdvanced();
    }
}
