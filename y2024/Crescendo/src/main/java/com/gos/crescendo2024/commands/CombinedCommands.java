package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {


    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToAngle(ArmPivotSubsystem.ARM_INTAKE_ANGLE.getValue())
            .alongWith(intake.createMoveIntakeInCommand())
            .withName("Intake Piece");
    }

    public static Command baseShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, double angle) {
        return armPivot.createMoveArmToAngle(angle)
            .andThen(shooter.createSetRPMCommand(ShooterSubsystem.SHOOTER_SPEED.getValue()))
            .withName("Basic Shoot");
    }

    public static Command speakerShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, ChassisSubsystem chassis) {
        return chassis.createTurnToPointDrive(0, 0, FieldConstants.Speaker.CENTER_SPEAKER_OPENING);
            //.alongWith(baseShooterCommand(armPivot, shooter, ArmPivotSubsystem.ARM_SPEAKER_ANGLE.getValue()));
    }

    public static Command ampShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter) {
        return baseShooterCommand(armPivot, shooter, ArmPivotSubsystem.ARM_AMP_ANGLE.getValue());
    }


}

