package frc.robot.commands; 

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterIntake;

public class MovePiston extends CommandBase {

    private final ShooterIntake m_shooterIntake;
    private final boolean m_intake;

    public MovePiston(ShooterIntake shooterIntake, boolean intake) {
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
            m_shooterIntake.moveToCollect();
        }
        else {
            m_shooterIntake.retract();
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_shooterIntake.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
