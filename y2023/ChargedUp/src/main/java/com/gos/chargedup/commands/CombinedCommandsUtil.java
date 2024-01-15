package com.gos.chargedup.commands;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.GamePieceType;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public final class CombinedCommandsUtil {

    private CombinedCommandsUtil() {

    }

    public static Command goHome(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return extension.createFullRetractCommand()
            .alongWith(Commands.waitUntil(() -> pivot.getAbsoluteEncoderAngle2() > -40)
            .andThen(pivot.createGoHomeCommand()))
            .withName("Go Home Without Turret");
    }

    //public static Command goHomeWithTurret(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension, TurretSubsystem turret) {
    //    return extension.commandFullRetract()
    //        .andThen(pivot.commandGoHome()
    //            .alongWith(turret.goHome()))
    //        .withName("Go Home With Turret");
    //}

    public static Command goToGroundPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return pivot.createGoToGroundPickupAndHoldCommand()
            .andThen(extension.createMiddleExtensionCommand())
            .withName("Go To Ground Pickup");
    }

    public static Command goToGroundPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension, double allowableError, double velocityAllowableError) {
        return pivot.createGoToGroundPickupAndHoldCommand(allowableError, velocityAllowableError)
            .andThen((extension.createMiddleExtensionCommand().andThen(new PrintCommand("Ran middle retract"))))
            .withName("Go To Ground Pickup");
    }

    public static Command armToHpPickup(ArmPivotSubsystem pivot, ArmExtensionSubsystem extension) {
        return pivot.createGoToHpPickupHoldCommand()
            .alongWith(Commands.waitUntil(() -> (pivot.getAbsoluteEncoderAngle2() > -40)).andThen(extension.createMiddleExtensionCommand()))
            .withName("HP Pickup");
    }

    public static Command moveToScore(AutoPivotHeight height, GamePieceType gamePiece, ArmPivotSubsystem armPivot) {
        return new ParallelCommandGroup(
            //turret.commandTurretPID(turretAngle),
            armPivot.createMoveArmToPieceScorePositionAndHoldCommand(height, gamePiece) //set for second piece
        ).withName("Move To Score");
    }
}
