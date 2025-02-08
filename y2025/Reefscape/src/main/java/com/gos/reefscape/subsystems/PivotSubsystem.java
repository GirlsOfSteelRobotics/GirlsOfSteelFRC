package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledSingleJointedArmController;
import com.gos.reefscape.Constants;
import com.revrobotics.AbsoluteEncoder;
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
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;


public class PivotSubsystem extends SubsystemBase {
    private static final double ALLOWABLE_ERROR = .5; //TODO change allowable error to make it more accurate or to make scoring faster
    private static final double PIVOT_ERROR = 3;
    private static final double GEAR_RATIO = (58.0 / 15.0) * 45;

    private final SparkFlex m_pivotMotor;
    private final RelativeEncoder m_relativeEncoder;
    private final AbsoluteEncoder m_absoluteEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_checkAlerts;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    private final RevProfiledSingleJointedArmController m_armPidController;

    private double m_armGoalAngle = Double.MIN_VALUE;

    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR_ID, MotorType.kBrushless);
        m_relativeEncoder = m_pivotMotor.getEncoder();
        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder();


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(false);

        pivotConfig.closedLoop.positionWrappingEnabled(true);
        pivotConfig.closedLoop.positionWrappingMinInput(0);
        pivotConfig.closedLoop.positionWrappingMaxInput(360);

        m_armPidController = new RevProfiledSingleJointedArmController.Builder("Arm Pivot", false, m_pivotMotor, pivotConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(200)
            .addMaxAcceleration(300)
            // Arm FF
            .addKs(0)
            .addKv(0)
            .addKg(0)
            // REV Position controller
            .addKp(0)
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

        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, 252, 1,
                0.381, Units.degreesToRadians(-40), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        syncRelativeEncoder();
    }

    @SuppressWarnings("removal")
    public void moveArmToAngle(double goal) {
        m_armGoalAngle = goal;

        // m_armPidController.goToAngleWithVelocities(goal, getRelativeAngle(), getRelativeVelocity());
        m_armPidController.goToAngle(goal, getRelativeAngle());
    }


    private void syncRelativeEncoder() {
        m_relativeEncoder.setPosition(m_absoluteEncoder.getPosition());
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

    public double getAbsoluteAngle() {
        return m_absoluteEncoder.getPosition();
    }

    private void resetPidController() {
        m_armPidController.resetPidController(getRelativeAngle(), getRelativeVelocity());
    }

    public boolean isAtGoalAngle() {
        return (Math.abs(getRelativeAngle() - getArmGoalAngle()) <= PIVOT_ERROR);
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


    ////////////////
    //command factories
    ////////////////
    ///
    public Command createMoveArmtoAngleCommand(Double angle) {
        return createResetPidControllerCommand()
            .andThen(runEnd(() -> moveArmToAngle(angle), this::stop))
            .withName("Go to angle" + angle);


    }

    private Command createResetPidControllerCommand() {
        return runOnce(this::resetPidController);
    }


}


