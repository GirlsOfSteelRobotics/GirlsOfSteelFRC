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

public class OnePieceAndLeaveCommunityCommandGroup extends SequentialCommandGroup {
    public OnePieceAndLeaveCommunityCommandGroup(ChassisSubsystem chassis, ArmPivotSubsystem armPivot,
                                                 ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path,
                                                 AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        PathPlannerTrajectory onePieceAndLeave = PathPlanner.loadPath(path, Constants.DEFAULT_PATH_CONSTRAINTS, true);
        Command driveAutoOnePieceAndLeave = chassis.ramseteAutoBuilder(new HashMap<>()).fullAuto(onePieceAndLeave);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHomeWithoutTurret(armPivot, armExtension)));

        //turn to start
        addCommands(chassis.createTurnPID(onePieceAndLeave.getInitialPose().getRotation().getDegrees()));

        //drive out of community
        addCommands(driveAutoOnePieceAndLeave);
    }
}
