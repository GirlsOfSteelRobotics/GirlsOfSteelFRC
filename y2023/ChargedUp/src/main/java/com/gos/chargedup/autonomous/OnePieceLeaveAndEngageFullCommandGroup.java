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
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class OnePieceLeaveAndEngageFullCommandGroup extends SequentialCommandGroup {

    public OnePieceLeaveAndEngageFullCommandGroup(ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType, String path) {

        List<PathPlannerTrajectory> driveOverStation = PathPlanner.loadPathGroup(path, true,
            Constants.DEFAULT_PATH_CONSTRAINTS,
            Constants.DEFAULT_PATH_CONSTRAINTS);
        Command driveForwardOverChargingStation1 = chassis.createFollowPathCommand(driveOverStation);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));

        //drive
        addCommands(driveForwardOverChargingStation1
            .alongWith(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //engage
        addCommands(chassis.createAutoEngageCommand());
    }
}
