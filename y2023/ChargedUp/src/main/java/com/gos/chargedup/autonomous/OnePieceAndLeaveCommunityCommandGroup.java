package com.gos.chargedup.autonomous;


import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class OnePieceAndLeaveCommunityCommandGroup extends SequentialCommandGroup {
    public OnePieceAndLeaveCommunityCommandGroup(ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot,
                                                 ArmExtensionSubsystem armExtension, ClawSubsystem claw, String path,
                                                 AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        Command driveAutoOnePieceAndLeave = chassis.createFollowPathCommand(path);

        //score
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType)
            .andThen(CombinedCommandsUtil.goHome(armPivot, armExtension)));

        //drive out of community
        addCommands(driveAutoOnePieceAndLeave);
    }
}
