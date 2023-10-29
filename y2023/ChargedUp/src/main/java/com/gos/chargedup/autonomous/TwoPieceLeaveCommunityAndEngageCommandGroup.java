package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class TwoPieceLeaveCommunityAndEngageCommandGroup extends SequentialCommandGroup {
    public TwoPieceLeaveCommunityAndEngageCommandGroup(ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType, String firstPath, String secondPath) {
        // node 4
        PathPlannerTrajectory twoPieceLeaveAndEngageBefore = PathPlanner.loadPath(firstPath, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoTwoPieceLeaveCommunityAndEngageBefore = chassis.createFollowPathCommand(twoPieceLeaveAndEngageBefore);

        List<PathPlannerTrajectory> twoPieceLeaveAndEngageAfter = PathPlanner.loadPathGroup(secondPath, false, new PathConstraints(Units.inchesToMeters(60), Units.inchesToMeters(60)));
        Command driveAutoTwoPieceLeaveCommunityAndEngageAfter = chassis.createFollowPathCommand(twoPieceLeaveAndEngageAfter);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //drive out of community across engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageBefore);

        //turn 180
        addCommands(chassis.createTurnToAngleCommand(0));

        //grab piece

        addCommands(armPivot.createGoToGroundPickupCommand());
        addCommands(claw.createMoveClawIntakeInWithTimeoutCommand());

        //engage
        addCommands(driveAutoTwoPieceLeaveCommunityAndEngageAfter);
        addCommands(chassis.createAutoEngageCommand());

    }
}
