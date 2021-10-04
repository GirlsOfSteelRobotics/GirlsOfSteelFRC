package frc.robot.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.properties.PropertyManager;
import frc.robot.subsystems.ShooterSubsystem;

public class FindShooterFFGainCommand extends CommandBase {

    private static final PropertyManager.DoubleProperty SHOOTER_SPEED = new PropertyManager.DoubleProperty("Tuning.Shooter.FFSpeed", 0);
    private final ShooterSubsystem m_shooter;

    public FindShooterFFGainCommand(ShooterSubsystem shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void execute() {
        m_shooter.setPercentOutput(SHOOTER_SPEED.getValue());
    }

}
