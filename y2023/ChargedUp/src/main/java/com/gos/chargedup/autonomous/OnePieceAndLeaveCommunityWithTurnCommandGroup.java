package com.gos.chargedup.autonomous;


import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;

public class OnePieceAndLeaveCommunityWithTurnCommandGroup extends SequentialCommandGroup {
    public OnePieceAndLeaveCommunityWithTurnCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot,
                                                         ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path,
                                                         AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory onePieceAndLeave = PathPlanner.loadPath(path, new PathConstraints(Units.inchesToMeters(24), Units.inchesToMeters(24)), true);
        Command driveAutoOnePieceAndLeave = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(onePieceAndLeave);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //0.6 away
        Pose2d startPose = new Pose2d(new Translation2d(onePieceAndLeave.getInitialPose().getTranslation().getX() - 0.6, onePieceAndLeave.getInitialPose().getTranslation().getY()), Rotation2d.fromDegrees(180));
        addCommands(chassis.createResetOdometry(startPose));
        addCommands(new PrintCommand("reset Odom to correct initial pose "));

        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("reset Odom"));

        Pose2d realTrajectoryStart = onePieceAndLeave.getInitialPose();

        addCommands(new ConditionalCommand(
            chassis.driveToPointNoFlip(startPose, new Pose2d(realTrajectoryStart.getTranslation(), Rotation2d.fromDegrees(180)), true),
            chassis.driveToPointNoFlip(AllianceFlipper.flip(startPose), AllianceFlipper.flip(new Pose2d(realTrajectoryStart.getTranslation(), Rotation2d.fromDegrees(180))), true),
            () -> DriverStation.getAlliance() == DriverStation.Alliance.Blue
        ));

        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("drive to point"));


        //turn to start pos
        addCommands(chassis.createTurnPID(onePieceAndLeave.getInitialPose().getRotation().getDegrees()));
        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("turn at point"));


        //drive out of community
        addCommands(driveAutoOnePieceAndLeave);
        addCommands(new PrintCommand("leave"));

    }
}
