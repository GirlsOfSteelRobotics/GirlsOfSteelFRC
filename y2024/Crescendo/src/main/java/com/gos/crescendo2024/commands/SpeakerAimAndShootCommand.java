package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.AllianceFlipper;
import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;


public final class SpeakerAimAndShootCommand extends Command {
    private static final double DEFAULT_INTAKE_TIME = 1;
    private static final double EMERGENCY_RELEASE_TIME = 2.5;

    private final ArmPivotSubsystem m_armPivotSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final Timer m_intakeTimer;
    private final Timer m_emergencyReleaseTimer;

    private final DoubleSupplier m_armAngleGoalSupplier;
    private final DoubleSupplier m_shooterRpmGoalSupplier;
    private final Supplier<Pose2d> m_robotPoseProvider;
    private boolean m_runIntake;

    private final NetworkTableEntry m_didEmergencyTimerElapse;

    private final double m_intakeTime;

    private final Debouncer m_debouncer;

    private SpeakerAimAndShootCommand(ArmPivotSubsystem armPivotSubsystem,
                                     ChassisSubsystem chassisSubsystem,
                                     IntakeSubsystem intakeSubsystem,
                                     ShooterSubsystem shooterSubsystem,
                                     Supplier<Pose2d> poseSupplier,
                                     DoubleSupplier shooterRpmGoalSupplier,
                                     DoubleSupplier armAngleGoalSupplier,
                                      double runIntakeTime) {
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_intakeTimer = new Timer();
        this.m_emergencyReleaseTimer = new Timer();

        m_armAngleGoalSupplier = armAngleGoalSupplier;
        m_shooterRpmGoalSupplier = shooterRpmGoalSupplier;
        m_robotPoseProvider = poseSupplier;
        m_debouncer = new Debouncer(.1);

        m_intakeTime = runIntakeTime;

        m_didEmergencyTimerElapse = NetworkTableInstance.getDefault().getEntry("AutoShootDidEmergencyRelease");

        addRequirements(this.m_armPivotSubsystem, this.m_chassisSubsystem, this.m_intakeSubsystem, this.m_shooterSubsystem);
    }

    public static SpeakerAimAndShootCommand createWithDefaults(
        ArmPivotSubsystem armPivotSubsystem,
        ChassisSubsystem chassisSubsystem,
        IntakeSubsystem intakeSubsystem,
        ShooterSubsystem shooterSubsystem) {
        DoubleSupplier armAngleGoalSupplier = ArmPivotSubsystem.MIDDLE_SUBWOOFER_ANGLE::getValue;
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.SPEAKER_SHOT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> robotPoseProvider = chassisSubsystem::getPose;
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            robotPoseProvider,
            shooterRpmGoalSupplier,
            armAngleGoalSupplier,
            DEFAULT_INTAKE_TIME);
    }

    public static SpeakerAimAndShootCommand createWithFixedArmAngle(ArmPivotSubsystem armPivotSubsystem,
                                                                    ChassisSubsystem chassisSubsystem,
                                                                    IntakeSubsystem intakeSubsystem,
                                                                    ShooterSubsystem shooterSubsystem,
                                                                    DoubleSupplier armAngleGoalSupplier) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.SPEAKER_SHOT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> robotPoseProvider = chassisSubsystem::getPose;
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            robotPoseProvider,
            shooterRpmGoalSupplier,
            armAngleGoalSupplier,
            DEFAULT_INTAKE_TIME);
    }

    public static SpeakerAimAndShootCommand createShootWhileDrive(ArmPivotSubsystem armPivotSubsystem,
                                                                  ChassisSubsystem chassisSubsystem,
                                                                  IntakeSubsystem intakeSubsystem,
                                                                  ShooterSubsystem shooterSubsystem) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.SPEAKER_SHOT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getFuturePose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsingSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            pose,
            shooterRpmGoalSupplier,
            pivotAngle,
            DEFAULT_INTAKE_TIME);
    }

    public static SpeakerAimAndShootCommand createShootWhileStationary(ArmPivotSubsystem armPivotSubsystem,
                                                                       ChassisSubsystem chassisSubsystem,
                                                                       IntakeSubsystem intakeSubsystem,
                                                                       ShooterSubsystem shooterSubsystem) {
        return createShootWhileStationary(armPivotSubsystem, chassisSubsystem, intakeSubsystem, shooterSubsystem, DEFAULT_INTAKE_TIME);
    }

    public static SpeakerAimAndShootCommand createShootWhileStationary(ArmPivotSubsystem armPivotSubsystem,
                                                                  ChassisSubsystem chassisSubsystem,
                                                                  IntakeSubsystem intakeSubsystem,
                                                                  ShooterSubsystem shooterSubsystem,
                                                                double intakeTime) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.SPEAKER_SHOT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getPose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsingSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem,
            chassisSubsystem,
            intakeSubsystem,
            shooterSubsystem,
            pose,
            shooterRpmGoalSupplier,
            pivotAngle,
            intakeTime);
    }

    @Override
    public void initialize() {
        m_intakeTimer.reset();
        m_intakeTimer.stop();

        m_emergencyReleaseTimer.reset();
        m_emergencyReleaseTimer.start();
        m_runIntake = false;

        m_didEmergencyTimerElapse.setBoolean(false);
    }


    @Override
    public void execute() {
        Pose2d speaker = AllianceFlipper.maybeFlip(FieldConstants.Speaker.CENTER_SPEAKER_OPENING);

        m_armPivotSubsystem.moveArmToAngle(m_armAngleGoalSupplier.getAsDouble());
        m_chassisSubsystem.turnButtToFacePoint(m_robotPoseProvider.get(), speaker, 0, 0);
        m_shooterSubsystem.setPidRpm(m_shooterRpmGoalSupplier.getAsDouble());

        boolean isReadyToShoot = m_armPivotSubsystem.isArmAtGoal() && m_chassisSubsystem.isAngleAtGoal() && m_shooterSubsystem.isShooterAtGoal();
        if (m_debouncer.calculate(isReadyToShoot) || m_emergencyReleaseTimer.hasElapsed(EMERGENCY_RELEASE_TIME)) {
            if (m_emergencyReleaseTimer.hasElapsed(EMERGENCY_RELEASE_TIME)) {
                m_didEmergencyTimerElapse.setBoolean(true);
            }
            m_runIntake = true;
            m_intakeTimer.start();
        }

        if (m_runIntake) {
            m_intakeSubsystem.intakeIn();
        }
    }

    @Override
    public boolean isFinished() {
        return m_intakeTimer.hasElapsed(m_intakeTime);
    }

    @Override
    public void end(boolean interrupted) {
        m_armPivotSubsystem.stopArmMotor();
        m_shooterSubsystem.stopShooter();
        m_intakeSubsystem.intakeStop();
        m_chassisSubsystem.setChassisSpeed(new ChassisSpeeds());
    }
}
