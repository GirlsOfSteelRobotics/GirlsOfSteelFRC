package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PizzaSubsystem extends SubsystemBase {

    private final SparkFlex m_pizzaMotor;
    private final GosDoubleProperty m_pizzaSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "pizzaSpeed", 1);



    public PizzaSubsystem() {

        m_pizzaMotor = new SparkFlex(Constants.PIZZA_MOTOR, MotorType.kBrushless);

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
}

