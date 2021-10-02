package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class SetRobotPoseCommand extends CommandBase {
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
