package com.gos.chargedup.subsystems;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.HashMap;
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

    void clearStickyFaults();

    void setChassisSpeed(ChassisSpeeds speed);

    ChassisSpeeds getChassisSpeed();

    Command createDriveToPointCommand(Pose2d point, boolean reverse);

    Command createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse);

    Command createAutoEngageCommand();

    Command createResetOdometryCommand(Pose2d pose2d);

    Command createSyncOdometryWithPoseEstimatorCommand();

    default Command createFollowPathCommand(String pathFilename, boolean isReversed, PathConstraints constraint, PathConstraints... constraints) {
        return createFollowPathCommand(pathFilename, isReversed, new HashMap<>(), constraint, constraints);
    }

    default Command createFollowPathCommand(String pathFilename, boolean isReversed, Map<String, Command> eventMap, PathConstraints constraint, PathConstraints... constraints) {
        List<PathPlannerTrajectory> path = PathPlanner.loadPathGroup(pathFilename, isReversed, constraint, constraints);
        return createFollowPathCommand(path, eventMap);
    }

    Command createFollowPathCommand(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    default Command createFollowPathCommandNoPoseReset(String pathFile, boolean isReversed, Map<String, Command> events, PathConstraints constraint)  {
        List<PathPlannerTrajectory> path = PathPlanner.loadPathGroup(pathFile, isReversed, constraint, constraint);
        return createFollowPathCommandNoPoseReset(path, events);
    }

    default Command createFollowPathCommandNoPoseReset(String pathFile, boolean isReversed, PathConstraints constraint) {
        return createFollowPathCommandNoPoseReset(pathFile, isReversed, new HashMap<>(), constraint);
    }

    Command createFollowPathCommandNoPoseReset(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    Command createDeferredDriveToPointCommand(Pose2d point, boolean reverse);

    Command createResetPoseCommand(PathPlannerTrajectory trajectory, Rotation2d startAngle);

    Command createTurnToAngleCommand(double angleGoal);

    Command createSelfTestMotorsCommand();
}
