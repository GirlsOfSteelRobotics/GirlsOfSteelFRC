package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledSingleJointedArmController;
import com.gos.reefscape.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;


public class PivotSubsystem extends SubsystemBase {
    private static final double ALLOWABLE_ERROR = .5; //TODO change allowable error to make it more accurate or to make scoring faster
    private static final double PIVOT_ERROR = 3;
    private static final double GEAR_RATIO = 45.0; // reduction
    public static final double DEFAULT_ANGLE = -28 + 2;
    public static final double NO_GOAL_ANGLE = -360;

    private final SparkFlex m_pivotMotor;
    private final RelativeEncoder m_relativeEncoder;
    private final DutyCycleEncoder m_absoluteEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_checkAlerts;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    public static final GosDoubleProperty PIVOT_TUNABLE_ANGLE = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "tunablePivot", -220);


    private final RevProfiledSingleJointedArmController m_armPidController;

    private double m_armGoalAngle = NO_GOAL_ANGLE;

    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR_ID, MotorType.kBrushless);
        m_relativeEncoder = m_pivotMotor.getEncoder();
        m_absoluteEncoder = new DutyCycleEncoder(Constants. PIVOT_ABSOLUTE_ENCODER, 360, 248 - 14 - 6);


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(true);

        m_armPidController = new RevProfiledSingleJointedArmController.Builder("Arm Pivot", Constants.DEFAULT_CONSTANT_PROPERTIES, m_pivotMotor, pivotConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(360)
            .addMaxAcceleration(540)
            // Arm FF
            .addKs(0)
            .addKv(1.0)
            .addKg(0.1)
            // REV Position controller
            .addKp(.05)
            .build();

        pivotConfig.signals.absoluteEncoderPositionPeriodMs(20);
        pivotConfig.signals.absoluteEncoderVelocityPeriodMs(20);

        pivotConfig.encoder.positionConversionFactor(360.0 / GEAR_RATIO);
        pivotConfig.encoder.velocityConversionFactor(360.0 / GEAR_RATIO / 60);

        m_networkTableEntries = new LoggingUtil("Pivot");
        m_networkTableEntries.addDouble("Speed", this::getSpeed);
        m_networkTableEntries.addDouble("Relative Angle", this::getRelativeAngle);
        m_networkTableEntries.addDouble("Absolute Angle", this::getAbsoluteAngle);
        m_networkTableEntries.addDouble("Goal angle", this::getArmGoalAngle);
        m_networkTableEntries.addDouble("Setpoint angle", m_armPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Velocity", m_armPidController::getVelocitySetpoint);
        m_networkTableEntries.addDouble("Rel Encoder Velocity", m_relativeEncoder::getVelocity);
        m_networkTableEntries.addBoolean("Is at goal", this::isPivotAtGoal);
        m_checkAlerts = new SparkMaxAlerts(m_pivotMotor, "Pivot Alert");
        m_networkTableEntries.addDouble("Percent Output", m_pivotMotor::getAppliedOutput);


        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeoVortex(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, GEAR_RATIO, .01,
                0.381, Units.degreesToRadians(-220), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        syncRelativeEncoder();
        m_relativeEncoder.setPosition(DEFAULT_ANGLE);
    }

    public void clearStickyFaults() {
        m_pivotMotor.clearFaults();
    }

    @SuppressWarnings("removal")
    public void moveArmToAngle(double goal) {
        if (Math.abs(m_armGoalAngle - goal) > 2) {
            resetPidController();
        }
        m_armGoalAngle = goal;

        // m_armPidController.goToAngleWithVelocities(goal, getRelativeAngle(), getRelativeVelocity());
        m_armPidController.goToAngle(goal, getAbsoluteAngle());
    }

    public void moveArmToTunableAngle() {
        moveArmToAngle(PIVOT_TUNABLE_ANGLE.getValue());
    }


    private void syncRelativeEncoder() {
        m_relativeEncoder.setPosition(getAbsoluteAngle());
    }

    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }


    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_checkAlerts.checkAlerts();

        m_armPidController.updateIfChanged();
        if (DriverStation.isDisabled()) {
            syncRelativeEncoder();
        }

        //        double encoderDelta = Math.abs(getRelativeAngle() - getAbsoluteAngle());
        //        if (encoderDelta > 5 && getRelativeVelocity() < 5) {
        //            syncRelativeEncoder();
        //        }
    }


    public boolean isPivotAtGoal() {
        double error = m_armGoalAngle - getRelativeAngle();
        return Math.abs(error) < ALLOWABLE_ERROR;
    }

    public double getArmGoalAngle() {
        return m_armGoalAngle;
    }

    public double getSpeed() {
        return m_pivotMotor.getAppliedOutput();
    }

    public void stop() {
        m_armGoalAngle = NO_GOAL_ANGLE;
        m_pivotMotor.set(0);
    }

    public void setSpeed(double speed) {
        m_pivotMotor.set(speed);
    }

    public double getRelativeAngle() {
        return m_relativeEncoder.getPosition();
    }

    public double getRelativeVelocity() {
        return m_relativeEncoder.getVelocity();
    }

    public final double getAbsoluteAngle() {
        double angle = -m_absoluteEncoder.get();
        if (angle < -300) {
            angle += 360;
        }
        return angle;
    }

    private void resetPidController() {
        m_armPidController.resetPidController(getRelativeAngle(), getRelativeVelocity());
    }

    public boolean isAtGoalAngle() {
        return isAtGoalAngle(getArmGoalAngle());
    }

    public boolean isAtGoalAngle(double goal) {
        return (Math.abs(getRelativeAngle() - goal) <= PIVOT_ERROR);
    }


    public void setVoltage(double outputVolts) {
        m_pivotMotor.setVoltage(outputVolts);
    }

    public double getVoltage() {
        if (RobotBase.isReal()) {
            return m_pivotMotor.getBusVoltage();
        }
        return m_pivotMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
    }




    public void setIdleMode(IdleMode idleMode) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(idleMode);
        m_pivotMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void addPivotDebugCommands(boolean isComp) {
        ShuffleboardTab debugTabPivot = Shuffleboard.getTab("arm pivot");
        if (!isComp) {
            debugTabPivot.add(createMovePivotToAngleCommand(0.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-15.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-30.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-45.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-90.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-135.0));
            debugTabPivot.add(createMovePivotToAngleCommand(-180.0));
        }
        debugTabPivot.add(createPivotoCoastModeCommand());
        debugTabPivot.add(createResetEncoderCommand().withName("Reset pivot"));
        debugTabPivot.add(createPivotToTunableAngleCommand().withName("Pivot to tunable angle"));
    }

    ////////////////
    //command factories
    ////////////////
    ///
    public Command createMovePivotToAngleCommand(double angle) {
        return runEnd(() -> moveArmToAngle(angle), this::stop)
            .withName("Go to angle" + angle);
    }

    public Command createResetEncoderCommand() {
        return run(() -> m_relativeEncoder.setPosition(DEFAULT_ANGLE)).ignoringDisable(true);
    }

    public Command createPivotoCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(IdleMode.kCoast),
                () -> setIdleMode(IdleMode.kBrake))
            .ignoringDisable(true).withName("Pivot to Coast");
    }

    public Command createPivotToTunableAngleCommand() {
        return runEnd(this::moveArmToTunableAngle, this::stop).withName("pivot to tunable angle ");
    }


    public Command createElevatorToCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(IdleMode.kCoast),
                () -> setIdleMode(IdleMode.kBrake))
            .ignoringDisable(true).withName("Elevator to Coast");
    }


}


