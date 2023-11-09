package com.gos.codelabs.pid.commands.auton;

import com.gos.codelabs.pid.subsystems.ChassisSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

public class SetRobotPoseCommand extends Command {
    private final int m_loopsToRun;
    private final ChassisSubsystem m_chassis;
    private final Pose2d m_pose;
    private int m_loops;

    public SetRobotPoseCommand(ChassisSubsystem chassis, Pose2d pose) {
        this(chassis, pose, 50);
    }

    public SetRobotPoseCommand(ChassisSubsystem chassis, Pose2d pose, int loopsToRun) {
        m_loopsToRun = loopsToRun;
        m_pose = pose;
        m_chassis = chassis;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public void initialize() {
        m_loops = 0;
    }

    @Override
    public void execute() {
        m_chassis.resetOdometry(m_pose);
        ++m_loops;
    }

    @Override
    public boolean isFinished() {
        return m_loops >= m_loopsToRun;
    }
}
