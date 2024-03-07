package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.Supplier;

public class CombinedCommands {

    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToGroundIntakeAngleCommand()
            .alongWith(intake.createMoveIntakeInCommand())
            .until(intake::hasGamePiece)
            .withName("Intake Piece");
    }

    public static Command prepareSpeakerShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, Supplier<Pose2d> pos) {
        return armPivot.createPivotUsingSpeakerTableCommand(pos)
            // TODO(gpr) Make a `shooter.createSetSpeakerRPMCommand` and make 4000 a constant / GosDoubleProperty
            .alongWith(shooter.createSetRPMCommand(4000));
    }

    public static Command prepareSpeakerShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, double angle) {
        return armPivot.createMoveArmToAngleCommand(angle)
            // TODO(gpr) Make a `shooter.createSetSpeakerRPMCommand` and make 4000 a constant / GosDoubleProperty
            .alongWith(shooter.createSetRPMCommand(4000));
    }

    public static Command prepareAmpShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter) {
        return armPivot.createMoveArmToAmpAngleCommand()
            // TODO(gpr) Make a `shooter.createSetAmpRPMCommand` and make 800 a constant / GosDoubleProperty
            .alongWith(shooter.createSetRPMCommand(800))
            .withName("Prepare Amp Shot");
    }

    public static Command ampShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, IntakeSubsystem intake) {
        return prepareAmpShot(armPivot, shooter)
            .alongWith(intake.createMoveIntakeInCommand())
            .withName("Auto shoot into amp");
    }

    public static Command vibrateIfReadyToShoot(ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter, CommandXboxController controller) {
        boolean isReady = chassis.isAngleAtGoal() && arm.isArmAtGoal() && shooter.isShooterAtGoal();
        return new VibrateControllerTimedCommand(controller, 2, isReady);

    }
}
