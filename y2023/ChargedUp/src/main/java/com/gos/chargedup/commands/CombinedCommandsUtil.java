package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public final class CombinedCommandsUtil {

    private CombinedCommandsUtil() {

    }

    public static CommandBase goHome(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return extension.commandFullRetract()
            .alongWith(Commands.waitUntil(() -> pivot.getAbsoluteEncoderAngle2() > -40)
                .andThen(pivot.commandGoHome()))
            .withName("Go Home Without Turret");
    }

    //public static CommandBase goHomeWithTurret(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension, TurretSubsystem turret) {
    //    return extension.commandFullRetract()
    //        .andThen(pivot.commandGoHome()
    //            .alongWith(turret.goHome()))
    //        .withName("Go Home With Turret");
    //}

    public static CommandBase goToGroundPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return pivot.commandGoToGroundPickupAndHold()
            .andThen(extension.commandMiddleRetract())
            .withName("Go To Ground Pickup");
    }

    public static CommandBase goToGroundPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension, double allowableError, double velocityAllowableError) {
        return pivot.commandGoToGroundPickupAndHold(allowableError, velocityAllowableError)
            .andThen((extension.commandMiddleRetract().andThen(new PrintCommand("Ran middle retract"))))
            .withName("Go To Ground Pickup");
    }

    public static CommandBase armToHpPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return pivot.commandHpPickupHold()
            .alongWith(Commands.waitUntil(() -> (pivot.getAbsoluteEncoderAngle2() > -40)).andThen(extension.commandMiddleRetract()))
            .withName("HP Pickup");
    }

    public static CommandBase moveToScore(AutoPivotHeight height, GamePieceType gamePiece, ArmPivotSubsystem armPivot) {
        return new ParallelCommandGroup(
            //turret.commandTurretPID(turretAngle),
            armPivot.commandMoveArmToPieceScorePositionAndHold(height, gamePiece) //set for second piece
        ).withName("Move To Score");
    }
}
