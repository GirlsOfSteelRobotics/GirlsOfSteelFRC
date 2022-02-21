package com.gos.rapidreact.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class SetInitialOdometryCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final double m_xPosition;
    private final double m_yPosition;
    private final double m_anglePositionDegrees;

    //position parameters are for starting position at the beginning of autonomous
    public SetInitialOdometryCommand(ChassisSubsystem chassisSubsystem, double xPosition, double yPosition, double anglePositionDegrees) {
        this.m_chassis = chassisSubsystem;
        m_xPosition = xPosition;
        m_yPosition = yPosition;
        m_anglePositionDegrees = anglePositionDegrees;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Pose2d pose2d = new Pose2d(m_xPosition, m_yPosition, Rotation2d.fromDegrees(m_anglePositionDegrees));
        m_chassis.resetInitialOdometry(pose2d);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
