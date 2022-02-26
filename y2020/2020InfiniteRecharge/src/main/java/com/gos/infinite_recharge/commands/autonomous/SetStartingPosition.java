package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetStartingPosition extends CommandBase {

    private final Chassis m_chassis;
    private final double m_xValue;
    private final double m_yValue;
    private final double m_angle;
    private final int m_loopsToLock;
    private int m_loopsRun;

    public SetStartingPosition(Chassis chassis, double x, double y, double angle) {
        m_chassis = chassis;
        m_xValue = x;
        m_yValue = y;
        m_angle = angle;
        m_loopsToLock = 10;
        addRequirements(chassis);
    }

    public SetStartingPosition(Chassis chassis, Pose2d startingPosition) {
        this(chassis,
                Units.metersToInches(startingPosition.getX()),
                Units.metersToInches(startingPosition.getY()),
                startingPosition.getRotation().getDegrees());
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.setPositionInches(m_xValue, m_yValue, m_angle);
        ++m_loopsRun;
    }

    @Override
    public boolean isFinished() {
        return m_loopsRun > m_loopsToLock;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
