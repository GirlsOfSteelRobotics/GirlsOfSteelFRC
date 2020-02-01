package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class SetStartingPosition extends CommandBase {

    private final Chassis m_chassis;
    private final double m_xValue;
    private final double m_yValue;
    private final double m_angle;

    public SetStartingPosition(Chassis chassis, double x, double y, double angle) {
        m_chassis = chassis;
        m_xValue = x;
        m_yValue = y;
        m_angle = angle;
        addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.setPosition(m_xValue, m_yValue, m_angle);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
