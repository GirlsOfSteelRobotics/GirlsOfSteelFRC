package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.commands.ElevatorToPositionCommand;
import com.gos.lib.logging.LoggingUtil;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.BaseDigitalInputWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ElevatorSubsystem extends SubsystemBase implements AutoCloseable {
    public static final double ALLOWABLE_POSITION_ERROR = Units.inchesToMeters(1);

    public enum Positions {
        LOW(Units.inchesToMeters(10)),
        MID(Units.inchesToMeters(35)),
        HIGH(Units.inchesToMeters(45));

        public final double m_heightMeters;

        Positions(double heightMeters) {
            m_heightMeters = heightMeters;
        }
    }


    private final SparkMax m_liftMotor;
    private final RelativeEncoder m_liftEncoder;
    private final DigitalInput m_lowerLimitSwitch;
    private final DigitalInput m_upperLimitSwitch;

    private final LoggingUtil m_loggingUtil;
    private final Mechanism2d m_mechanism;
    private final MechanismLigament2d m_elevatorLigament;

    private ElevatorSimWrapper m_elevatorSim;

    private static final class ElevatorSimConstants {
        public static final double K_ELEVATOR_GEARING = 10.0;
        public static final double K_CARRIAGE_MASS = 4.0; // kg
        public static final double K_MIN_ELEVATOR_HEIGHT = 0;
        public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(60);
        public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getNEO(1);
        public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(1.0);
    }

    public ElevatorSubsystem() {
        m_liftMotor = new SparkMax(Constants.CAN_LIFT_MOTOR, MotorType.kBrushless);
        m_liftEncoder = m_liftMotor.getEncoder();

        m_lowerLimitSwitch = new DigitalInput(Constants.DIO_LIFT_LOWER_LIMIT);
        m_upperLimitSwitch = new DigitalInput(Constants.DIO_LIFT_UPPER_LIMIT);

        // Draws the elevator in the simulator GUI, under SmartDashboard -> Elevator Mechanism
        m_mechanism = new Mechanism2d(1, ElevatorSimConstants.K_MAX_ELEVATOR_HEIGHT + .1);
        m_elevatorLigament = m_mechanism.getRoot("Elevator Root", .5, 0).append(new MechanismLigament2d("Elevator", 0, 90));
        SmartDashboard.putData("Elevator Mechanism", m_mechanism);

        m_loggingUtil = new LoggingUtil("Elevator");
        m_loggingUtil.addDouble("Height", this::getHeight);
        m_loggingUtil.addDouble("Motor Speed", m_liftMotor::getAppliedOutput);
        m_loggingUtil.addBoolean("Lower Limit Switch", this::isAtLowerLimit);
        m_loggingUtil.addBoolean("Upper Limit Switch", this::isAtUpperLimit);

        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                    ElevatorSimConstants.K_ELEVATOR_GEARBOX,
                    ElevatorSimConstants.K_ELEVATOR_GEARING,
                    ElevatorSimConstants.K_CARRIAGE_MASS,
                    ElevatorSimConstants.K_ELEVATOR_DRUM_RADIUS,
                    ElevatorSimConstants.K_MIN_ELEVATOR_HEIGHT,
                    ElevatorSimConstants.K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_elevatorSim = new ElevatorSimWrapper(sim,
                    new RevMotorControllerSimWrapper(m_liftMotor, ElevatorSimConstants.K_ELEVATOR_GEARBOX),
                    RevEncoderSimWrapper.create(m_liftMotor));
            m_elevatorSim.setLowerLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_lowerLimitSwitch)::setValue));
            m_elevatorSim.setUpperLimitSwitch(new BaseDigitalInputWrapper(new DIOSim(m_upperLimitSwitch)::setValue));
        }
    }

    @Override
    public void close() {
        m_liftMotor.close();
        m_lowerLimitSwitch.close();
        m_upperLimitSwitch.close();
    }

    @Override
    public void periodic() {
        m_elevatorLigament.setLength(getHeight());
        m_loggingUtil.updateLogs();
    }

    @Override
    public void simulationPeriodic() {
        m_elevatorSim.update();
    }

    /**
     * Moves the elevator towards a goal height. This should be called every loop until the elevator gets there.
     *
     * @param position The goal height, in meters
     * @return True if the elevator is within ALLOWABLE_POSITION_ERROR of the goal
     */
    public boolean goToPosition(double position) {
        // TODO implement
        return false;
    }

    /**
     * @return True if the bottom limit switch is pressed
     */
    public boolean isAtLowerLimit() {
        // TODO implement
        return false;
    }

    /**
     * @return True if the top limit switch is pressed
     */
    public boolean isAtUpperLimit() {
        // TODO implement
        return false;
    }

    public void stop() {
        // TODO implement
    }

    public void setSpeed(double speed) {
        // TODO implement
    }

    /**
     * @return The height of the elevator, in meters
     */
    public double getHeight() {
        // TODO implement
        return 0;
    }

    public void addElevatorDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Elevator");
        tab.add(new ElevatorToPositionCommand(this, Positions.LOW).withName("To Position Low"));
        tab.add(new ElevatorToPositionCommand(this, Positions.MID).withName("To Position Mid"));
        tab.add(new ElevatorToPositionCommand(this, Positions.HIGH).withName("To Position High"));
    }
}
