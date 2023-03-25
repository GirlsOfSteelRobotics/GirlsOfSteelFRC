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
    public TwoPieceLeaveCommunityAndEngageCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory twoPieceLeaveAndEngageBefore = PathPlanner.loadPath("OnePieceEngageWithSecondp1", Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoTwoPieceLeaveCommunityAndEngageBefore = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(twoPieceLeaveAndEngageBefore);

        PathPlannerTrajectory twoPieceLeaveAndEngageAfter = PathPlanner.loadPath("OnePieceEngageWithSecondp2", Constants.DEFAULT_PATH_CONSTRAINTS, false);
        Command driveAutoTwoPieceLeaveCommunityAndEngageAfter = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(twoPieceLeaveAndEngageAfter);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //drive out of community across engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageBefore);

        //turn 180
        //addCommands(chassis.createTurnPID(twoPieceLeaveAndEngageBefore.getInitialPose().getRotation().getDegrees()));
        addCommands(chassis.createTurnPID(0));

        //grab piece
        addCommands(armPivot.commandGoToGroundPickup());
        addCommands(claw.createMoveClawIntakeInWithTimeoutCommand());

        //engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageAfter);
        addCommands(chassis.createAutoEngageCommand());
    }
}
