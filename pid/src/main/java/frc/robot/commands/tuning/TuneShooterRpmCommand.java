package frc.robot.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.properties.PropertyManager;
import frc.robot.subsystems.ShooterSubsystem;


public class TuneShooterRpmCommand extends CommandBase {
    private static final PropertyManager.DoubleProperty SHOOTER_RPM = new PropertyManager.DoubleProperty("Tuning.Shooter.RPM", 1500);

    private final ShooterSubsystem m_spinningWheelSubsystem;

    public TuneShooterRpmCommand(ShooterSubsystem spinningWheelSubsystem) {
        m_spinningWheelSubsystem = spinningWheelSubsystem;
        addRequirements(this.m_spinningWheelSubsystem);
    }

    @Override
    public void execute() {
        m_spinningWheelSubsystem.spinAtRpm(SHOOTER_RPM.getValue());
    }
}
