package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TuneChassisVelocityCommand extends CommandBase {
    private static final GosDoubleProperty CHASSIS_VELOCITY = new GosDoubleProperty(false, "Tuning.Chassis.Velocity", 0);

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
