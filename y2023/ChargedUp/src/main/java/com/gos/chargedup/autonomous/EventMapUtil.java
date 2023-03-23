package com.gos.chargedup.autonomous;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.commands.CombinedCommandsUtil;
import com.gos.chargedup.commands.ScorePieceCommandGroup;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.HashMap;
import java.util.Map;

public class EventMapUtil {

    public static Map<String, Command> createDefaultEventMap(ChassisSubsystem chassis, ArmPivotSubsystem armPivot, ArmExtensionSubsystem armExtension, ClawSubsystem claw, AutoPivotHeight pivotHeightType, GamePieceType gamePieceType) {
        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("pickUpObject", claw.createMoveClawIntakeInWithTimeoutCommand());
        //eventMap.put("resetArmAndTurret", turret.goHome().andThen(CombinedCommandsUtil.goHome(armPivot, armExtension, turret)));
        eventMap.put("setArmAndTurretToScore", CombinedCommandsUtil.moveToScore(pivotHeightType, gamePieceType, armPivot));
        eventMap.put("scorePiece", new ScorePieceCommandGroup(armPivot, armExtension, claw, pivotHeightType, gamePieceType));
        eventMap.put("engage", chassis.createAutoEngageCommand());

        return eventMap;
    }
}
