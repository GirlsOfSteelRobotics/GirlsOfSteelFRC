package com.gos.chargedup.subsystems;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
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

    CommandBase createDriveToPointCommand(Pose2d point, boolean reverse);

    CommandBase createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse);

    CommandBase createAutoEngageCommand();

    CommandBase createResetOdometryCommand(Pose2d pose2d);

    CommandBase createSyncOdometryWithPoseEstimatorCommand();

    default CommandBase createFollowPathCommand(String pathFilename, boolean isReversed, PathConstraints constraint, PathConstraints... constraints) {
        return createFollowPathCommand(pathFilename, isReversed, new HashMap<>(), constraint, constraints);
    }

    default CommandBase createFollowPathCommand(String pathFilename, boolean isReversed, Map<String, Command> eventMap, PathConstraints constraint, PathConstraints... constraints) {
        List<PathPlannerTrajectory> path = PathPlanner.loadPathGroup(pathFilename, isReversed, constraint, constraints);
        return createFollowPathCommand(path, eventMap);
    }

    CommandBase createFollowPathCommand(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    default CommandBase createFollowPathCommandNoPoseReset(String pathFile, boolean isReversed, Map<String, Command> events, PathConstraints constraint)  {
        List<PathPlannerTrajectory> path = PathPlanner.loadPathGroup(pathFile, isReversed, constraint, constraint);
        return createFollowPathCommandNoPoseReset(path, events);
    }

    default CommandBase createFollowPathCommandNoPoseReset(String pathFile, boolean isReversed, PathConstraints constraint) {
        return createFollowPathCommandNoPoseReset(pathFile, isReversed, new HashMap<>(), constraint);
    }

    CommandBase createFollowPathCommandNoPoseReset(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    CommandBase createDeferredDriveToPointCommand(Pose2d point, boolean reverse);

    CommandBase createResetPoseCommand(PathPlannerTrajectory trajectory, Rotation2d startAngle);

    CommandBase createTurnToAngleCommand(double angleGoal);

    CommandBase createSelfTestMotorsCommand();
}
