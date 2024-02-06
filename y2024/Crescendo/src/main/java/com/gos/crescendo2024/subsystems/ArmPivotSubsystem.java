package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.SpeakerLookupTable;
import com.gos.crescendo2024.subsystems.sysid.ArmPivotSysId;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

import java.util.function.Supplier;


public class ArmPivotSubsystem extends SubsystemBase {
    private static final GosDoubleProperty ARM_INTAKE_ANGLE = new GosDoubleProperty(false, "intakeAngle", 4);
    public static final GosDoubleProperty ARM_DEFAULT_SPEAKER_ANGLE = new GosDoubleProperty(false, "speakerScoreAngle", 24);
    private static final GosDoubleProperty ARM_AMP_ANGLE = new GosDoubleProperty(false, "ampScoreAngle", 90);

    private static final double GEAR_RATIO = 72; // TODO get from design
    private static final boolean USE_ABSOLUTE_ENCODER = false;

    private static final double ALLOWABLE_ERROR = 1;

    private final SimableCANSparkMax m_pivotMotor;
    private final SimableCANSparkMax m_followMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final AbsoluteEncoder m_pivotAbsEncoder;
    private final LoggingUtil m_networkTableEntriesPivot;
    private final SparkMaxAlerts m_armPivotMotorErrorAlerts;

    private final SparkPIDController m_sparkPidController;
    private final PidProperty m_sparkPidProperties;
    private final ArmFeedForwardProperty m_wpiFeedForward;
    private double m_armGoalAngle = Double.MIN_VALUE;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    private final SpeakerLookupTable m_speakerTable;

    private final ArmPivotSysId m_armPivotSysId;

    public ArmPivotSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.ARM_PIVOT, CANSparkLowLevel.MotorType.kBrushless);
        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotMotor.setInverted(false);
        m_pivotMotor.setSmartCurrentLimit(60);

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
        m_pivotAbsEncoder.setInverted(true);
        m_pivotAbsEncoder.setZeroOffset(50.1);

        m_speakerTable = new SpeakerLookupTable();

        m_sparkPidController = m_pivotMotor.getPIDController();
        if (USE_ABSOLUTE_ENCODER) {
            m_sparkPidController.setFeedbackDevice(m_pivotAbsEncoder);
        } else {
            m_sparkPidController.setFeedbackDevice(m_pivotMotorEncoder);
        }
        m_sparkPidController.setPositionPIDWrappingEnabled(true);
        m_sparkPidProperties = new RevPidPropertyBuilder("Arm Pivot", false, m_sparkPidController, 0)
            .addP(0.0001)
            .addFF(0.0007)
            .addI(0)
            .addD(0)
            .addMaxAcceleration(150)
            .addMaxVelocity(150)
            .build();

        m_wpiFeedForward = new ArmFeedForwardProperty("Arm Pivot Profile ff", false)
            .addKff(0)
            .addKg(0.9);

        m_networkTableEntriesPivot = new LoggingUtil("Arm Pivot Subsystem");
        m_networkTableEntriesPivot.addDouble("Output", m_pivotMotor::getAppliedOutput);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Position", m_pivotAbsEncoder::getPosition);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Velocity", m_pivotAbsEncoder::getVelocity);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Position", m_pivotMotorEncoder::getPosition);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Velocity", m_pivotMotorEncoder::getVelocity);
        m_networkTableEntriesPivot.addBoolean("Arm At Goal", this::isArmAtGoal);


        m_armPivotMotorErrorAlerts = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor");

        m_pivotMotor.burnFlash();
        m_followMotor.burnFlash();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), 252, 1,
                0.381, 0, Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        m_armPivotSysId = new ArmPivotSysId(this);
    }

    @Override
    public void periodic() {
        m_networkTableEntriesPivot.updateLogs();
        m_armPivotMotorErrorAlerts.checkAlerts();

        m_sparkPidProperties.updateIfChanged();
        m_wpiFeedForward.updateIfChanged();
    }


    public void clearStickyFaults() {
        m_pivotMotor.clearFaults();
    }


    public void moveArmToAngle(double goalAngle) {
        m_armGoalAngle = goalAngle;
        double currentAngle = getAngle();

        double feedForwardVolts = m_wpiFeedForward.calculate(
            Units.degreesToRadians(currentAngle),
            Units.degreesToRadians(0));


        m_sparkPidController.setReference(m_armGoalAngle, CANSparkMax.ControlType.kSmartMotion, 0, feedForwardVolts);
        SmartDashboard.putNumber("feedForwardVolts", feedForwardVolts);
    }


    public void pivotUsingSpeakerLookupTable(Supplier<Pose2d> roboMan) {
        Pose2d speaker = FieldConstants.Speaker.CENTER_SPEAKER_OPENING;
        Translation2d roboManTranslation =  roboMan.get().getTranslation();
        double distanceToSpeaker = roboManTranslation.getDistance(speaker.getTranslation());
        moveArmToAngle(m_speakerTable.getVelocityTable(distanceToSpeaker));
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
            return m_pivotAbsEncoder.getPosition();
        }
        else {
            return m_pivotMotorEncoder.getPosition();
        }
    }

    public void setArmMotorSpeed(double speed) {
        m_pivotMotor.set(speed);
    }

    public void setVoltageLeadAndFollow(double outputVolts) {
        m_pivotMotor.setVoltage(outputVolts);
        m_followMotor.setVoltage(outputVolts);
    }

    public double getArmAngleGoal() {
        return m_armGoalAngle;
    }


    public double getPivotMotorPercentage() {
        return m_pivotMotor.getAppliedOutput();
    }

    public double replaceVoltage() {
        return m_pivotMotor.get() * RobotController.getBatteryVoltage();
    }

    public double getEncoderPos() {
        return m_pivotMotorEncoder.getPosition();
    }

    public double getEncoderVel() {
        return m_pivotMotorEncoder.getVelocity();
    }

    public boolean isArmAtGoal() {
        double error = m_armGoalAngle - getAngle();
        return Math.abs(error) < ALLOWABLE_ERROR;
    }

    private void syncRelativeEncoder() {
        m_pivotMotorEncoder.setPosition(m_pivotAbsEncoder.getPosition());
    }

    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////


    public Command createPivotUsingSpeakerTableCommand(Supplier<Pose2d> roboMan) {
        return this.runEnd(() -> this.pivotUsingSpeakerLookupTable(roboMan), this::stopArmMotor).withName("pivot from robot pose");
    }

    public Command createMoveArmToAngleCommand(double goalAngle) {
        return runEnd(() -> moveArmToAngle(goalAngle), this::stopArmMotor).withName("arm to " + goalAngle);
    }

    public Command createMoveArmToGroundIntakeAngleCommand() {
        return runEnd(() -> moveArmToAngle(ARM_INTAKE_ANGLE.getValue()), this::stopArmMotor).withName("arm to ground intake angle");
    }

    public Command createMoveArmToAmpAngleCommand() {
        return runEnd(() -> moveArmToAngle(ARM_AMP_ANGLE.getValue()), this::stopArmMotor).withName("arm to amp angle");
    }

    public Command createMoveArmToDefaultSpeakerAngleCommand() {
        return runEnd(() -> moveArmToAngle(ARM_DEFAULT_SPEAKER_ANGLE.getValue()), this::stopArmMotor).withName("arm to default speaker angle");
    }

    public Command createSyncRelativeEncoderCommand() {
        return run(this::syncRelativeEncoder).ignoringDisable(true).withName("arm: sync encoder");
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
