package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {


    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToGroundIntakeAngleCommand()
            .alongWith(intake.createMoveIntakeInCommand())
            .until(intake::hasGamePiece)
            .withName("Intake Piece");
    }

    public static Command speakerAimAndShoot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, ChassisSubsystem chassis, IntakeSubsystem intake) {
        return new SpeakerAimAndShootCommand(armPivot, chassis, intake, shooter)
            .withName("Auto shoot into speaker");
    }

    public static Command ampShooterCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToAmpAngleCommand()
            .until(armPivot::isArmAtGoal)
            .andThen(intake.createMoveIntakeOutCommand())
            .withName("Auto shoot into amp");
    }
}

