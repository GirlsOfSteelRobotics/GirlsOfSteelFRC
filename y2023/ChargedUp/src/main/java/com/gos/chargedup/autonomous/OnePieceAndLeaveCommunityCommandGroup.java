package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;

public class OnePieceAndLeaveCommunityCommandGroup extends SequentialCommandGroup {
    public OnePieceAndLeaveCommunityCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot,
                                                 ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path,
                                                 AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory onePieceAndLeave = PathPlanner.loadPath(path, new PathConstraints(Units.inchesToMeters(24), Units.inchesToMeters(24)), true);
        Command driveAutoOnePieceAndLeave = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(onePieceAndLeave);

        //score
        //addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
          //  .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //0.6 away
        Pose2d startPose = new Pose2d(new Translation2d(onePieceAndLeave.getInitialPose().getTranslation().getX()-0.6, onePieceAndLeave.getInitialPose().getTranslation().getY()), Rotation2d.fromDegrees(180));
        addCommands(chassis.createResetOdometry(startPose));

        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("reset Odom"));

        addCommands(chassis.createDriveToPoint(new Pose2d(onePieceAndLeave.getInitialPose().getTranslation(), Rotation2d.fromDegrees(180)), true));
        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("drive to point "));


        addCommands(chassis.createTurnPID(onePieceAndLeave.getInitialPose().getRotation().getDegrees()));
        addCommands(new WaitCommand(2));
        addCommands(new PrintCommand("turn at point"));


        //drive out of community
        addCommands(driveAutoOnePieceAndLeave);
        addCommands(new PrintCommand("leave"));

    }
}
