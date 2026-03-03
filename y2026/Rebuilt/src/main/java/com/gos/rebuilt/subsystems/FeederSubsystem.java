package com.gos.rebuilt.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

public class FeederSubsystem extends SubsystemBase {

    private final SparkFlex m_feederMotor;
    private final RelativeEncoder m_feederEncoder;
    private final GosDoubleProperty m_feederSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FeederSpeed", 0.8);
    private final SparkMaxAlerts m_feederMotorAlerts;
    private double m_goal;
    private static final double DEADBAND = 67;
    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;
    private final LoggingUtil m_networkTableEntries;
    private ISimWrapper m_feederSimulator;
    private final GosDoubleProperty m_tuneRpm = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FEEDAtuneRPM", 1);




    public FeederSubsystem() {

        m_networkTableEntries = new LoggingUtil("Feeder Subsystem");

        m_feederMotor = new SparkFlex(Constants.FEEDER_MOTOR, MotorType.kBrushless);

        m_feederMotorAlerts = new SparkMaxAlerts(m_feederMotor, "feedrMotor");

        SparkMaxConfig feederConfig = new SparkMaxConfig();
        feederConfig.idleMode(IdleMode.kCoast);
        feederConfig.smartCurrentLimit(60);
        feederConfig.inverted(true);

        feederConfig.encoder.positionConversionFactor(12);
        feederConfig.encoder.velocityConversionFactor(12);


        m_feederMotor.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_pidController = m_feederMotor.getClosedLoopController();
        m_feederEncoder = m_feederMotor.getEncoder();

        m_pidProperties = new RevPidPropertyBuilder("Feeder", false, m_feederMotor, feederConfig, ClosedLoopSlot.kSlot0)
            .addFF(0)
            .addP(0)
            .build();


        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            LinearSystem<N1, N1, N1> plant =
                LinearSystemId.createFlywheelSystem(gearbox, 0.01, 1.0);
            FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
            this.m_feederSimulator = new FlywheelSimWrapper(
                shooterFlywheelSim,
                new RevMotorControllerSimWrapper(this.m_feederMotor, gearbox),
                RevEncoderSimWrapper.create(this.m_feederMotor));
        }


        m_networkTableEntries.addDouble("Current", m_feederMotor::getOutputCurrent);

        m_networkTableEntries.addDouble("Feeder rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

    }

    public double getGoal() {
        return m_goal;
    }

    public void feed() {
        m_feederMotor.set(m_feederSpeed.getValue());
    }

    public void reverse() {
        m_feederMotor.set(-m_feederSpeed.getValue());
    }

    public void stop() {
        m_feederMotor.stopMotor();


    }

    public boolean isAtGoalRPM() {
        return Math.abs(m_goal - getRPM()) < DEADBAND;
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal, ControlType.kVelocity);

    }

    public double getRPM() {
        return m_feederEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_feederMotorAlerts.checkAlerts();
        m_pidProperties.updateIfChanged();
        m_networkTableEntries.updateLogs();
    }

    public void addFeederDebugCommands(boolean areweatacompetitionyesornoboolean) {

        ShuffleboardTab tab = Shuffleboard.getTab("Feeder");
        if (!areweatacompetitionyesornoboolean) {
            tab.add(createFeederSpin(1000));
            tab.add(createFeederSpin(500));

            tab.add(createFeederCommand());
            tab.add(createFeederReverseCommand());
        }
        tab.add(createTuneRPM());
    }

    @Override
    public void simulationPeriodic() {
        m_feederSimulator.update();
    }

    public Command createFeederCommand() {
        return runEnd(this::feed, this::stop).withName("Feed🍕😎");
    }

    public Command createFeederReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the Feeder 😱");
    }


    public Command createFeederSpin(double rpm) {
        return runEnd(() -> setRPM(rpm), this::stop).withName("Feed " + rpm + " burgers!");
    }

    public Command createTuneRPM() {
        return runEnd(() -> setRPM(m_tuneRpm.getValue()), this::stop).withName("FEEDA spins to tuneRPM!!");
    }


}
