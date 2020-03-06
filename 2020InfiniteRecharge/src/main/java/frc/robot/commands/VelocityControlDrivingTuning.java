package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class VelocityControlDrivingTuning extends CommandBase {

    private final Chassis m_chassis;

    public VelocityControlDrivingTuning(Chassis chassis) {
        this.m_chassis = chassis;

        super.addRequirements(chassis);
    }
    public void initialize() {
    }

    public void execute() {
        m_chassis.smartVelocityControl(20, 20);
    }

    public void end() {
    }

    public boolean isFinished() {
        return false;
    }
}
