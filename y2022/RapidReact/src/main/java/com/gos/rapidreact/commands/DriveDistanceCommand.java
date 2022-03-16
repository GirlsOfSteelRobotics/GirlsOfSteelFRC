package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class DriveDistanceCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;
    private final double m_distance;
    private final double m_allowableError;
    private double m_error;
    private double m_leftDistance;
    private double m_rightDistance;


    public DriveDistanceCommand(ChassisSubsystem chassis, double distance, double allowableError) {

        m_chassis = chassis;

        m_distance = distance;
        m_allowableError = allowableError;

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        m_leftDistance = m_chassis.getLeftEncoderDistance() + m_distance;
        m_rightDistance = m_chassis.getRightEncoderDistance() + m_distance;
    }

    @Override
    public void execute() {
        double currentLeftPosition = m_chassis.getLeftEncoderDistance();
        m_error = m_leftDistance - currentLeftPosition;
        m_chassis.trapezoidMotionControl(m_leftDistance, m_rightDistance);

    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_error) < m_allowableError;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
