package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.RobotExtrinsics;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class CombinedCommands {

    public static Command intakePieceCommand(ArmPivotSubsystem armPivot, IntakeSubsystem intake) {
        return armPivot.createMoveArmToGroundIntakeAngleCommand()
            .alongWith(intake.createMoveIntakeInCommand())
            .until(intake::hasGamePiece)
            .withName("Intake Piece");
    }

    public static Sendable prepareTunableShot(ArmPivotSubsystem arm, ShooterSubsystem shooter) {
        return arm.createMoveArmToTunableSpeakerAngleCommand()
            .alongWith(shooter.createRunTunableRpmCommand())
            .withName("Prepare Tunable Shot");
    }

    public static Command prepareSpeakerShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, Supplier<Pose2d> pos) {
        return armPivot.createPivotUsingSpeakerTableCommand(pos)
            .alongWith(shooter.createRunSpeakerShotRPMCommand());
    }

    public static Command prepareSpeakerShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, double angle) {
        return armPivot.createMoveArmToAngleCommand(angle)
            .alongWith(shooter.createRunSpeakerShotRPMCommand());
    }

    //what we ran during quals
    public static Command prepareSpeakerShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, GosDoubleProperty angle) {
        DoubleSupplier supplier = angle::getValue;
        return armPivot.createMoveArmToAngleCommand(supplier)
            .alongWith(shooter.createRunSpeakerShotRPMCommand());
    }

    public static Command prepareAmpShot(ArmPivotSubsystem armPivot, ShooterSubsystem shooter) {
        return armPivot.createMoveArmToAmpAngleCommand()
            .alongWith(shooter.createRunAmpShotRPMCommand())
            .withName("Prepare Amp Shot");
    }

    public static Command ampShooterCommand(ArmPivotSubsystem armPivot, ShooterSubsystem shooter, IntakeSubsystem intake) {
        return prepareAmpShot(armPivot, shooter)
            .alongWith(intake.createMoveIntakeInCommand())
            .withName("Auto shoot into amp");
    }

    @SuppressWarnings("PMD.LinguisticNaming")
    public static Command vibrateIfReadyToShoot(ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter, CommandXboxController controller) {
        BooleanSupplier isReadySupplier = () -> chassis.isAngleAtGoal() && arm.isArmAtGoal() && shooter.isShooterAtGoal();
        return new VibrateControllerWhileTrueCommand(controller, isReadySupplier);
    }

    public static Command feedPieceAcrossFieldWithVision(CommandXboxController joystick, ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter, IntakeSubsystem intake) {
        BooleanSupplier readyToLaunchSupplier = () -> {
            double blueMinX = 10.2;
            double redMaxX = FieldConstants.FIELD_LENGTH - blueMinX;
            boolean mechReady = arm.isArmAtGoal() && shooter.isShooterAtGoal() && chassis.isAngleAtGoal();
            boolean distanceReady;
            if (GetAllianceUtil.isBlueAlliance()) {
                distanceReady = chassis.getPose().getX() < blueMinX;
            } else {
                distanceReady = chassis.getPose().getX() > redMaxX;
            }

            SmartDashboard.putBoolean("Feed: Mech Ready", mechReady);
            SmartDashboard.putNumber("Feed: X: ", chassis.getPose().getX());
            return mechReady && distanceReady;
        };


        return Commands.parallel(
            // Drive, Prep Arm And Shooter
            new TurnToPointSwerveDrive(chassis, joystick, RobotExtrinsics.FULL_FIELD_FEEDING_AIMING_POINT, true, chassis::getPose),
            arm.createMoveArmFeederAngleCommand(),
            shooter.createShootNoteToAllianceRPMCommand(),

            // Then, once they are all deemed ready, run the intake and vibrate the controller
            Commands.waitUntil(readyToLaunchSupplier)
                .andThen(intake.createMoveIntakeInCommand()
                    .alongWith(new VibrateControllerTimedCommand(joystick, 1)))
        ).withName("Full Field Feed Piece");
    }

    public static Command feedPieceAcrossFieldNoVision(CommandXboxController joystick, ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter, IntakeSubsystem intake) {
        BooleanSupplier readyToLaunchSupplier = () -> {
            return arm.isArmAtGoal() && shooter.isShooterAtGoal() && chassis.isAngleAtGoal();
        };

        return Commands.parallel(
            // Drive, Prep Arm And Shooter
            arm.createMoveArmFeederAngleCommand(),
            shooter.createShootNoteToAllianceRPMCommand(),
            Commands.waitUntil(readyToLaunchSupplier).andThen(
                new VibrateControllerTimedCommand(joystick, 1))
        ).withName("Full Field Feed Piece");
    }

    public static Command autoScoreInAmp(CommandXboxController joystick, ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter) {
        return Commands.parallel(
            chassis.createTakeAprilTagScreenshotCommand(),
            chassis.createDriveToAmpCommand(),
            Commands.waitUntil(() -> chassis.getDistanceToAmp() < Units.feetToMeters(4))
                .andThen(prepareAmpShot(arm, shooter))
                .andThen(new VibrateControllerTimedCommand(joystick, 1))
        );
    }

    public static Command prepHangingUp(CommandXboxController driverController, ArmPivotSubsystem armPivot, HangerSubsystem hanger, ChassisSubsystem chassis) {
        return armPivot.createMoveArmToPrepHangerAngleCommand()
            .andThen(hanger.createAutoUpCommand());


    }
}
