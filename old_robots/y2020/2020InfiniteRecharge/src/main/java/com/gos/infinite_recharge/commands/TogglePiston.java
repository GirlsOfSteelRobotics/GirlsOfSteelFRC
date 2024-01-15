package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.infinite_recharge.subsystems.ShooterIntake;

public class TogglePiston extends Command {

    private final ShooterIntake m_shooterIntake;

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public TogglePiston(ShooterIntake shooterIntake, boolean intake) {
        this.m_shooterIntake = shooterIntake;

        super.addRequirements(shooterIntake);
    }

    @Override
    public void initialize() {
        m_shooterIntake.moveToCollect();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterIntake.retract();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
