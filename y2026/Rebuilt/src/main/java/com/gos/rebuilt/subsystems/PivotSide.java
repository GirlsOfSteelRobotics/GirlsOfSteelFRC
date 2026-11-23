package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import org.wpilib.hardware.bus.CANPort;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledSingleJointedArmController;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
// import org.wpilib.math.system.DCMotor;
// import org.wpilib.math.util.Units;
// import org.wpilib.framework.RobotBase;
// import org.wpilib.simulation.SingleJointedArmSim;
// import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
// import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
// import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class PivotSide {

    private static final double GEAR_RATIO = 3 * 3 * 4;
    public static final double STARTING_ANGLE = 100;
    public static final double DEPLOYED_ANGLE = -6;

    // TODO(2027) REVLib removed SPARK conversion factors, so they are applied in software.
    //  The SPARK closed loop gains now operate on native units (rotations) and need re-tuning.
    private static final double RELATIVE_POSITION_CONVERSION_FACTOR = 360 / GEAR_RATIO; // degrees per motor rotation
    private static final double RELATIVE_VELOCITY_CONVERSION_FACTOR = 360 / GEAR_RATIO / 60; // degrees per second per RPM
    private static final double ABSOLUTE_POSITION_CONVERSION_FACTOR = 360; // degrees per rotation

    private final SparkFlex m_pivotMotor;
    private final AbsoluteEncoder m_absoluteEncoder;
    private final RelativeEncoder m_relativeEncoder;
    private final SparkMaxAlerts m_pivotMotorAlerts;
    private final LoggingUtil m_networkTableEntries;


    private double m_armGoalAngle = 90;
    private final RevProfiledSingleJointedArmController m_armPidController;

    private final GosDoubleProperty m_tuningPivotSpeed;


    // TODO(2027) restore simulation once SnobotSim supports 2027
    // private SingleJointedArmSimWrapper m_pivotSimulator;

    public PivotSide(int id, boolean inverted, double offset, String name) {


        m_pivotMotor = new SparkFlex(CANPort.CAN_S0, id, MotorType.kBrushless);
        m_relativeEncoder = m_pivotMotor.getEncoder();
        m_tuningPivotSpeed = new GosDoubleProperty(false, "Pivot Speed", -0.05);

        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder();

        m_pivotMotorAlerts = new SparkMaxAlerts(m_pivotMotor, "pivotMotor");


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(inverted);
        pivotConfig.absoluteEncoder.inverted(inverted);
        pivotConfig.absoluteEncoder.zeroCentered(true);
        pivotConfig.absoluteEncoder.zeroOffset(offset);

        m_armPidController = new RevProfiledSingleJointedArmController.Builder("Arm Pivot", false, m_pivotMotor, pivotConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(360)
            .addMaxAcceleration(540)
            // Arm FF
            .addKs(0)
            .addKv(1.0)
            .addKg(0.1)
            // REV Position controller
            .addKp(.05)
            .withPositionConversionFactor(RELATIVE_POSITION_CONVERSION_FACTOR)
            .build();

        // TODO(2027) restore simulation once SnobotSim supports 2027
        // if (RobotBase.isSimulation()) {
        //     DCMotor gearbox = DCMotor.getNeoVortex(1);
        //     SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, GEAR_RATIO, .01,
        //         0.381, Units.degreesToRadians(-5), Units.degreesToRadians(90), true, 0);
        //     m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
        //         RevEncoderSimWrapper.create(m_pivotMotor), true);
        // }

        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries = new LoggingUtil("Pivot Subsystem: " + name);

        m_networkTableEntries.addDouble("Pivot Position", this::getPosition);
        m_networkTableEntries.addDouble("Pivot Velocity", this::getVelocity);
        m_networkTableEntries.addDouble("Applied Output", () -> m_pivotMotor.getAppliedOutput().get());
        m_networkTableEntries.addDouble("Pivot goal", this::getGoalAngle);
        m_networkTableEntries.addDouble("Setpoint angle", m_armPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Velocity", m_armPidController::getVelocitySetpoint);
        m_networkTableEntries.addDouble("Absolute Encoder Position", this::getAbsolutePosition);

        syncEncoders();
    }


    public double getPosition() {
        return m_relativeEncoder.getPosition().get() * RELATIVE_POSITION_CONVERSION_FACTOR;
    }

    public final void syncEncoders() {
        m_relativeEncoder.setPosition(getAbsolutePosition() / RELATIVE_POSITION_CONVERSION_FACTOR);
    }

    public final double getAbsolutePosition() {
        return m_absoluteEncoder.getPosition().get() * ABSOLUTE_POSITION_CONVERSION_FACTOR;
    }

    public double getVelocity() {
        return m_relativeEncoder.getVelocity().get() * RELATIVE_VELOCITY_CONVERSION_FACTOR;
    }


    public void setSpeed(double pow) {
        m_pivotMotor.setThrottle(pow);
    }

    public void stop() {
        m_pivotMotor.stopMotor();
    }

    public double getAngle() {
        return getPosition();
    }

    public void clearStickyFaults() {
        m_pivotMotor.clearFaults();
    }


    private void resetPidController() {
        m_armPidController.resetPidController(getPosition(), getVelocity());
    }

    public final double getAbsoluteAngle() {
        double angle = getPosition();
        if (angle < -300) {
            angle += 360;
        }
        return angle;
    }

    public double getGoalAngle() {
        return m_armGoalAngle;
    }

    public void setIdleMode(IdleMode idleMode) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(idleMode);
        m_pivotMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @SuppressWarnings("removal")
    public void moveArmToAngle(double goal) {
        if (Math.abs(m_armGoalAngle - goal) > 2) {
            System.out.println("Resetting controller" + m_armGoalAngle + ", " + goal);
            resetPidController();
        }
        m_armGoalAngle = goal;

        m_armPidController.goToAngle(goal, getAbsoluteAngle());
        // m_armPidController.goToAngleWithVelocities(goal, getAngle(), getVelocity());
    }


    public void resetEncoderUp() {
        m_relativeEncoder.setPosition(STARTING_ANGLE / RELATIVE_POSITION_CONVERSION_FACTOR);
    }

    public void resetEncoderDown() {
        m_relativeEncoder.setPosition(DEPLOYED_ANGLE / RELATIVE_POSITION_CONVERSION_FACTOR);
    }

    public void movePivotAtTuningSpeed() {
        setSpeed(m_tuningPivotSpeed.getValue());
    }

    public void simulationPeriodic() {
        // TODO(2027) restore simulation once SnobotSim supports 2027
        // m_pivotSimulator.update();
    }

    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_pivotMotorAlerts.checkAlerts();
        m_armPidController.updateIfChanged();
    }


}
