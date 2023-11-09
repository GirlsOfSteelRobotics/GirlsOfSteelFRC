package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;


public class FindChassisTurningCompensationCommand extends Command {
    private static final GosDoubleProperty CHASSIS_SPEED = new GosDoubleProperty(false, "Tuning.Chassis.TurningComp", 0);

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
