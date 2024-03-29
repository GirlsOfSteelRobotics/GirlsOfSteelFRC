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
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.HashMap;
import java.util.Map;

public class OnePieceANDGrabSecondCommandGroup extends SequentialCommandGroup {
    public OnePieceANDGrabSecondCommandGroup(ChassisSubsystemInterface chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType, String pathStart, String pathEnd) {
        Map<String, Command> eventMap = new HashMap<>();
        eventMap.put("GrabPiece", CombinedCommandsUtil.goToGroundPickup(armPivot, armExtension, 10, 200000));

        Command driveToPiece = chassis.createFollowPathCommand(pathStart);
        Command driveToGetSecondPiece = chassis.createFollowPathCommand(pathEnd);

        //score piece
        addCommands(new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));

        //first part
        addCommands(driveToPiece
            .alongWith(armPivot.createPivotToAngleAndHoldCommand(-10))
            .alongWith(Commands.waitSeconds(0.25)
                .andThen(armExtension.createMiddleExtensionCommand())));

        //turn 180
        addCommands(chassis.createTurnToAngleCommand(0));

        //drive second part
        addCommands(driveToGetSecondPiece
            .raceWith(claw.createMoveClawIntakeInCommand()));

        //turn 180
        addCommands(chassis.createTurnToAngleCommand(180)
            .raceWith(CombinedCommandsUtil.goHome(armPivot, armExtension)));
    }
}
