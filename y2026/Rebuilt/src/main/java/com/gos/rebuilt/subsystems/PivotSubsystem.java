package com.gos.rebuilt.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledSingleJointedArmController;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class PivotSubsystem extends SubsystemBase {

    private final SparkFlex m_pivotMotor;
    private static final double GEAR_RATIO = 45.0;
    private final RelativeEncoder m_encoder;
    private final SparkMaxAlerts m_pivotMotorAlerts;
    private final LoggingUtil m_networkTableEntries;
    private double m_armGoalAngle = 90;
    private final RevProfiledSingleJointedArmController m_armPidController;


    private SingleJointedArmSimWrapper m_pivotSimulator;


    public PivotSubsystem() {


        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR, MotorType.kBrushless);
        m_encoder = m_pivotMotor.getEncoder();


        m_pivotMotorAlerts = new SparkMaxAlerts(m_pivotMotor, "pivotMotor");


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(false);
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

        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeoVortex(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, GEAR_RATIO, .01,
                0.381, Units.degreesToRadians(-5), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }

        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries = new LoggingUtil("Pivot Subsystem");

        m_networkTableEntries.addDouble("Pivot Position", this::getPosition);
        m_networkTableEntries.addDouble("Pivot Velocity", this::getVelocity);
        m_networkTableEntries.addDouble("Applied Output", m_pivotMotor::getAppliedOutput);
        m_networkTableEntries.addDouble("Pivot goal", this::getGoalAngle);
        m_networkTableEntries.addDouble("Setpoint angle", m_armPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Velocity", m_armPidController::getVelocitySetpoint);
    }

    public double getPosition() {
        return m_encoder.getPosition();
    }

    public double getVelocity() {
        return m_encoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_pivotMotorAlerts.checkAlerts();
        m_armPidController.updateIfChanged();
    }

    public void setSpeed(double pow) {
        m_pivotMotor.set(pow);
    }

    public void stop() {
        m_pivotMotor.stopMotor();
    }

    public double getAngle() {
        return m_encoder.getPosition();
    }


    private void resetPidController() {
        m_armPidController.resetPidController(getPosition(), getVelocity());
    }

    public final double getAbsoluteAngle() {
        double angle = -m_encoder.getPosition();
        if (angle < -300) {
            angle += 360;
        }
        return angle;
    }

    public double getGoalAngle() {
        return m_armGoalAngle;
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


    @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }

    public void addPivotDebugCommands() {
        ShuffleboardTab debugTabPivot = Shuffleboard.getTab("arm pivot");

        debugTabPivot.add(createMovePivotToAngleCommand(0.0));
        debugTabPivot.add(createMovePivotToAngleCommand(15.0));
        debugTabPivot.add(createMovePivotToAngleCommand(30.0));
        debugTabPivot.add(createMovePivotToAngleCommand(45.0));
        debugTabPivot.add(createMovePivotToAngleCommand(90.0));

        debugTabPivot.add(createMovePivotUpCommand());
        debugTabPivot.add(createMovePivotDownCommand());
    }

    public Command createMovePivotDownCommand() {
        return runEnd(() -> moveArmToAngle(0), this::stop)
            .withName("Go down");
    }

    public Command createMovePivotUpCommand() {
        return runEnd(() -> moveArmToAngle(90), this::stop)
            .withName("Go up");
    }

    public Command createMovePivotToAngleCommand(double angle) {
        return runEnd(() -> moveArmToAngle(angle), this::stop)
            .withName("Go to angle" + angle);
    }


}

