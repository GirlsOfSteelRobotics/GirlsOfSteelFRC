package com.gos.rapidreact.commands.tuning;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

public class VelocityControlDrivingTuningCommand extends Command {

    private final ChassisSubsystem m_chassis;
    private static final GosDoubleProperty GOAL_VELOCITY_FPS = new GosDoubleProperty(false, "Chassis Tune Velocity (ft/s)", 0);
    private static final GosDoubleProperty GOAL_ACCELERATION = new GosDoubleProperty(false, "Chassis Tune Acceleration", 0);

    public VelocityControlDrivingTuningCommand(ChassisSubsystem chassis) {
        this.m_chassis = chassis;

        super.addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double goalMps = Units.feetToMeters(GOAL_VELOCITY_FPS.getValue());
        double goalAccel = Units.feetToMeters(GOAL_ACCELERATION.getValue());
        m_chassis.smartVelocityControl(goalMps, goalMps, goalAccel, goalAccel);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
