package frc.robot.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.ChassisSubsystem;


public class FindChassisTurningCompensationCommand extends CommandBase {
    private static final PropertyManager.DoubleProperty CHASSIS_SPEED = new PropertyManager.DoubleProperty("Tuning.Chassis.TurningComp", 0);

    private final ChassisSubsystem m_chassisSubsystem;

    public FindChassisTurningCompensationCommand(ChassisSubsystem chassisSubsystem) {
        m_chassisSubsystem = chassisSubsystem;
        addRequirements(this.m_chassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassisSubsystem.setSpeedAndSteer(0, CHASSIS_SPEED.getValue());
    }
}
