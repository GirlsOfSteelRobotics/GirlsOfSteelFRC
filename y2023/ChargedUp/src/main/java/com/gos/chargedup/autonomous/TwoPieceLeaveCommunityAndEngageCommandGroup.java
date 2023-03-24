package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;

public class TwoPieceLeaveCommunityAndEngageCommandGroup extends SequentialCommandGroup {
    public TwoPieceLeaveCommunityAndEngageCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory twoPieceLeaveAndEngageBefore = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoTwoPieceLeaveCommunityAndEngageBefore = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(twoPieceLeaveAndEngageBefore);
        PathPlannerTrajectory twoPieceLeaveAndEngageAfter = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoTwoPieceLeaveCommunityAndEngageAfter = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(twoPieceLeaveAndEngageAfter);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //turn to start
        addCommands(chassis.createTurnPID(twoPieceLeaveAndEngageBefore.getInitialPose().getRotation().getDegrees()));

        //drive out of community across engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageBefore);

        //grab piece
        addCommands(armPivot.commandGoToGroundPickup());
        addCommands(claw.createMoveClawIntakeInCommand());
        addCommands(armPivot.commandHpPickupHold());

        //turn to go back
        addCommands(chassis.createTurnPID(twoPieceLeaveAndEngageAfter.getInitialPose().getRotation().getDegrees()));

        //engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageAfter);
    }
}
