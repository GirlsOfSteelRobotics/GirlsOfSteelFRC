package com.gos.chargedup.subsystems;

import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

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

    default Command createFollowPathCommand(String pathFilename) {
        return createFollowPathCommand(pathFilename, true);
    }

    default Command createFollowPathCommand(String pathFilename, boolean resetPose) {
        return createFollowPathCommand(PathPlannerPath.fromPathFile(pathFilename), resetPose);
    }

    Command createFollowPathCommand(PathPlannerPath path, boolean resetPose);

    default Command createFollowPathCommandNoPoseReset(String pathFile)  {
        return createFollowPathCommand(pathFile, false);
    }

    Command createDeferredDriveToPointCommand(Pose2d point, boolean reverse);

    Command createTurnToAngleCommand(double angleGoal);

    Command createSelfTestMotorsCommand();
}
