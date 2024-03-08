package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.AllianceFlipper;
import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.SpeakerLookupTable;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ArmPivotSubsystem extends SubsystemBase {
    private static final GosDoubleProperty ARM_INTAKE_ANGLE = new GosDoubleProperty(false, "intakeAngle", 358);
    public static final GosDoubleProperty ARM_TUNABLE_SPEAKER_ANGLE = new GosDoubleProperty(false, "tunableSpeakerAngle", 9);
    public static final GosDoubleProperty MIDDLE_SUBWOOFER_ANGLE = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "defaultSpeakerScoreAngle", 9); //TODO changeeee
    public static final GosDoubleProperty SIDE_SUBWOOFER_ANGLE = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "defaultSideSpeakerScoreAngle", 15);
    private static final GosDoubleProperty ARM_AMP_ANGLE = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "ampScoreAngle", 90);

    private static final double ARM_MAX_ANGLE = 102; //from hanger testing day 3/5/24

    public static final GosDoubleProperty SPIKE_TOP_ANGLE = new GosDoubleProperty(false, "arm spike top angle", 25);
    public static final GosDoubleProperty SPIKE_MIDDLE_ANGLE = new GosDoubleProperty(false, "arm spike middle angle", 9);
    public static final GosDoubleProperty SPIKE_BOTTOM_ANGLE = new GosDoubleProperty(false, "arm spike bottom angle", 25);


    private static final double GEAR_RATIO;
    private static final boolean USE_ABSOLUTE_ENCODER = false;

    private static final double ALLOWABLE_ERROR = 1.7;

    static {
        if (Constants.IS_COMPETITION_ROBOT) {
            GEAR_RATIO = (58.0 / 15.0) * 25;
        } else {
            GEAR_RATIO = (58.0 / 12.0) * (3.0) * (5.0);
        }
    }

    private final SimableCANSparkMax m_pivotMotor;
    private final SimableCANSparkMax m_followMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final AbsoluteEncoder m_pivotAbsEncoder;
    private final LoggingUtil m_networkTableEntriesPivot;
    private final SparkMaxAlerts m_armPivotMotorErrorAlerts;
    private final SparkMaxAlerts m_armPivotFollowerErrorAlerts;

    private final SparkPIDController m_sparkPidController;
    private final PidProperty m_sparkPidProperties;
    private final ProfiledPIDController m_profilePID;
    private final PidProperty m_profilePidProperties;
    private final ArmFeedForwardProperty m_wpiFeedForward;
    private double m_armGoalAngle = Double.MIN_VALUE;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    private final SpeakerLookupTable m_speakerTable;

    public ArmPivotSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.ARM_PIVOT, CANSparkLowLevel.MotorType.kBrushless);
        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotMotor.setSmartCurrentLimit(60);
        m_pivotMotor.setInverted(false);

        m_followMotor = new SimableCANSparkMax(Constants.ARM_PIVOT_FOLLOW, CANSparkLowLevel.MotorType.kBrushless);
        m_followMotor.restoreFactoryDefaults();
        m_followMotor.follow(m_pivotMotor, true);
        m_followMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_followMotor.setSmartCurrentLimit(60);

        // Request the absolute encoder position / velocity faster than the default period
        m_pivotMotor.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus5, 20);
        m_pivotMotor.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus6, 20);

        m_pivotMotorEncoder = m_pivotMotor.getEncoder();
        m_pivotMotorEncoder.setPositionConversionFactor(360.0 / GEAR_RATIO);
        m_pivotMotorEncoder.setVelocityConversionFactor(360.0 / GEAR_RATIO / 60);

        m_pivotAbsEncoder = m_pivotMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
        m_pivotAbsEncoder.setPositionConversionFactor(360.0);
        m_pivotAbsEncoder.setVelocityConversionFactor(360.0 / 60);
        m_pivotAbsEncoder.setInverted(false);
        m_pivotAbsEncoder.setZeroOffset(277.11 + 2);

        m_speakerTable = new SpeakerLookupTable();

        m_sparkPidController = m_pivotMotor.getPIDController();
        if (USE_ABSOLUTE_ENCODER) {
            m_sparkPidController.setFeedbackDevice(m_pivotAbsEncoder);
        } else {
            m_sparkPidController.setFeedbackDevice(m_pivotMotorEncoder);
        }
        m_sparkPidController.setPositionPIDWrappingEnabled(true);
        m_sparkPidController.setPositionPIDWrappingMinInput(0);
        m_sparkPidController.setPositionPIDWrappingMaxInput(360);
        m_sparkPidProperties = new RevPidPropertyBuilder("Arm Pivot", Constants.DEFAULT_CONSTANT_PROPERTIES, m_sparkPidController, 0)
            .addP(0.14)
            .addI(0)
            .addD(0)
            .build();
        m_profilePID = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_profilePID.enableContinuousInput(0, 360);
        m_profilePidProperties = new WpiProfiledPidPropertyBuilder("Arm Profile PID", Constants.DEFAULT_CONSTANT_PROPERTIES, m_profilePID)
            .addMaxVelocity(120)
            .addMaxAcceleration(120)
            .build();
        m_wpiFeedForward = new ArmFeedForwardProperty("Arm Pivot Profile ff", Constants.DEFAULT_CONSTANT_PROPERTIES)
            .addKs(0)
            .addKff(2.2)
            .addKg(1.2);

        m_networkTableEntriesPivot = new LoggingUtil("Arm Pivot Subsystem");
        m_networkTableEntriesPivot.addDouble("Output", m_pivotMotor::getAppliedOutput);
        m_networkTableEntriesPivot.addDouble("Arm Current", m_pivotMotor::getOutputCurrent);
        m_networkTableEntriesPivot.addDouble("Arm Speed", m_pivotMotor::get);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Position", m_pivotAbsEncoder::getPosition);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Velocity", m_pivotAbsEncoder::getVelocity);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Position", () -> m_pivotMotorEncoder.getPosition() % 360);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Velocity", m_pivotMotorEncoder::getVelocity);
        m_networkTableEntriesPivot.addBoolean("Arm At Goal", this::isArmAtGoal);
        m_networkTableEntriesPivot.addDouble("Setpoint Position", () -> m_profilePID.getSetpoint().position);
        m_networkTableEntriesPivot.addDouble("Setpoint Velocity", () -> m_profilePID.getSetpoint().velocity);

        m_armPivotMotorErrorAlerts = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor");
        m_armPivotFollowerErrorAlerts = new SparkMaxAlerts(m_followMotor, "arm follower");

        m_pivotMotor.burnFlash();
        m_followMotor.burnFlash();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), 252, 1,
                0.381, Units.degreesToRadians(-2), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        syncRelativeEncoder();
    }

    public void clearStickyFaults() {
        m_pivotMotor.clearFaults();
        m_followMotor.clearFaults();
    }

    @Override
    public void periodic() {
        if (DriverStation.isDisabled()) {
            syncRelativeEncoder();
        }
        m_networkTableEntriesPivot.updateLogs();
        m_armPivotMotorErrorAlerts.checkAlerts();
        m_armPivotFollowerErrorAlerts.checkAlerts();

        m_sparkPidProperties.updateIfChanged();
        m_wpiFeedForward.updateIfChanged();
        m_profilePidProperties.updateIfChanged();
    }

    public void moveArmToAngle(double goalAngle) {
        if (Math.abs(m_armGoalAngle - goalAngle) > 2) {
            resetPidController();
        }

        m_armGoalAngle = goalAngle;
        double currentAngle = getAngle();
        if (currentAngle < ARM_MAX_ANGLE || MathUtil.inputModulus(goalAngle, -180, 180) < MathUtil.inputModulus(currentAngle, -180, 180)) {
            m_profilePID.calculate(currentAngle, goalAngle);
            TrapezoidProfile.State setpoint = m_profilePID.getSetpoint();
            double feedForwardVolts = m_wpiFeedForward.calculate(
                Units.degreesToRadians(currentAngle),
                Units.degreesToRadians(setpoint.velocity));


            m_sparkPidController.setReference(setpoint.position, CANSparkMax.ControlType.kPosition, 0, feedForwardVolts);
            SmartDashboard.putNumber("feedForwardVolts", feedForwardVolts);
        }
        else {
            stopArmMotor();
        }
    }

    private void resetPidController() {
        m_profilePID.reset(getAngle(), getEncoderVel());
    }


    public void pivotUsingSpeakerLookupTable(Supplier<Pose2d> roboMan) {
        moveArmToAngle(getPivotAngleUsingSpeakerLookupTable(roboMan));
    }

    public double getPivotAngleUsingSpeakerLookupTable(Supplier<Pose2d> roboMan) {
        Pose2d speaker = AllianceFlipper.maybeFlip(FieldConstants.Speaker.CENTER_SPEAKER_OPENING);
        Translation2d roboManTranslation = roboMan.get().getTranslation();
        double distanceToSpeaker = roboManTranslation.getDistance(speaker.getTranslation());
        return m_speakerTable.getAngleTable(distanceToSpeaker);
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }

    public void stopArmMotor() {
        m_pivotMotor.set(0);
        m_armGoalAngle = Double.MIN_VALUE;
    }

    public double getAngle() {
        if (RobotBase.isReal() && USE_ABSOLUTE_ENCODER) {
            return getAbsoluteEncoderAngle();
        }
        else {
            return getRelativeEncoderAngle();
        }
    }

    public double getAbsoluteEncoderAngle() {
        double angle = m_pivotAbsEncoder.getPosition();
        angle = MathUtil.inputModulus(angle, -180, 180);
        return angle;
    }

    public double getRelativeEncoderAngle() {
        double angle = m_pivotMotorEncoder.getPosition();
        angle = MathUtil.inputModulus(angle, -180, 180);
        return angle;
    }

    public double getEncoderVel() {
        return m_pivotMotorEncoder.getVelocity();
    }

    public void setArmMotorSpeed(double speed) {
        if (getAngle() < ARM_MAX_ANGLE || speed < 0) {
            m_pivotMotor.set(speed);
        }
        else {
            m_sparkPidController.setReference(ARM_MAX_ANGLE, CANSparkBase.ControlType.kPosition);
        }
    }

    public void setVoltage(double outputVolts) {
        m_pivotMotor.setVoltage(outputVolts);
    }

    public double getArmAngleGoal() {
        return m_armGoalAngle;
    }


    public double getPivotMotorPercentage() {
        return m_pivotMotor.getAppliedOutput();
    }

    public double getVoltage() {
        if (RobotBase.isReal()) {
            return m_pivotMotor.getBusVoltage();
        }
        return m_pivotMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
    }

    public boolean isArmAtGoal() {
        double error = m_armGoalAngle - getAngle();
        return Math.abs(error) < ALLOWABLE_ERROR;
    }

    private void syncRelativeEncoder() {
        m_pivotMotorEncoder.setPosition(m_pivotAbsEncoder.getPosition());
    }

    public boolean areArmEncodersGood() {
        return Math.abs(getRelativeEncoderAngle() - getAbsoluteEncoderAngle()) <= ALLOWABLE_ERROR;
    }

    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////
    private Command createResetPidControllerCommand() {
        return runOnce(this::resetPidController);
    }

    public Command createPivotUsingSpeakerTableCommand(Supplier<Pose2d> roboMan) {
        return this.runEnd(() -> this.pivotUsingSpeakerLookupTable(roboMan), this::stopArmMotor).withName("pivot from robot pose");
    }

    private Command createMoveArmToAngleCommand(DoubleSupplier angleSupplier) {
        return createResetPidControllerCommand().andThen(
            runEnd(() -> moveArmToAngle(angleSupplier.getAsDouble()), this::stopArmMotor));
    }

    public Command createMoveArmToAngleCommand(double goalAngle) {
        return createMoveArmToAngleCommand(() -> goalAngle).withName("arm to " + goalAngle);
    }

    public Command createMoveArmToGroundIntakeAngleCommand() {
        return createMoveArmToAngleCommand(ARM_INTAKE_ANGLE::getValue).withName("arm to ground intake angle");
    }

    public Command createMoveArmToAmpAngleCommand() {
        return createMoveArmToAngleCommand(ARM_AMP_ANGLE::getValue).withName("arm to amp angle");
    }

    public Command createMoveArmToDefaultSpeakerAngleCommand() {
        return createMoveArmToAngleCommand(MIDDLE_SUBWOOFER_ANGLE::getValue).withName("arm to default speaker angle");
    }

    public Command createSyncRelativeEncoderCommand() {
        return run(this::syncRelativeEncoder).ignoringDisable(true).withName("arm: sync encoder");
    }

    public Command createMoveArmToTunableSpeakerAngleCommand() {
        return createMoveArmToAngleCommand(ARM_TUNABLE_SPEAKER_ANGLE::getValue).withName("arm to tunable speaker angle");
    }

    public Command createPivotToCoastModeCommand() {
        return this.runEnd(
                () -> {
                    m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
                    m_followMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
                },
                () -> {
                    m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
                    m_followMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);
                })
            .ignoringDisable(true).withName("Pivot to Coast");
    }
}
