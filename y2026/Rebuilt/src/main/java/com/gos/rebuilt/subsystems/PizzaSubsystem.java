package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
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

    private ISimWrapper m_pizzaSimulator;

    public PizzaSubsystem() {


        SparkMaxConfig pizzaConfig = new SparkMaxConfig();
        pizzaConfig.idleMode(IdleMode.kCoast);
        pizzaConfig.smartCurrentLimit(60);
        pizzaConfig.inverted(false);



        m_pizzaMotor = new SparkMax(Constants.PIZZA_MOTOR, MotorType.kBrushless);

        m_pizzaAlert = new SparkMaxAlerts(m_pizzaMotor, "pizzaAlert");
        m_pizzaEncoder = m_pizzaMotor.getEncoder();
        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            this.m_pizzaSimulator = new InstantaneousMotorSim(
                new RevMotorControllerSimWrapper(this.m_pizzaMotor, gearbox),
                RevEncoderSimWrapper.create(this.m_pizzaMotor),
                360);
        }

        m_pizzaMotor.configure(pizzaConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public double getRPM() {
        return m_pizzaEncoder.getVelocity();
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

    public void stop() {
        m_pizzaMotor.stopMotor();
    }



    public void addPizzaDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Pizza");
        tab.add(createPizzaFeedCommand());
        tab.add(createPizzaReverseCommand());
    }

    @Override
    public void periodic() {
        m_pizzaAlert.checkAlerts();
    }

    public Command createPizzaFeedCommand() {
        return runEnd(this::feed, this::stop).withName("Feed the hungry");
    }

    public Command createPizzaReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the pizza");
    }

    @Override
    public void simulationPeriodic() {
        m_pizzaSimulator.update();
    }

}

