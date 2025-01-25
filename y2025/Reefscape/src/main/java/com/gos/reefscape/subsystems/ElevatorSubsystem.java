package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
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

    private ElevatorSimWrapper m_simulator;

    public ElevatorSubsystem() {

        m_elevatorMotor = new SparkFlex(Constants.ELEVATOR_MOTOR_ID, MotorType.kBrushless);
        m_encoder = m_elevatorMotor.getEncoder();
        m_botLimitSwitch = new DigitalInput(Constants.BOTLIMITSWICTH_ID);
        m_topLimitSwitch = new DigitalInput(Constants.TOPLIMITSWITCH_ID);

        m_networkTableEntries = new LoggingUtil("Elevator");
        m_networkTableEntries.addDouble("current height", this::getHeight);
        m_networkTableEntries.addBoolean("Top Limit Switch", this::isAtTop);
        m_networkTableEntries.addBoolean("Bottom Limit Switch", this::isAtBottom);

        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        elevatorConfig.smartCurrentLimit(60);
        elevatorConfig.inverted(false);

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

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_checkAlerts.checkAlerts();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}

