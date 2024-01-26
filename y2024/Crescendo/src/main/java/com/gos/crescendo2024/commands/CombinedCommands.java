package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {


    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToAngle(ArmPivotSubsystem.ARM_INTAKE_ANGLE.getValue())
            .alongWith(intake.createMoveIntakeInCommand()).until(intake::hasGamePiece)
            .withName("Intake Piece");
    }

    public static Command baseShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, double angle) {
        return armPivot.createMoveArmToAngle(angle)
            .andThen(shooter.createSetRPMCommand(ShooterSubsystem.SHOOTER_SPEED.getValue()))
            .withName("Basic Shoot");
    }

    public static Command speakerShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, ChassisSubsystem chassis, IntakeSubsystem intake) {
        return new SpeakerShooterCommand(armPivot, chassis, intake, shooter);
    }

    public static Command ampShooterCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        //return baseShooterCommand(armPivot, shooter, ArmPivotSubsystem.ARM_AMP_ANGLE.getValue());
        return armPivot.createMoveArmToAngle(ArmPivotSubsystem.ARM_AMP_ANGLE.getValue()).until(armPivot::isArmAtGoal).andThen(intake.createMoveIntakeOutCommand());
    }


}

