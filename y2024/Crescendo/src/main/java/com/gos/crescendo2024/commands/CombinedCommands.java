package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {
    private CombinedCommands() {

    }

    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToAngle(armPivot.ARM_INTAKE_ANGLE.getValue())
            .alongWith(intake.createMoveIntakeInCommand())
            .withName("Intake Piece");
    }

    public static Command baseShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, double angle, double vel) {
        return armPivot.createMoveArmToAngle(angle)
            .andThen(shooter.createSetRPMCommand(vel))
            .withName("Basic Shoot");
    }

    public static Command SpeakerShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter) {
        return armPivot.createMoveArmToAngle(armPivot.ARM_SPEAKER_ANGLE.getValue())
            .andThen(shooter.createSetRPMCommand(shooter.SHOOTER_SPEED.getValue()))
            .withName("Basic Shoot");
    }



}

