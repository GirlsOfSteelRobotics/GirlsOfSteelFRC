package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.MaybeFlippedPose2d;
import com.gos.crescendo2024.RobotExtrinsics;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.HangerSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.auto.NamedCommands;
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

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class CombinedCommands {

    private final ChassisSubsystem m_chassis;
    private final ArmPivotSubsystem m_arm;
    private final ShooterSubsystem m_shooter;
    private final IntakeSubsystem m_intake;
    private final HangerSubsystem m_hanger;

    public CombinedCommands(ChassisSubsystem chassisSubsystem, ArmPivotSubsystem armPivotSubsystem, ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem, HangerSubsystem hanger) {
        m_chassis = chassisSubsystem;
        m_arm = armPivotSubsystem;
        m_shooter = shooterSubsystem;
        m_intake = intakeSubsystem;
        m_hanger = hanger;
    }

    public Command intakePieceCommand() {
        return m_arm.createMoveArmToGroundIntakeAngleCommand()
            .alongWith(m_intake.createMoveIntakeInCommand())
            .until(m_intake::hasGamePiece)
            .withName("Intake Piece");
    }

    public Sendable prepareTunableShot() {
        return m_arm.createMoveArmToTunableSpeakerAngleCommand()
            .alongWith(m_shooter.createRunTunableRpmCommand())
            .withName("Prepare Tunable Shot");
    }

    public Command prepareSpeakerShot(Supplier<Pose2d> pos) {
        return m_arm.createPivotUsingSpeakerTableCommand(pos)
            .alongWith(m_shooter.createRunSpeakerShotRPMCommand());
    }

    public Command prepareSpeakerShot(double angle) {
        return m_arm.createMoveArmToAngleCommand(angle)
            .alongWith(m_shooter.createRunSpeakerShotRPMCommand());
    }

    //what we ran during quals
    public Command prepareSpeakerShot(GosDoubleProperty angle) {
        DoubleSupplier supplier = angle::getValue;
        return m_arm.createMoveArmToAngleCommand(supplier)
            .alongWith(m_shooter.createRunSpeakerShotRPMCommand());
    }

    public Command prepareAmpShot() {
        return m_arm.createMoveArmToAmpAngleCommand()
            .alongWith(m_shooter.createRunAmpShotRPMCommand())
            .withName("Prepare Amp Shot");
    }

    public Command ampShooterCommand() {
        return prepareAmpShot()
            .alongWith(m_intake.createMoveIntakeInCommand())
            .withName("Auto shoot into amp");
    }

    @SuppressWarnings("PMD.LinguisticNaming")
    public Command vibrateIfReadyToShoot(CommandXboxController controller) {
        BooleanSupplier isReadySupplier = () -> m_chassis.isAngleAtGoal() && m_arm.isArmAtGoal() && m_shooter.isShooterAtGoal();
        return new VibrateControllerWhileTrueCommand(controller, isReadySupplier);
    }

    public Command feedPieceAcrossFieldWithVision(CommandXboxController joystick) {
        BooleanSupplier readyToLaunchSupplier = () -> {
            double blueMinX = 10.2;
            double redMaxX = FieldConstants.FIELD_LENGTH - blueMinX;
            boolean mechReady = m_arm.isArmAtGoal() && m_shooter.isShooterAtGoal() && m_chassis.isAngleAtGoal();
            boolean distanceReady;
            if (GetAllianceUtil.isBlueAlliance()) {
                distanceReady = m_chassis.getPose().getX() < blueMinX;
            } else {
                distanceReady = m_chassis.getPose().getX() > redMaxX;
            }

            SmartDashboard.putBoolean("Feed: Mech Ready", mechReady);
            SmartDashboard.putNumber("Feed: X: ", m_chassis.getPose().getX());
            return mechReady && distanceReady;
        };


        return Commands.parallel(
            // Drive, Prep Arm And Shooter
            new TurnToPointSwerveDrive(m_chassis, joystick, RobotExtrinsics.FULL_FIELD_FEEDING_AIMING_POINT, true, m_chassis::getPose),
            m_arm.createMoveArmFeederAngleCommand(),
            m_shooter.createShootNoteToAllianceRPMCommand(),

            // Then, once they are all deemed ready, run the intake and vibrate the controller
            Commands.waitUntil(readyToLaunchSupplier)
                .andThen(m_intake.createMoveIntakeInCommand()
                    .alongWith(new VibrateControllerTimedCommand(joystick, 1)))
        ).withName("Full Field Feed Piece");
    }

    public Command feedPieceAcrossFieldNoVision(CommandXboxController joystick) {
        BooleanSupplier readyToLaunchSupplier = () -> m_arm.isArmAtGoal() && m_shooter.isShooterAtGoal() && m_chassis.isAngleAtGoal();

        return Commands.parallel(
            // Drive, Prep Arm And Shooter
            new DavidDriveSwerve(m_chassis, joystick),
            m_arm.createMoveArmFeederAngleCommand(),
            m_shooter.createShootNoteToAllianceRPMCommand(),
            Commands.waitUntil(readyToLaunchSupplier).andThen(
                new VibrateControllerTimedCommand(joystick, 1))
        ).withName("Full Field Feed Piece");
    }

    public Command autoScoreInAmp(CommandXboxController joystick) {
        return Commands.parallel(
            m_chassis.createTakeAprilTagScreenshotCommand(),
            m_chassis.createDriveToAmpCommand(),
            Commands.waitUntil(() -> m_chassis.getDistanceToAmp() < Units.feetToMeters(4))
                .andThen(prepareAmpShot())
                .andThen(new VibrateControllerTimedCommand(joystick, 1))
        );
    }

    public Command prepHangingUp(CommandXboxController driverController) {
        return m_arm.createMoveArmToPrepHangerAngleCommand()
            .andThen(m_hanger.createAutoUpCommand());
    }

    public Command followPathWhileIntaking(String trajectoryName) {
        return Commands.deadline(
                followChoreoPath(trajectoryName),
                NamedCommands.getCommand("IntakePiece")
            );
    }

    public Command resetPose(MaybeFlippedPose2d pose) {
        return m_chassis.createResetPoseCommand(pose);
    }

    public Command autoAimAndShoot() {
        return SpeakerAimAndShootCommand.createShootWhileStationary(m_arm, m_chassis, m_intake, m_shooter);
    }
}
