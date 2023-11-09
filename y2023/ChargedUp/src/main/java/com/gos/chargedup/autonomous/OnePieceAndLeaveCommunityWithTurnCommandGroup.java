package com.gos.chargedup.autonomous;


import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.lib.GetAllianceUtil;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;
import java.util.List;


public class OnePieceAndLeaveCommunityWithTurnCommandGroup extends SequentialCommandGroup {
    public OnePieceAndLeaveCommunityWithTurnCommandGroup(ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot,
                                                         ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path,
                                                         AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        List<PathPlannerTrajectory> onePieceAndLeave = PathPlanner.loadPathGroup(path, false, new PathConstraints(Units.inchesToMeters(36), Units.inchesToMeters(36)));
        Command driveAutoOnePieceAndLeave = chassis.createFollowPathCommand(onePieceAndLeave, new HashMap<>());

        Pose2d initialPose = onePieceAndLeave.get(0).getInitialPose();

        //0.6 away
        Pose2d startPose = new Pose2d(new Translation2d(
            initialPose.getTranslation().getX() - Units.inchesToMeters(6),
            initialPose.getTranslation().getY()),
            Rotation2d.fromDegrees(180));
        Command resetOdometry = new ConditionalCommand(
            chassis.createResetOdometryCommand(startPose),
            chassis.createResetOdometryCommand(AllianceFlipper.flip(startPose)),
            GetAllianceUtil::isBlueAlliance
        );
        addCommands(resetOdometry);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));

        Command driveBackwards = new ConditionalCommand(
            chassis.createDriveToPointNoFlipCommand(startPose, new Pose2d(initialPose.getTranslation(), Rotation2d.fromDegrees(180)), true),
            chassis.createDriveToPointNoFlipCommand(AllianceFlipper.flip(startPose), AllianceFlipper.flip(new Pose2d(initialPose.getTranslation(), Rotation2d.fromDegrees(180))), true),
            GetAllianceUtil::isBlueAlliance
        );

        addCommands(driveBackwards
            .alongWith(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //turn to start pos
        Command turnToAngle = new ConditionalCommand(
            chassis.createTurnToAngleCommand(initialPose.getRotation().getDegrees()),
            chassis.createTurnToAngleCommand(AllianceFlipper.flip(initialPose.getRotation()).getDegrees()),
            GetAllianceUtil::isBlueAlliance
        );
        addCommands(
            turnToAngle
                .raceWith(new WaitCommand(100)
                    .alongWith(CombinedCommandsUtil.goHome(armPivot, armExtension))));
        addCommands(new PrintCommand("turn at point"));


        //drive out of community
        addCommands(driveAutoOnePieceAndLeave);
        addCommands(new PrintCommand("leave"));

    }
}
