package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;


public class ArmPivotSubsystem extends SubsystemBase {
    private final SimableCANSparkMax m_pivotMotor;
    private final SimableCANSparkMax m_followMotor;
    private final RelativeEncoder m_pivotMotorEncoder;
    private final AbsoluteEncoder m_pivotAbsEncoder;
    private final LoggingUtil m_networkTableEntriesPivot;
    private final SparkMaxAlerts m_armPivotMotorErrorAlerts;

    private final SparkPIDController m_sparkPidController;
    private final PidProperty m_sparkPidProperties;
    private final ArmFeedForwardProperty m_wpiFeedForward;
    private double m_armGoalAngle;
    private SingleJointedArmSimWrapper m_pivotSimulator;

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
        m_sparkPidController.setFeedbackDevice(m_pivotAbsEncoder);
        m_sparkPidProperties = new RevPidPropertyBuilder("Arm Pivot", false, m_sparkPidController, 0)
            .addP(0)
            .addI(0)
            .addD(0)
            .addMaxAcceleration(80)
            .addMaxVelocity(80)
            .build();

        m_wpiFeedForward = new ArmFeedForwardProperty("Arm Pivot Profile ff", false)
            .addKff(0)
            .addKg(0);

        m_networkTableEntriesPivot = new LoggingUtil("Arm Pivot Subsystem");
        m_networkTableEntriesPivot.addDouble("Output", m_pivotMotor::getAppliedOutput);
        m_networkTableEntriesPivot.addDouble("Abs Encoder Value", m_pivotAbsEncoder::getPosition);
        m_networkTableEntriesPivot.addDouble("Rel Encoder Value", m_pivotMotorEncoder::getPosition);


        m_armPivotMotorErrorAlerts = new SparkMaxAlerts(m_pivotMotor, "arm pivot motor");

        m_pivotMotor.burnFlash();
        m_followMotor.burnFlash();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), 252, 1,
                0.381, 0, Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

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

        m_sparkPidController.setReference(m_armGoalAngle, CANSparkMax.ControlType.kPosition, 0, feedForwardVolts);

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
        if (RobotBase.isSimulation()) {
            return m_pivotMotorEncoder.getPosition();
        }
        else {
            return m_pivotAbsEncoder.getPosition();
        }
    }

    public void setArmMotorSpeed(double speed) {
        m_pivotMotor.set(speed);
    }

    // Command Factory //

    public Command createMoveArmToAngle(double goalAngle) {
        return runEnd(() -> moveArmToAngle(goalAngle), this::stopArmMotor).withName("arm to " + goalAngle);
    }

    public double getArmAngleGoal() {
        return m_armGoalAngle;
    }

    public double getPivotMotorPercentage() {
        return m_pivotMotor.getAppliedOutput();
    }
}
