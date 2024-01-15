package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.infinite_recharge.subsystems.Chassis;

public class VelocityControlDrivingTuning extends Command {

    private final Chassis m_chassis;

    public VelocityControlDrivingTuning(Chassis chassis) {
        this.m_chassis = chassis;

        super.addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.smartVelocityControl(20, 20);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
