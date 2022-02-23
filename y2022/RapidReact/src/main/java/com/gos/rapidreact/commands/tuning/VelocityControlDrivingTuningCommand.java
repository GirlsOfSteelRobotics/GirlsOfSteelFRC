package com.gos.rapidreact.commands.tuning;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class VelocityControlDrivingTuningCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;

    public VelocityControlDrivingTuningCommand(ChassisSubsystem chassis) {
        this.m_chassis = chassis;

        super.addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.smartVelocityControl(Units.feetToMeters(5), Units.feetToMeters(5));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
