package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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



    private final SparkFlex m_elevatorMotor;
    private final RelativeEncoder m_encoder;
    private final DigitalInput m_botLimitSwitch;
    private final DigitalInput m_topLimitSwitch;
    private final SparkMaxAlerts m_checkAlerts;
    private final LoggingUtil m_networkTableEntries;

    private final SparkFlex m_followMotor;
    private final SparkMaxAlerts m_elevatorFollowerErrorAlerts;


    private final RevProfiledElevatorController m_elevatorPidController;

    private double m_goalHeight = NO_GOAL_HEIGHT;


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


        double conversionFactor = Units.inchesToMeters(44 - 16.5) / 30.45;
        elevatorConfig.encoder.positionConversionFactor(conversionFactor);
        elevatorConfig.encoder.velocityConversionFactor(conversionFactor / 60);

        SparkMaxConfig followMotorConfig = new SparkMaxConfig();
        followMotorConfig.follow(m_elevatorMotor, true);
        followMotorConfig.idleMode(IdleMode.kBrake);
        followMotorConfig.smartCurrentLimit(60);

        elevatorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);


        m_elevatorPidController = new RevProfiledElevatorController.Builder("Elevator", Constants.DEFAULT_CONSTANT_PROPERTIES, m_elevatorMotor, elevatorConfig, ClosedLoopSlot.kSlot0)
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
        m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_elevatorFollowerErrorAlerts = new SparkMaxAlerts(m_followMotor, "elevator follower");
        m_followMotor.configure(followMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);



        m_checkAlerts = new SparkMaxAlerts(m_elevatorMotor, "Elevator Alert");



        if (RobotBase.isSimulation()) {
            ElevatorSim sim = new ElevatorSim(
                K_ELEVATOR_GEARBOX,
                K_ELEVATOR_GEARING * 0.5, // Cut in half because of how a two stage elevator works (???)
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
        m_goalHeight = NO_GOAL_HEIGHT;
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

    public void goToTunableHeight() {
        goToHeight(ELEVATOR_TUNABLE_HEIGHT.getValue());
    }

    public void goToHeight(double goalHeight) {
        if (Math.abs(m_goalHeight - goalHeight) > Units.inchesToMeters(5)) {
            resetPidController();
        }
        m_goalHeight = goalHeight;
        m_elevatorPidController.goToHeight(goalHeight, getHeight(), getEncoderVel());
    }

    public void resetPidController() {
        m_elevatorPidController.resetPidController(getHeight(), getEncoderVel());
    }

    public boolean isAtGoalHeight() {
        return isAtGoalHeight(m_goalHeight);
    }

    public boolean isAtGoalHeight(double goal) {
        return Math.abs(getHeight() - goal) <= ELEVATOR_ERROR;
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

    public void addElevatorDebugCommands(boolean inComp) {

        ShuffleboardTab debugTab = Shuffleboard.getTab("Elevator");
        if (!inComp) {
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(1)));
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(3)));
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(5)));
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(0)));
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(2)));
            debugTab.add(createMoveElevatorToHeightCommand(Units.feetToMeters(2.5)));
            debugTab.add(createELevatorToTunableHeightCommand().withName("elevator to tunable height"));
            //l3 15.5
        }



        debugTab.add(createResetEncoderCommand().withName("reset encoder omg"));
        debugTab.add(createElevatorToCoastModeCommand().withName("Move elevator to coast"));
    }

    //command factories//

    public Command createMoveElevatorToHeightCommand(double height) {
        return runEnd(() -> goToHeight(height), this::stop)
            .withName("Elevator go to height" + height);
    }

    public Command createELevatorToTunableHeightCommand() {
        return runEnd(this::goToTunableHeight, this::stop).withName("elevator to tunable height ");
    }

    public Command createResetEncoderCommand() {
        return run(() -> m_encoder.setPosition(0)).ignoringDisable(true);
    }

    public Command createElevatorToCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(IdleMode.kCoast),
                () -> setIdleMode(IdleMode.kBrake))
            .ignoringDisable(true).withName("Elevator to Coast");
    }



}

