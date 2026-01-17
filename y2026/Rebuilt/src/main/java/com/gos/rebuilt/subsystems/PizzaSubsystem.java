package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
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

public class PizzaSubsystem extends SubsystemBase {

    private final SparkFlex m_pizzaMotor;
    private final GosDoubleProperty m_pizzaSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "pizzaSpeed", 1);
    private final RelativeEncoder m_pizzaEncoder;

    private ISimWrapper m_pizzaSimulator;

    public PizzaSubsystem() {

        m_pizzaMotor = new SparkFlex(Constants.PIZZA_MOTOR, MotorType.kBrushless);
        m_pizzaEncoder = m_pizzaMotor.getEncoder();
        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            LinearSystem<N1, N1, N1> plant =
                LinearSystemId.createFlywheelSystem(gearbox, 0.01, 1.0);
            FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
            this.m_pizzaSimulator = new FlywheelSimWrapper(
                shooterFlywheelSim,
                new RevMotorControllerSimWrapper(this.m_pizzaMotor, gearbox),
                RevEncoderSimWrapper.create(this.m_pizzaMotor));
        }
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

    public Command createPizzaFeedCommand() {
        return runEnd(this::feed, this::stop).withName("Feed the hungry 🍕😎");
    }

    public Command createPizzaReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the pizza 😱");
    }

    @Override
    public void simulationPeriodic() {
        m_pizzaSimulator.update();
    }

}

