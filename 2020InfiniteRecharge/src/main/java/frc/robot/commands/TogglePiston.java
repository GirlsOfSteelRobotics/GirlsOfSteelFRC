package frc.robot.commands; 

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterIntake;

public class TogglePiston extends CommandBase {

    private final ShooterIntake m_shooterIntake;
    private final boolean m_intake;

    public TogglePiston(ShooterIntake shooterIntake, boolean intake) {
        this.m_shooterIntake = shooterIntake;
        this.m_intake = intake;

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
