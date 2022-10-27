package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;

public class ConveyorWhileHeld extends CommandBase {
    private final ShooterConveyor m_shooterConveyor;
    private final boolean m_intake;

    public ConveyorWhileHeld(ShooterConveyor shooterConveyor, boolean intake) {
        this.m_shooterConveyor = shooterConveyor;
        this.m_intake = intake;

        super.addRequirements(shooterConveyor);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (m_intake) {
            m_shooterConveyor.inConveyor();
        }
        else {
            m_shooterConveyor.outConveyor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterConveyor.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
