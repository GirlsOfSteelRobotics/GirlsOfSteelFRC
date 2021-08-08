package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class SetHoodAngleCommand extends CommandBase {

    private final Shooter m_shooter;
    private final double m_goalAngle;

    public SetHoodAngleCommand(Shooter shooter, double goalAngle) {
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(shooter);
        m_shooter = shooter;
        m_goalAngle = goalAngle;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setHoodAngle(m_goalAngle);
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isHoodAtAngle();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopShooter();
    }
}
