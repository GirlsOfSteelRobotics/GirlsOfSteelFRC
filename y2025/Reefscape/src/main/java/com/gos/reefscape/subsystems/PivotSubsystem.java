package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.feedforward.ArmFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.reefscape.Constants;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;


public class PivotSubsystem extends SubsystemBase {
    private final SparkFlex m_pivotMotor;
    private final RelativeEncoder m_relativeEncoder;
    private final AbsoluteEncoder m_absoluteEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_checkAlerts;
    private SingleJointedArmSimWrapper m_pivotSimulator;

    private static final double PIVOT_ERROR = 3;
    private static final double GEAR_RATIO = (58.0 / 15.0) * 45;

    private final PidProperty m_sparkPidProperties;
    private final SparkClosedLoopController m_sparkPidController;

    private final ProfiledPIDController m_profilePID;
    private final PidProperty m_profilePidProperties;

    private final ArmFeedForwardProperty m_wpiFeedForward;
    private static final double ALLOWABLE_ERROR = .5; //TODO change allowable error to make it more accurate or to make scoring faster
    private double m_armGoalAngle = Double.MIN_VALUE;

    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR_ID, MotorType.kBrushless);
        m_relativeEncoder = m_pivotMotor.getEncoder();
        m_absoluteEncoder = m_pivotMotor.getAbsoluteEncoder();


        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig.idleMode(IdleMode.kBrake);
        pivotConfig.smartCurrentLimit(60);
        pivotConfig.inverted(false);

        m_sparkPidController = m_pivotMotor.getClosedLoopController();

        pivotConfig.closedLoop.positionWrappingEnabled(true);
        pivotConfig.closedLoop.positionWrappingMinInput(0);
        pivotConfig.closedLoop.positionWrappingMaxInput(360);
        m_sparkPidProperties = new RevPidPropertyBuilder("Arm Pivot", false, m_pivotMotor, pivotConfig, ClosedLoopSlot.kSlot0)
            .addP(0)
            .build();
        m_profilePID = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_profilePID.enableContinuousInput(0, 360);
        m_profilePidProperties = new WpiProfiledPidPropertyBuilder("Arm Profile PID", false, m_profilePID)
            .addMaxVelocity(200)
            .addMaxAcceleration(300)
            .build();
        m_wpiFeedForward = new ArmFeedForwardProperty("Arm Pivot Profile ff", false)
            .addKs(0)
            .addKff(2.2)
            .addKg(1.2);

        pivotConfig.signals.absoluteEncoderPositionPeriodMs(20);
        pivotConfig.signals.absoluteEncoderVelocityPeriodMs(20);

        pivotConfig.encoder.positionConversionFactor(360.0 / GEAR_RATIO);
        pivotConfig.encoder.velocityConversionFactor(360.0 / GEAR_RATIO / 60);

        m_networkTableEntries = new LoggingUtil("Pivot");
        m_networkTableEntries.addDouble("Speed", this::getSpeed);
        m_networkTableEntries.addDouble("Relative Angle", this::getRelativeAngle);
        m_networkTableEntries.addDouble("Absolute Angle", this::getAbsoluteAngle);
        m_networkTableEntries.addDouble("Setpoint angle", this::getArmGoalAngle);
        m_networkTableEntries.addDouble("Setpoint Velocity", () -> m_profilePID.getSetpoint().velocity);
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
        double currentAngle = getRelativeAngle();
        m_profilePID.calculate(currentAngle, goal);
        TrapezoidProfile.State setpoint = m_profilePID.getSetpoint();

        //double feedForwardVolts = m_wpiFeedForward.calculateWithVelocities(currentAngle, m_relativeEncoder.getVelocity(), setpoint.velocity);
        double feedForwardVolts = m_wpiFeedForward.calculate(Math.toRadians(currentAngle), Math.toRadians(setpoint.velocity));

        m_sparkPidController.setReference(setpoint.position, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedForwardVolts);
        SmartDashboard.putNumber("feedForwardVolts", feedForwardVolts);
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

        m_sparkPidProperties.updateIfChanged();
        m_wpiFeedForward.updateIfChanged();
        m_profilePidProperties.updateIfChanged();
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
        m_profilePID.reset(getRelativeAngle(), getRelativeVelocity());
    }

    public boolean isAtGoalAngle() {
        return (Math.abs(this.getRelativeAngle() - this.getArmGoalAngle()) <= this.PIVOT_ERROR);
    }

    ////////////////
    //command factories yay :))
    ////////////////
    ///
    public Command createMoveArmtoAngleCommand(Double angle) {
        return createResetPidControllerCommand().andThen(runEnd(() -> moveArmToAngle(angle), this::stop)).withName("Go to angle" + angle);


    }

    private Command createResetPidControllerCommand() {
        return runOnce(this::resetPidController);
    }


}
