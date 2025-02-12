package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledElevatorController;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;
import org.snobotv2.module_wrappers.BaseDigitalInputWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ElevatorSubsystem extends SubsystemBase {

    public static final double K_ELEVATOR_GEARING = 12.0;
    public static final double K_CARRIAGE_MASS = 4.0; // kg
    public static final double K_MIN_ELEVATOR_HEIGHT = Units.inchesToMeters(-4);
    public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(120);
    public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getNeoVortex(1);
    public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(2.0);
    public static final double ELEVATOR_ERROR = Units.inchesToMeters(3);


    private final SparkFlex m_elevatorMotor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_botLimitSwitch;
    private final DigitalInput m_topLimitSwitch;
    private final SparkMaxAlerts m_checkAlerts;
    private final LoggingUtil m_networkTableEntries;

    private final SparkFlex m_followMotor;
    private final SparkMaxAlerts m_elevatorFollowerErrorAlerts;


    private final RevProfiledElevatorController m_elevatorPidController;

    private double m_goalHeight;


    private ElevatorSimWrapper m_simulator;

    public ElevatorSubsystem() {

        m_elevatorMotor = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_followMotor = new SparkFlex(Constants.ELEVATOR_FOLLOW_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_elevatorMotor.getEncoder();
        m_botLimitSwitch = new DigitalInput(Constants.BOTLIMITSWICTH_ID);
        m_topLimitSwitch = new DigitalInput(Constants.TOPLIMITSWITCH_ID);

        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        elevatorConfig.smartCurrentLimit(60);
        elevatorConfig.inverted(true);

        elevatorConfig.encoder.positionConversionFactor(1 / K_ELEVATOR_GEARING);
        elevatorConfig.encoder.velocityConversionFactor(1 / K_ELEVATOR_GEARING / 60);


        SparkMaxConfig followMotorConfig = new SparkMaxConfig();
        followMotorConfig.follow(m_elevatorMotor, true);
        followMotorConfig.idleMode(IdleMode.kBrake);
        followMotorConfig.smartCurrentLimit(60);

        elevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        m_elevatorPidController = new RevProfiledElevatorController.Builder("Elevator", false, m_elevatorMotor, elevatorConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(Units.inchesToMeters(20))
            .addMaxAcceleration(Units.inchesToMeters(20))
            // Elevator FF
            .addKs(0)
            .addKv(0)
            .addKg(0)
            .addKa(0)
            // REV Position controller
            .addKp(0)
            .build();

        m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_elevatorFollowerErrorAlerts = new SparkMaxAlerts(m_followMotor, "elevator follower");
        m_followMotor.configure(followMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);



        m_checkAlerts = new SparkMaxAlerts(m_elevatorMotor, "Elevator Alert");



        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING,
                K_CARRIAGE_MASS,
                K_ELEVATOR_DRUM_RADIUS,
                K_MIN_ELEVATOR_HEIGHT,
                K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_simulator = new ElevatorSimWrapper(sim,
                new RevMotorControllerSimWrapper(m_elevatorMotor, K_ELEVATOR_GEARBOX),
                RevEncoderSimWrapper.create(m_elevatorMotor));
            m_simulator.setLowerLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_botLimitSwitch)::setValue));
            m_simulator.setUpperLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_topLimitSwitch)::setValue));


        }

        m_networkTableEntries = new LoggingUtil("Elevator");
        m_networkTableEntries.addDouble("current height", this::getHeight);
        m_networkTableEntries.addBoolean("Top Limit Switch", this::isAtTop);
        m_networkTableEntries.addBoolean("Bottom Limit Switch", this::isAtBottom);
        m_networkTableEntries.addDouble("Velocity", this::getEncoderVel);
        m_networkTableEntries.addDouble("Setpoint Position", m_elevatorPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Velocity", m_elevatorPidController::getVelocitySetpoint);
        m_networkTableEntries.addDouble("Goal Height", () -> m_goalHeight);
        m_networkTableEntries.addBoolean("Is at good height", this::isAtGoalHeight);
        m_networkTableEntries.addDouble("Percent Output", m_elevatorMotor::getAppliedOutput);
    }

    public void clearStickyFaults() {
        m_elevatorMotor.clearFaults();
        m_followMotor.clearFaults();
    }


    public double getHeight() {
        return m_encoder.getPosition();
    }

    public void setSpeed(double speed) {
        m_elevatorMotor.set(speed);
    }

    public boolean isAtBottom() {
        return m_botLimitSwitch.get();
    }

    public boolean isAtTop() {
        return m_topLimitSwitch.get();
    }

    public void stop() {
        m_elevatorMotor.set(0);
    }

    public double getGoalHeight() {
        return m_goalHeight;
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_checkAlerts.checkAlerts();

        m_elevatorPidController.updateIfChanged();
        m_elevatorFollowerErrorAlerts.checkAlerts();

    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public double getEncoderVel() {
        return m_encoder.getVelocity();
    }

    public void goToHeight(double goalHeight) {
        m_goalHeight = goalHeight;
        m_elevatorPidController.goToHeight(goalHeight, getHeight(), getEncoderVel());
    }

    public void resetPidController() {
        m_elevatorPidController.resetPidController(getHeight(), getEncoderVel());
    }

    public boolean isAtGoalHeight() {
        return Math.abs(getHeight() - m_goalHeight) <= ELEVATOR_ERROR;
    }

    public void setVoltage(double outputVolts) {
        m_elevatorMotor.setVoltage(outputVolts);
    }

    public double getVoltage() {
        if (RobotBase.isReal()) {
            return m_elevatorMotor.getBusVoltage();
        }
        return m_elevatorMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
    }

    public void setIdleMode(IdleMode idleMode) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(idleMode);
        m_elevatorMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        m_followMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    //command factories//
    public Command createResetPidControllerCommand() {
        return runOnce(this::resetPidController);
    }

    public Command createMoveElevatorToHeightCommand(double height) {
        return createResetPidControllerCommand().andThen(
        runEnd(() -> goToHeight(height), m_elevatorMotor::stopMotor)).withName("Elevator go to height" + height);
    }

    public Command createResetEncoderCommand() {
        return run(() -> m_encoder.setPosition(0));
    }

    public Command createElevatorToCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(IdleMode.kCoast),
                () -> setIdleMode(IdleMode.kBrake))
            .ignoringDisable(true).withName("Elevator to Coast");
    }





}

