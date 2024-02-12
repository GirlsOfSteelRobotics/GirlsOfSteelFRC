package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.SpeakerLookupTable;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

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

    private SpeakerAimAndShootCommand(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_intakeTimer = new Timer();

        m_armAngleGoalSupplier = ArmPivotSubsystem.ARM_DEFAULT_SPEAKER_ANGLE::getValue;
        m_shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        m_robotPoseProvider = m_chassisSubsystem::getPose;

        addRequirements(this.m_armPivotSubsystem, this.m_chassisSubsystem, this.m_intakeSubsystem, this.m_shooterSubsystem);
    }
    public SpeakerAimAndShootCommand(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, Supplier<Pose2d> poseSupplier, DoubleSupplier shooterRpmGoalSupplier, DoubleSupplier armAngleGoalSupplier) {
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_intakeTimer = new Timer();

        m_armAngleGoalSupplier = armAngleGoalSupplier;
        m_shooterRpmGoalSupplier = shooterRpmGoalSupplier;
        m_robotPoseProvider = poseSupplier;

        addRequirements(this.m_armPivotSubsystem, this.m_chassisSubsystem, this.m_intakeSubsystem, this.m_shooterSubsystem);
    }

    public static SpeakerAimAndShootCommand createWithDefaults(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        return new SpeakerAimAndShootCommand(armPivotSubsystem, chassisSubsystem, intakeSubsystem, shooterSubsystem);
    }
    public static SpeakerAimAndShootCommand createWithFixedArmAngle(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, Supplier<Pose2d> poseSupplier, DoubleSupplier shooterRpmGoalSupplier, DoubleSupplier armAngleGoalSupplier) {
        return new SpeakerAimAndShootCommand(armPivotSubsystem, chassisSubsystem, intakeSubsystem, shooterSubsystem, poseSupplier, shooterRpmGoalSupplier, armAngleGoalSupplier);
    }

    public static SpeakerAimAndShootCommand createShootWhileDrive(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        DoubleSupplier shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        Supplier<Pose2d> pose = chassisSubsystem::getFuturePose;
        DoubleSupplier pivotAngle = () -> armPivotSubsystem.getPivotAngleUsingSpeakerLookupTable(pose);
        return new SpeakerAimAndShootCommand(armPivotSubsystem, chassisSubsystem, intakeSubsystem, shooterSubsystem, pose, shooterRpmGoalSupplier, pivotAngle);
    }

    public SpeakerAimAndShootCommand(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, Supplier<Pose2d> poseSupplier) {
        this.m_armPivotSubsystem = armPivotSubsystem;
        this.m_chassisSubsystem = chassisSubsystem;
        this.m_intakeSubsystem = intakeSubsystem;
        this.m_shooterSubsystem = shooterSubsystem;
        this.m_intakeTimer = new Timer();

        m_armAngleGoalSupplier = ArmPivotSubsystem.ARM_DEFAULT_SPEAKER_ANGLE::getValue;
        m_shooterRpmGoalSupplier = ShooterSubsystem.DEFAULT_SHOOTER_RPM::getValue;
        m_robotPoseProvider = poseSupplier;

        addRequirements(this.m_armPivotSubsystem, this.m_chassisSubsystem, this.m_intakeSubsystem, this.m_shooterSubsystem);
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
