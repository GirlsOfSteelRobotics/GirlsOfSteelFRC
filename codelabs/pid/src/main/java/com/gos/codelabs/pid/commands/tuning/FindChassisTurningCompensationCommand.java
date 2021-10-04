package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.properties.PropertyManager;


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
