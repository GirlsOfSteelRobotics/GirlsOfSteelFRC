package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignLeftRight extends CommandBase {

    private final Limelight m_limelight;
    private final Chassis m_chassis;

    public AlignLeftRight(Chassis chassis, Limelight limelight) {
        this.m_limelight = limelight;
        this.m_chassis = chassis;

        addRequirements(limelight, chassis);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.setSteer(m_limelight.getSteerCommand());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
