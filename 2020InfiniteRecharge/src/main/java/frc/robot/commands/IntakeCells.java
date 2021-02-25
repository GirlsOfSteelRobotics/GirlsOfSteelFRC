package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterIntake;

public class IntakeCells extends CommandBase {

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
