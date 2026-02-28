package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevProfiledElevatorController;
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
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ElevatorSimWrapper;

public class ClimberSubsystem extends SubsystemBase {
    public static final double K_ELEVATOR_GEARING = 12.0;
    public static final double K_CARRIAGE_MASS = Units.lbsToKilograms(9);
    public static final double K_MIN_ELEVATOR_HEIGHT = Units.inchesToMeters(-4);
    public static final double K_MAX_ELEVATOR_HEIGHT = Units.inchesToMeters(120);
    public static final DCMotor K_ELEVATOR_GEARBOX = DCMotor.getNeoVortex(2);
    public static final double K_ELEVATOR_DRUM_RADIUS = Units.inchesToMeters(1.0);

    private final SparkFlex m_climberLeftMotor;
    private final SparkFlex m_climberRightMotor;
    private final RelativeEncoder m_climberLeftEncoder;
    private final RelativeEncoder m_climberRightEncoder;
    private final SparkMaxAlerts m_climberLeftMotorAlerts;
    private final SparkMaxAlerts m_climberRightMotorAlerts;
    private final RevProfiledElevatorController m_climberLeftPidController;
    private final RevProfiledElevatorController m_climberRightPidController;
    private ElevatorSimWrapper m_leftSimulator;
    private ElevatorSimWrapper m_rightSimulator;


    private double m_goalHeight;

