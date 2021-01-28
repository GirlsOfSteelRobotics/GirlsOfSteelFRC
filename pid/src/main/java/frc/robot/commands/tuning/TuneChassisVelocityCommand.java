package frc.robot.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.properties.PropertyManager;
import frc.robot.subsystems.ChassisSubsystem;

public class TuneChassisVelocityCommand extends CommandBase {
    private static final PropertyManager.DoubleProperty CHASSIS_VELOCITY = new PropertyManager.DoubleProperty("Tuning.Chassis.Velocity", 0);

    private final ChassisSubsystem m_chassisSubsystem;

    public TuneChassisVelocityCommand(ChassisSubsystem chassisSubsystem) {
        m_chassisSubsystem = chassisSubsystem;
        addRequirements(this.m_chassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassisSubsystem.driveWithVelocity(CHASSIS_VELOCITY.getValue(), CHASSIS_VELOCITY.getValue());
    }
}
