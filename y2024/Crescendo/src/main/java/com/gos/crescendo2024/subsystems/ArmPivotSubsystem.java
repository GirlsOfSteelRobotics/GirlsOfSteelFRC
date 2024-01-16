package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmPivotSubsystem extends SubsystemBase {
    private static final double ARM_PIVOT_SPEED = .5;
    private static final GosDoubleProperty ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Error", 1.5);
    private static final GosDoubleProperty ALLOWABLE_VELOCITY_ERROR = new GosDoubleProperty(false, "Pivot Arm Allowable Velocity Error", 20);

    private final SimableCANSparkMax m_pivotMotor;
    private final SimableCANSparkMax m_followMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final AbsoluteEncoder m_pivotAbsEncoder;
    private final LoggingUtil m_networkTableEntriesPivot;
    private final SparkMaxAlerts m_armPivotMotorErrorAlerts;

    private final SparkPIDController m_sparkPidController;
    private final PidProperty m_sparkPidProperties;
    private final ProfiledPIDController m_wpiPidController;
    private final PidProperty m_wpiPidProperties;
    private final ArmFeedForwardProperty m_wpiFeedForward;
    private double m_armGoalAngle;

    private final NetworkTableEntry m_profilePositionGoalEntry;
    private final NetworkTableEntry m_profileVelocityGoalEntry;
    private final NetworkTableEntry m_pidArbitraryFeedForwardEntry;

    public ArmPivotSubsystem() {
        m_pivotMotor = new SimableCANSparkMax(Constants.ARM_PIVOT, CANSparkLowLevel.MotorType.kBrushless);
        m_pivotMotor.restoreFactoryDefaults();
        m_pivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_pivotMotor.setInverted(false);
        m_pivotMotor.setSmartCurrentLimit(60);

        m_followMotor = new SimableCANSparkMax(Constants.ARM_PIVOT_FOLLOW, CANSparkLowLevel.MotorType.kBrushless);
        m_followMotor.restoreFactoryDefaults();
        m_followMotor.follow(m_pivotMotor);
        m_followMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_followMotor.setInverted(false);
        m_followMotor.setSmartCurrentLimit(60);

        m_pivotMotorEncoder = m_pivotMotor.getEncoder();
        m_pivotAbsEncoder = m_pivotMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
        m_pivotAbsEncoder.setPositionConversionFactor(360.0);
        m_pivotAbsEncoder.setVelocityConversionFactor(360.0 / 60);
        m_pivotAbsEncoder.setInverted(false);
        m_pivotAbsEncoder.setZeroOffset(0);

        m_sparkPidController = m_pivotMotor.getPIDController();
        m_sparkPidController.setFeedbackDevice(m_pivotMotorEncoder);
        m_sparkPidProperties = new RevPidPropertyBuilder("Arm Pivot", true, m_sparkPidController, 0)
            .addP(0)
            .addI(0)
            .addD(0)
            .addMaxAcceleration(80)
            .addMaxVelocity(80)
            .build();

        m_wpiPidController = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_wpiPidProperties = new WpiProfiledPidPropertyBuilder("Arm Pivot Profile", false, m_wpiPidController)
            .build();
        m_wpiFeedForward = new ArmFeedForwardProperty("Arm Pivot Profile ff", false)
            .addKff(0)
            .addKg(0);

        m_networkTableEntriesPivot = new LoggingUtil("Arm Pivot Subsystem");
        m_networkTableEntriesPivot.addDouble("Output", m_pivotMotor::getAppliedOutput);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Value", m_pivotAbsEncoder::getPosition);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Value", m_pivotMotorEncoder::getPosition);


        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Arm Pivot Subsystem");
        m_profilePositionGoalEntry = loggingTable.getEntry("Profile Angle Goal");
        m_profileVelocityGoalEntry = loggingTable.getEntry("Profile Velocity Goal");
        m_pidArbitraryFeedForwardEntry = loggingTable.getEntry("Arbitrary FF");

        m_armPivotMotorErrorAlerts = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor");

        m_pivotMotor.burnFlash();
        m_followMotor.burnFlash();
    }

    @Override
    public void periodic() {
        m_networkTableEntriesPivot.updateLogs();
        m_armPivotMotorErrorAlerts.checkAlerts();

        m_sparkPidProperties.updateIfChanged();
        m_wpiPidProperties.updateIfChanged();
        m_wpiFeedForward.updateIfChanged();
    }

    public void clearStickyFaults() { m_pivotMotor.clearFaults(); }

    public void setArmPivotSpeed(double speed) {
        m_pivotMotor.set(speed);
    }
    public void pivotArmUp() { m_pivotMotor.set(ARM_PIVOT_SPEED); }
    public void pivotArmDown() { m_pivotMotor.set(-ARM_PIVOT_SPEED); }

    private void resetWpiPidController() {
        m_wpiPidController.reset(m_pivotMotorEncoder.getPosition(), m_pivotMotorEncoder.getVelocity());
    }
    private boolean isMotionProfileFinished() {
        return m_wpiPidController.getSetpoint().equals(m_wpiPidController.getGoal());
    }

    public void moveArmToAngle(double goalAngle) {
        m_armGoalAngle = goalAngle;
        m_wpiPidController.calculate(m_pivotMotorEncoder.getPosition(), m_armGoalAngle);
        TrapezoidProfile.State profileSetpointDegrees = m_wpiPidController.getSetpoint();
        m_profileVelocityGoalEntry.setNumber(profileSetpointDegrees.velocity);
        m_profilePositionGoalEntry.setNumber(profileSetpointDegrees.position);

        double feedForwardVolts = m_wpiFeedForward.calculate(
            Units.degreesToRadians(profileSetpointDegrees.position),
            Units.degreesToRadians(profileSetpointDegrees.velocity));
        m_pidArbitraryFeedForwardEntry.setNumber(feedForwardVolts);

        if (isMotionProfileFinished()) {
            m_sparkPidController.setReference(m_armGoalAngle, CANSparkMax.ControlType.kPosition, 0, feedForwardVolts);
        } else {
            m_pivotMotor.setVoltage(feedForwardVolts);
        }
    }

    // Command Factory //

    private Command createResetWpiPidControllerCommand() { return runOnce(this::resetWpiPidController); }
    public Command createMoveArmToAngle(double goalAngle) {
        return createResetWpiPidControllerCommand()
            .andThen(runOnce(() -> moveArmToAngle(goalAngle)));
    }

}
