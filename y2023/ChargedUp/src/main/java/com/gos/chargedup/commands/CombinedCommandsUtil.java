package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public final class CombinedCommandsUtil {

    private CombinedCommandsUtil() {

    }

    public static CommandBase goHome(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension, TurretSubsystem turret) {
        return extension.commandMiddleRetract()
            .alongWith(pivot.commandGoHome())
            .alongWith(turret.goHome())
            .withName("Go Home");
    }

    public static CommandBase armToHpPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return pivot.commandHpPickupHold()
            .alongWith(extension.commandMiddleRetract())
            .withName("HP Pickup");
    }

    public static CommandBase moveToScore(double turretAngle, AutoPivotHeight height, GamePieceType gamePiece, TurretSubsystem turret, ArmPivotSubsystem armPivot) {
        return new ParallelCommandGroup(
            turret.commandTurretPID(turretAngle),
            armPivot.commandMoveArmToPieceScorePositionAndHold(height, gamePiece) //set for second piece
        ).withName("Move To Score");
    }
}
