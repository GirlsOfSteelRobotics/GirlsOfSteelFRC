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

    // INTENTIONALLY ROLL, WE ARE NOT BEING PSYCHOPATHS I PROMISE
    double getPitch();

    double getYaw();

    void turnPID(double angleGoal);

    boolean turnPIDIsAtAngle();

    void autoEngage();

    boolean tryingToEngage();

    void resetOdometry(Pose2d pose2d);

    boolean isInCommunityZone();

    boolean isInLoadingZone();

    boolean canExtendArm();

    @SuppressWarnings("PMD.AvoidReassigningParameters")
    CommandBase driveToPoint(Pose2d point, boolean reverse);

    CommandBase driveToPointNoFlip(Pose2d start, Pose2d end, boolean reverse);

    void resetStickyFaultsChassis();

    CommandBase createAutoEngageCommand();

    CommandBase createResetOdometry(Pose2d pose2d);

    CommandBase syncOdometryWithPoseEstimator();

    CommandBase createPathPlannerBuilder(PathPlannerTrajectory trajectory);

    CommandBase createPathPlannerBuilder(List<PathPlannerTrajectory> trajectory);

    CommandBase createPathPlannerBuilder(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    CommandBase createPathPlannerBuilderNoPoseReset(List<PathPlannerTrajectory> trajectory, Map<String, Command> events);

    CommandBase createPathPlannerBuilderNoPoseReset(PathPlannerTrajectory trajectory);

    CommandBase createDriveToPoint(Pose2d point, boolean reverse);

    CommandBase resetPose(PathPlannerTrajectory trajectory, Rotation2d startAngle);

    CommandBase createTurnPID(double angleGoal);

    CommandBase selfTestMotors();
}
