package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.feedforward.ElevatorFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;
import org.snobotv2.module_wrappers.BaseDigitalInputWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ElevatorSubsystem extends SubsystemBase {

    public static final double K_ELEVATOR_GEARING = 10.0;
    public static final double K_CARRIAGE_MASS = 4.0; // kg
    public static final double K_MIN_ELEVATOR_HEIGHT = Units.inchesToMeters(-4);
    public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(120);
    public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getVex775Pro(4);
    public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(2.0);


    private final SparkFlex m_elevatorMotor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_botLimitSwitch;
    private final DigitalInput m_topLimitSwitch;
    private final SparkMaxAlerts m_checkAlerts;
    private final LoggingUtil m_networkTableEntries;

    private final ProfiledPIDController m_profilePID;
    private final PidProperty m_sparkPidProperties;
    private final PidProperty m_profilePidProperties;
    private final ElevatorFeedForwardProperty m_wpiFeedForward;
    private final SparkClosedLoopController m_sparkPidController;

    private double m_goalHeight;


    private ElevatorSimWrapper m_simulator;

    public ElevatorSubsystem() {

        m_elevatorMotor = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_elevatorMotor.getEncoder();
        m_botLimitSwitch = new DigitalInput(Constants.BOTLIMITSWICTH_ID);
        m_topLimitSwitch = new DigitalInput(Constants.TOPLIMITSWITCH_ID);

        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        elevatorConfig.smartCurrentLimit(60);
        elevatorConfig.inverted(false);

        m_sparkPidController = m_elevatorMotor.getClosedLoopController();
        elevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        m_sparkPidProperties = new RevPidPropertyBuilder("Elevator", false, m_elevatorMotor, elevatorConfig, ClosedLoopSlot.kSlot0)
            .addP(0.18)
            .build();
        m_profilePID = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_profilePidProperties = new WpiProfiledPidPropertyBuilder("Elevator Profile PID", false, m_profilePID)
            .addMaxVelocity(0.5)
            .addMaxAcceleration(0.5)
            .build();
        m_wpiFeedForward = new ElevatorFeedForwardProperty("Elevator Profile ff", false)
            .addKs(0)
            .addKff(0)
            .addKg(0);

        m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

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
        m_networkTableEntries.addDouble("Setpoint Position", () -> m_profilePID.getSetpoint().position);
        m_networkTableEntries.addDouble("Setpoint Velocity", () -> m_profilePID.getSetpoint().velocity);
        m_networkTableEntries.addDouble("Goal Height", () -> m_goalHeight);
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

        m_sparkPidProperties.updateIfChanged();
        m_wpiFeedForward.updateIfChanged();
        m_profilePidProperties.updateIfChanged();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public double getEncoderVel() {
        return m_encoder.getVelocity();
    }

    public void goToHeight(double goalHeight) {
        m_profilePID.calculate(getHeight(), goalHeight);
        m_goalHeight = goalHeight;
        TrapezoidProfile.State setpoint = m_profilePID.getSetpoint();

        double feedForwardVolts = m_wpiFeedForward.calculateWithVelocities(
            getEncoderVel(),
            setpoint.velocity);

        m_sparkPidController.setReference(setpoint.position, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedForwardVolts);
        SmartDashboard.putNumber("feedForwardVolts", feedForwardVolts);

    }

    public void resetPidController() {
        m_profilePID.reset(getHeight(), getEncoderVel());
    }

    //command factories//
    public Command createResetPidControllerCommand() {
        return runOnce(this::resetPidController);
    }

    public Command createMoveElevatorToHeightCommand(double height) {
        return createResetPidControllerCommand().andThen(
        runEnd(() -> goToHeight(height), m_elevatorMotor::stopMotor)).withName("Elevator go to height" + height);
    }
}

