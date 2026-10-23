package frc.robot.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledElevatorController;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Unit;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.snobotv2.module_wrappers.BaseDigitalInputWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ElevatorSubsystem extends SubsystemBase {
    public static final double K_ELEVATOR_GEARING = 12.0;
    public static final double K_CARRIAGE_MASS = Units.lbsToKilograms(9);
    public static final double K_MIN_ELEVATOR_HEIGHT = Units.inchesToMeters(-4);
    public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(120);
    public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getNeoVortex(2);
    public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(1.0);
    public static final double ELEVATOR_STAGES = 2;
    public static final double ELEVATOR_GEAR_CIRCUMFERENCE = Units.inchesToMeters(2 * K_ELEVATOR_DRUM_RADIUS * Math.PI);
    public static final double ELEVATOR_ERROR = Units.inchesToMeters(1);
    public static final GosDoubleProperty ELEVATOR_TUNABLE_HEIGHT = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "tunableElevator", 0.25);
    public static final double NO_GOAL_HEIGHT = Units.inchesToMeters(-50); // The fake number to use to specify there is no goal height

    private final SparkFlex m_leader;
    private final SparkFlex m_follower;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_botLimitSwitch;
    private final DigitalInput m_topLimitSwitch;
    private final SparkMaxAlerts m_checkAlerts;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_elevatorFollowerErrorAlerts;
    private final RevProfiledElevatorController m_elevatorPidController;
   private double m_goalHeight = NO_GOAL_HEIGHT;
   private ElevatorSimWrapper m_simulator;


    public ElevatorSubsystem() {
        m_leader = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_follower = new SparkFlex(Constants.ELEVATOR_FOLLOW_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_leader.getEncoder();
        m_botLimitSwitch = new DigitalInput(Constants.BOTLIMITSWITCH_ID);
        m_topLimitSwitch = new DigitalInput(Constants.TOPLIMITSWITCH_ID);

        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        elevatorConfig.smartCurrentLimit(60);
        elevatorConfig.inverted(true);

        double conversionFactor = Units.inchesToMeters(44 - 16.5) / 30.45;
        elevatorConfig.encoder.positionConversionFactor(conversionFactor);
        elevatorConfig.encoder.velocityConversionFactor(conversionFactor / 60);

        SparkMaxConfig followMotorConfig = new SparkMaxConfig();
        followMotorConfig.follow(m_leader, true);
        followMotorConfig.idleMode(IdleMode.kBrake);
        followMotorConfig.smartCurrentLimit(60);

        elevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        m_elevatorPidController = new RevProfiledElevatorController.Builder("Elevator",Constants.DEFAULT_CONSTANT_PROPERTIES,m_leader,elevatorConfig, ClosedLoopSlot.kSlot0)
            //speed limits
            .addMaxVelocity(2)
            .addMaxAcceleration(2.25)
            //elevator ff
            .addKs(0)
            .addKs(0)
            .addKg(.45)
            .addKa(0)
            //REV position controller
            .addKp(4)
            .build();
        m_leader.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_elevatorFollowerErrorAlerts = new SparkMaxAlerts(m_follower, "elevator follower");
        m_follower.configure(followMotorConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        m_checkAlerts = new SparkMaxAlerts(m_elevatorMotor, "Elevator Alert");


        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING * 0.5
                K_CARRIAGE_MASS,
                K_ELEVATOR_DRUM_RADIUS,
                K_MIN_ELEVATOR_HEIGHT,
                K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_simulator = new ElevatorSimWrapper(sim,
                new RevMotorControllerSimWrapper(m_leader, K_ELEVATOR_GEARBOX),
                RevEncoderSimWrapper.create(m_leader));
            m_simulator.setLowerLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_botLimitSwitch)::setValue));
            m_simulator.setUpperLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_topLimitSwitch)::setValue));
        }

        m_networkTableEntries = new LoggingUtil("Elevator");
        m_networkTableEntries.addDouble("current height", this::getHeight);
        m_networkTableEntries.addDouble("current height (inches)", () -> Units.metersToInches(getHeight()));
        m_networkTableEntries.addBoolean("Top Limit Switch", this::isAtTop);
        m_networkTableEntries.addBoolean("Bottom Limit Switch", this::isAtBottom);
        m_networkTableEntries.addDouble("Velocity", this::getEncoderVel);
        m_networkTableEntries.addDouble("Setpoint Position", m_elevatorPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Velocity", m_elevatorPidController::getVelocitySetpoint);
        m_networkTableEntries.addDouble("Goal Height", () -> m_goalHeight);
        m_networkTableEntries.addBoolean("Is at good height", this::isAtGoalHeight);
        m_networkTableEntries.addDouble("Percent Output", m_elevatorMotor::getAppliedOutput);

    }

        public double getHeight() {
        return m_encoder.getPosition();
    }

    public void setThrottle(double throttle) {
        m_leader.set(throttle);
    }

    public boolean isAtBottom() {
        return m_botLimitSwitch.get();
    }

    public boolean isAtTop() {
        return m_topLimitSwitch.get();
    }

    public void clearStickyFaults() {
        m_leader.clearFaults();
        m_follower.clearFaults();
    }

    public void stop() {
        m_goalHeight = NO_GOAL_HEIGHT;
        m_leader.set(0);
    }

    public double getGoalHeight() {
        return m_goalHeight;
    }

    public double getEncoderVel() {
        return m_encoder.getVelocity();
    }


}
