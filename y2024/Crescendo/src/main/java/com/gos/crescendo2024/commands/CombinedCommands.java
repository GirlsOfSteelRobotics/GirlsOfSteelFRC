package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
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

    // TODO(buckeye) Create an automated and non-automated version
    public static Command feedPieceAcrossField(CommandXboxController joystick, ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter, IntakeSubsystem intake) {
        BooleanSupplier readyToLaunchSupplier = () -> {
            //            double blueMinX = 10.2;
            //            double redMaxX = FieldConstants.FIELD_LENGTH - blueMinX;
            //            boolean mechReady = arm.isArmAtGoal() && shooter.isShooterAtGoal();
            //            boolean distanceReady;
            //            if (GetAllianceUtil.isBlueAlliance()) {
            //                distanceReady = chassis.getPose().getX() < blueMinX;
            //            } else {
            //                distanceReady = chassis.getPose().getX() > redMaxX;
            //            }
            //
            //            SmartDashboard.putBoolean("Feed: Mech Ready", mechReady);
            //            SmartDashboard.putNumber("Feed: X: ", chassis.getPose().getX());
            return arm.isArmAtGoal() && shooter.isShooterAtGoal() && chassis.isAngleAtGoal();
        };


        return Commands.parallel(
            // Drive, Prep Arm And Shooter
            // new TurnToPointSwerveDrive(chassis, joystick, RobotExtrinsics.FULL_FIELD_FEEDING_AIMING_POINT, true, chassis::getPose),
            arm.createMoveArmFeederAngleCommand(),
            shooter.createShootNoteToAllianceRPMCommand(),

            //face alliance and have anna translate across
            //chassis.createTurnToAngleCommand(0), //might need to be 180 to face alliance
            // Then, once they are all deemed ready, run the intake and vibrate the controller
            //            Commands.waitUntil(readyToLaunchSupplier).andThen(
            //                intake.createMoveIntakeInCommand().alongWith(new VibrateControllerTimedCommand(joystick, 1)))
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
            .andThen(() -> chassis.setDefaultCommand(new TeleopSwerveDrive(chassis, driverController)))
            .andThen(hanger.createAutoUpCommand());


    }
}
