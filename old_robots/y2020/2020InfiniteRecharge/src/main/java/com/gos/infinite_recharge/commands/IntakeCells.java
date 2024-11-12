package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.subsystems.ShooterIntake;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCells extends Command {

    private final ShooterIntake m_shooterIntake;
    private final boolean m_intake;


    public IntakeCells(ShooterIntake shooterIntake, boolean intake) {
        this.m_shooterIntake = shooterIntake;
        this.m_intake = intake;

        super.addRequirements(shooterIntake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (m_intake) {
            m_shooterIntake.collectCells();
        }
        else {
            m_shooterIntake.decollectCells();
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_shooterIntake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
