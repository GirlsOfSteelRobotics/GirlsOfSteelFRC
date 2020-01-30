package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class RunShooterRPM extends CommandBase {

    private final Shooter m_shooter;
    private final double m_goalRPM;

    public RunShooterRPM(Shooter shooter, double goalRPM) {
        this.m_shooter = shooter;
        this.m_goalRPM = goalRPM;

        super.addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.setRPM(m_goalRPM);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
