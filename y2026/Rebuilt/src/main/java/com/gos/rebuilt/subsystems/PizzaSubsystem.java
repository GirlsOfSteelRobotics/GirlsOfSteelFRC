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
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;
import org.snobotv2.sim_wrappers.InstantaneousMotorSim;

public class PizzaSubsystem extends SubsystemBase {

    private final SparkMax m_pizzaMotor;
    private final GosDoubleProperty m_pizzaSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "pizzaSpeed", 1);
    private final RelativeEncoder m_pizzaEncoder;
    private final SparkMaxAlerts m_pizzaAlert;
    private final LoggingUtil m_networkTableEntries;
    private double m_goal;
    private static final double DEADBAND = 2;
    private static final GosDoubleProperty MIN_SPIN = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "min_Spin", 100);
    private static final GosDoubleProperty BASE_CURRENTS = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "base_Currents", 100);

    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;


    private ISimWrapper m_pizzaSimulator;

    public PizzaSubsystem() {

        m_networkTableEntries = new LoggingUtil("Pizza Subsystem");

        SparkMaxConfig pizzaConfig = new SparkMaxConfig();
        pizzaConfig.idleMode(IdleMode.kCoast);
        pizzaConfig.smartCurrentLimit(60);
        pizzaConfig.inverted(false);




        m_pizzaMotor = new SparkMax(Constants.PIZZA_MOTOR, MotorType.kBrushless);

        m_pidController = m_pizzaMotor.getClosedLoopController();

        m_pizzaAlert = new SparkMaxAlerts(m_pizzaMotor, "pizzaAlert");
        m_pizzaEncoder = m_pizzaMotor.getEncoder();
        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            this.m_pizzaSimulator = new InstantaneousMotorSim(
                new RevMotorControllerSimWrapper(this.m_pizzaMotor, gearbox),
                RevEncoderSimWrapper.create(this.m_pizzaMotor),
                360);
        }
        m_pidProperties = new RevPidPropertyBuilder("Pizza", false, m_pizzaMotor, pizzaConfig, ClosedLoopSlot.kSlot0)
            .addFF(0)
            .addP(0)
            .build();
        m_pizzaMotor.configure(pizzaConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries.addDouble("Current", m_pizzaMotor::getOutputCurrent);

        m_networkTableEntries.addDouble("Pizza rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

    }


    public double getGoal() {
        return m_goal;
    }

    public double getAngle() {
        return m_pizzaEncoder.getPosition();
    }

    public void feed() {
        m_pizzaMotor.set(m_pizzaSpeed.getValue());
    }

    public void reverse() {
        m_pizzaMotor.set(-m_pizzaSpeed.getValue());
    }

    public boolean checkJam() {
        return (getRPM() < MIN_SPIN.getValue() && m_pizzaMotor.getOutputCurrent() > BASE_CURRENTS.getValue());
    }

    public void stop() {
        m_pizzaMotor.stopMotor();
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal, ControlType.kVelocity);

    }

    public boolean isAtGoalRPM() {
        return Math.abs(m_goal - getRPM()) < DEADBAND;
    }

    public double getRPM() {
        return m_pizzaEncoder.getVelocity();
    }



    public void addPizzaDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Pizza");
        tab.add(createPizzaFeedCommand());
        tab.add(createPizzaReverseCommand());
        tab.add(createPizzaSpin500());
        tab.add(createPizzaSpin1000());
        tab.add(createRunUntilStall());
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_pizzaAlert.checkAlerts();
        m_pidProperties.updateIfChanged();
    }

    public Command createPizzaFeedCommand() {
        return runEnd(this::feed, this::stop).withName("Feed the hungry");
    }

    public Command createPizzaReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the pizza");
    }

    public Command createPizzaSpin1000() {
        return runEnd(() -> setRPM(1000), this::stop).withName("Go pizza go 1000!!");
    }

    public Command createPizzaSpin500() {
        return runEnd(() -> setRPM(500), this::stop).withName("Go pizza go 500!!");
    }

    public Command createRunUntilStall() {
        return runEnd(() -> setRPM(500), this::stop).until(this::checkJam).andThen(createPizzaReverseCommand().withTimeout(.5));

    }

    @Override
    public void simulationPeriodic() {
        m_pizzaSimulator.update();
    }

}