    private final GosDoubleProperty m_climbingSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "climbingSpeed", 1);
    private final LoggingUtil m_networkTableEntries;

    public ClimberSubsystem() {
        m_climberLeftMotor = new SparkFlex(Constants.CLIMBER_LEFT_MOTOR, MotorType.kBrushless);
        m_climberLeftEncoder = m_climberLeftMotor.getEncoder();
        m_climberRightMotor = new SparkFlex(Constants.CLIMBER_RIGHT_MOTOR, MotorType.kBrushless);
        m_climberRightEncoder = m_climberRightMotor.getEncoder();
        m_climberLeftMotorAlerts = new SparkMaxAlerts(m_climberLeftMotor, "Climber Left Motor");
        m_climberRightMotorAlerts = new SparkMaxAlerts(m_climberRightMotor, "Climber Right Motor");


        SparkMaxConfig climberLeftMotorConfig = new SparkMaxConfig();
        climberLeftMotorConfig.idleMode(IdleMode.kBrake);
        climberLeftMotorConfig.smartCurrentLimit(60);
        climberLeftMotorConfig.inverted(true);

        SparkMaxConfig climberRightMotorConfig = new SparkMaxConfig();
        climberRightMotorConfig.idleMode(IdleMode.kBrake);
        climberRightMotorConfig.smartCurrentLimit(60);
        climberLeftMotorConfig.inverted(false);

        m_climberLeftPidController = new RevProfiledElevatorController.Builder("Climber", Constants.DEFAULT_CONSTANT_PROPERTIES, m_climberLeftMotor, climberLeftMotorConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(2)
            .addMaxAcceleration(2.25)
            // Elevator FF
            .addKs(0)
            .addKv(5)
            .addKg(0.45)
            .addKa(0)
            // REV Position controller
            .addKp(4)
            .build();

        m_climberRightPidController = new RevProfiledElevatorController.Builder("Climber", Constants.DEFAULT_CONSTANT_PROPERTIES, m_climberRightMotor, climberRightMotorConfig, ClosedLoopSlot.kSlot0)
            // Speed Limits
            .addMaxVelocity(2)
            .addMaxAcceleration(2.25)
            // Elevator FF
            .addKs(0)
            .addKv(5)
            .addKg(0.45)
            .addKa(0)
            // REV Position controller
            .addKp(4)
            .build();

        m_climberLeftMotor.configure(climberLeftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_climberRightMotor.configure(climberRightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING * 0.5, // Cut in half because of how a two stage elevator works (???)
                K_CARRIAGE_MASS,
                K_ELEVATOR_DRUM_RADIUS,
                K_MIN_ELEVATOR_HEIGHT,
                K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_leftSimulator = new ElevatorSimWrapper(sim,
                new RevMotorControllerSimWrapper(m_climberLeftMotor, K_ELEVATOR_GEARBOX),
                RevEncoderSimWrapper.create(m_climberLeftMotor));


        }

        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING * 0.5, // Cut in half because of how a two stage elevator works (???)
                K_CARRIAGE_MASS,
                K_ELEVATOR_DRUM_RADIUS,
                K_MIN_ELEVATOR_HEIGHT,
                K_MAX_ELEVATOR_HEIGHT, true, 0);

            m_rightSimulator = new ElevatorSimWrapper(sim,
                new RevMotorControllerSimWrapper(m_climberRightMotor, K_ELEVATOR_GEARBOX),
                RevEncoderSimWrapper.create(m_climberRightMotor));


        }

        m_networkTableEntries = new LoggingUtil("ClimberSubsystem");
        m_networkTableEntries.addDouble("Climber Left RPM", this::getLeftRPM);
        m_networkTableEntries.addDouble("Climber Right RPM", this::getRightRPM);
        m_networkTableEntries.addDouble("Climber Left Height", this::getLeftHeight);
        m_networkTableEntries.addDouble("Climber Right Height", this::getRightHeight);
        m_networkTableEntries.addDouble("Climber Goal Height", this::getGoalHeight);
        m_networkTableEntries.addDouble("Setpoint Left Position", m_climberLeftPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Right Position", m_climberRightPidController::getPositionSetpoint);
        m_networkTableEntries.addDouble("Setpoint Left Velocity", m_climberLeftPidController::getVelocitySetpoint);
        m_networkTableEntries.addDouble("Setpoint Right Velocity", m_climberRightPidController::getVelocitySetpoint);


    }

    public double getGoalHeight() {
        return m_goalHeight;
    }

    public double getLeftHeight() {
        return m_climberLeftEncoder.getPosition();
    }

    public double getRightHeight() {
        return m_climberRightEncoder.getPosition();
    }

    public double getLeftVelocity() {
        return m_climberLeftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return m_climberRightEncoder.getVelocity();
    }


    public void climbUp() {
        m_climberLeftMotor.set(m_climbingSpeed.getValue());
        m_climberRightMotor.set(m_climbingSpeed.getValue());
    }

    public void climbDown() {
        m_climberLeftMotor.set(-m_climbingSpeed.getValue());
        m_climberRightMotor.set(-m_climbingSpeed.getValue());
    }

    public void leftClimbUp() {
        m_climberLeftMotor.set(m_climbingSpeed.getValue());
    }

    public void leftClimbDown() {
        m_climberLeftMotor.set(-m_climbingSpeed.getValue());
    }

    public void rightClimbUp() {
        m_climberRightMotor.set(m_climbingSpeed.getValue());
    }

    public void rightClimbDown() {
        m_climberRightMotor.set(-m_climbingSpeed.getValue());
    }

    public double getLeftRPM() {
        return m_climberLeftEncoder.getVelocity();
    }

    public double getRightRPM() {
        return m_climberRightEncoder.getVelocity();
    }

    public void resetLeftPidController() {
        m_climberLeftPidController.resetPidController(getLeftHeight(), getLeftVelocity());
    }

    public void resetRightidController() {
        m_climberRightPidController.resetPidController(getRightHeight(), getRightVelocity());
    }

    public void goToHeight(double goalHeight) {
        if (Math.abs(m_goalHeight - goalHeight) > Units.inchesToMeters(5)) {
            resetLeftPidController();
            resetRightidController();
        }
        m_goalHeight = goalHeight;
        m_climberLeftPidController.goToHeight(goalHeight, getLeftHeight(), getLeftVelocity());
        m_climberRightPidController.goToHeight(goalHeight, getRightHeight(), getRightVelocity());

    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_climberLeftMotorAlerts.checkAlerts();
        m_climberRightMotorAlerts.checkAlerts();
        m_climberLeftPidController.updateIfChanged();
        m_climberRightPidController.updateIfChanged();
    }

    @Override
    public void simulationPeriodic() {
        m_leftSimulator.update();
        m_rightSimulator.update();
    }

    public void stop() {
        m_climberLeftMotor.stopMotor();
        m_climberRightMotor.stopMotor();
    }

    public void leftStop() {
        m_climberLeftMotor.stopMotor();
    }

    public void rightStop() {
        m_climberRightMotor.stopMotor();
    }


    public Command createClimbingUpCommand() {
        return this.runEnd(this::climbUp, this::stop).withName("Climb Up");
    }

    public Command createClimbingDownCommand() {
        return this.runEnd(this::climbDown, this::stop).withName("Climb Down");
    }

    public Command createLeftClimbingUpCommand() {
        return this.runEnd(this::leftClimbUp, this::leftStop).withName("Left Climb Up");
    }

    public Command createLeftClimbingDownCommand() {
        return this.runEnd(this::leftClimbDown, this::leftStop).withName("Left Climb Down");
    }

    public Command createRightClimbingUpCommand() {
        return this.runEnd(this::rightClimbUp, this::rightStop).withName("Right Climb Up");
    }

    public Command createRightClimbingDownCommand() {
        return this.runEnd(this::rightClimbDown, this::rightStop).withName("Right Climb Down");
    }

    public Command createClimbToHeightCommand(double height) {
        return runEnd(() -> goToHeight(height), this::stop)
            .withName("Climber go to height " + height);
    }

    public void addClimberDebugCommands(boolean areweatacompeititionakashouldwehaveallthemethodsoronlysomeofthem) {
        ShuffleboardTab tab = Shuffleboard.getTab("Climber");
         if (!areweatacompeititionakashouldwehaveallthemethodsoronlysomeofthem) {
             tab.add(createClimbingUpCommand());
             tab.add(createClimbingDownCommand());
             tab.add(createClimbToHeightCommand(0));
             tab.add(createClimbToHeightCommand(Units.inchesToMeters(10)));
             tab.add(createClimbToHeightCommand(Units.inchesToMeters(20)));
         }

        tab.add(createLeftClimbingUpCommand());
        tab.add(createLeftClimbingDownCommand());
        tab.add(createRightClimbingUpCommand());
        tab.add(createRightClimbingDownCommand());
    }
}
