package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.SpeakerLookupTable;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;


public class SpeakerAimAndShootCommand extends Command {
    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final Timer m_intakeTimer;

    private final DoubleSupplier m_armAngleGoalSupplier;
    private final DoubleSupplier m_shooterRpmGoalSupplier;
    private final Supplier<Pose2d> m_robotPoseProvider;
    private boolean m_runIntake;

    private CommandXboxController m_controller;

    private SpeakerAimAndShootCommand(ArmPivotSubsystem armPivotSubsystem,
                                     ChassisSubsystem chassisSubsystem,
                                     IntakeSubsystem intakeSubsystem,
                                     ShooterSubsystem shooterSubsystem,
                                     Supplier<Pose2d> poseSupplier,
                                     DoubleSupplier shooterRpmGoalSupplier,
                                     DoubleSupplier armAngleGoalSupplier,
                                      CommandXboxController controller) {
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_intakeTimer = new Timer();
        this.m_controller = controller;

        m_armAngleGoalSupplier = armAngleGoalSupplier;
        m_shooterRpmGoalSupplier = shooterRpmGoalSupplier;
        m_robotPoseProvider = poseSupplier;

        addRequirements(this.m_armPivotSubsystem, this.m_chassisSubsystem, this.m_intakeSubsystem, this.m_shooterSubsystem);
    }

    public static SpeakerAimAndShootCommand createWithDefaults(
        ArmPivotSubsystem armPivotSubsystem,
        ChassisSubsystem chassisSubsystem,
        IntakeSubsystem intakeSubsystem,
        ShooterSubsystem shooterSubsystem) {
        DoubleSupplier m_armAngleGoalSupplier = ArmPivotSubsystem.ARM_DEFAULT_SPEAKER_ANGLE::getValue;
        DoubleSupplier m_shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> m_robotPoseProvider = chassisSubsystem::getPose;
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            m_robotPoseProvider,
            m_shooterRpmGoalSupplier,
            m_armAngleGoalSupplier,
            null);
    }
    public static SpeakerAimAndShootCommand createWithFixedArmAngle(ArmPivotSubsystem armPivotSubsystem,
                                                                    ChassisSubsystem chassisSubsystem,
                                                                    IntakeSubsystem intakeSubsystem,
                                                                    ShooterSubsystem shooterSubsystem,
                                                                    DoubleSupplier armAngleGoalSupplier) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> m_robotPoseProvider = chassisSubsystem::getPose;
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            m_robotPoseProvider,
            shooterRpmGoalSupplier,
            armAngleGoalSupplier,
            null);
    }

    public static SpeakerAimAndShootCommand createShootWhileDrive(ArmPivotSubsystem armPivotSubsystem,
                                                                  ChassisSubsystem chassisSubsystem,
                                                                  IntakeSubsystem intakeSubsystem,
                                                                  ShooterSubsystem shooterSubsystem) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getFuturePose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsingSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            pose,
            shooterRpmGoalSupplier,
            pivotAngle,
            null);
    }

    public static SpeakerAimAndShootCommand createShootWhileStationary(ArmPivotSubsystem armPivotSubsystem,
                                                                  ChassisSubsystem chassisSubsystem,
                                                                  IntakeSubsystem intakeSubsystem,
                                                                  ShooterSubsystem shooterSubsystem) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getPose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsingSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            pose,
            shooterRpmGoalSupplier,
            pivotAngle,
            null);
    }

    public static SpeakerAimAndShootCommand driverControllerPractice(ArmPivotSubsystem armPivotSubsystem,
                                                                     ChassisSubsystem chassisSubsystem,
                                                                     IntakeSubsystem intakeSubsystem,
                                                                     ShooterSubsystem shooterSubsystem,
                                                                     CommandXboxController controller) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getFuturePose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsiadded ngSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            pose,
            shooterRpmGoalSupplier,
            pivotAngle,
            controller);

    }

    @Override
    public void initialize() {
        m_intakeTimer.reset();
        m_intakeTimer.stop();
        m_runIntake = false;
    }


    @Override
    public void execute() {
        m_armPivotSubsystem.moveArmToAngle(m_armAngleGoalSupplier.getAsDouble());
        m_chassisSubsystem.turnButtToFacePoint(m_robotPoseProvider.get(), FieldConstants.Speaker.CENTER_SPEAKER_OPENING, 0, 0);
        m_shooterSubsystem.setPidRpm(m_shooterRpmGoalSupplier.getAsDouble());

        if (m_armPivotSubsystem.isArmAtGoal() && m_chassisSubsystem.isAngleAtGoal() && m_shooterSubsystem.isShooterAtGoal()) {
            m_runIntake = true;
            m_intakeTimer.start();
            if (m_controller != null)
                m_controller.getHID().setRumble(GenericHID.RumbleType.kBothRumble, 0.50);
        }

        if (m_runIntake) {
            m_intakeSubsystem.intakeIn();
        }
    }

    @Override
    public boolean isFinished() {
        return m_intakeTimer.hasElapsed(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_armPivotSubsystem.stopArmMotor();
        m_shooterSubsystem.stopShooter();
        m_intakeSubsystem.intakeStop();
        m_chassisSubsystem.setChassisSpeed(new ChassisSpeeds());
    }
}
