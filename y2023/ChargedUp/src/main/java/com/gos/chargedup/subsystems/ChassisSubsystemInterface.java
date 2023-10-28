package com.gos.chargedup.subsystems;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.List;
import java.util.Map;

public interface ChassisSubsystemInterface extends Subsystem {
    double findingClosestNodeY(double yPositionButton);

    Pose2d getPose();

    void stop();

    double getPitch();

    double getYaw();

    void turnToAngle(double angleGoal);

    boolean turnPIDIsAtAngle();

    void autoEngage();

    boolean tryingToEngage();

    void resetOdometry(Pose2d pose2d);

    boolean isInCommunityZone();

    boolean isInLoadingZone();

    boolean canExtendArm();

    void resetStickyFaultsChassis();

    CommandBase createDriveToPointCommand(Pose2d point, boolean reverse);

    CommandBase createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse);

    CommandBase createAutoEngageCommand();

    CommandBase createResetOdometryCommand(Pose2d pose2d);

    CommandBase createSyncOdometryWithPoseEstimatorCommand();

    CommandBase createFollowPathCommand(PathPlannerTrajectory trajectory);

    CommandBase createFollowPathCommand(List<PathPlannerTrajectory> trajectory);

    CommandBase createFollowPathCommand(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    CommandBase createFollowPathCommandNoPoseReset(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    CommandBase createFollowPathCommandNoPoseReset(PathPlannerTrajectory trajectory);

    CommandBase createDeferredDriveToPointCommand(Pose2d point, boolean reverse);

    CommandBase createResetPoseCommand(PathPlannerTrajectory trajectory, Rotation2d startAngle);

    CommandBase createTurnToAngleCommand(double angleGoal);

    CommandBase createSelfTestMotorsCommand();
}
