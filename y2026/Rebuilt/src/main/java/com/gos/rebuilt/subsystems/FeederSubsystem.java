package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {

    private final SparkFlex m_feederMotor;
    private final GosDoubleProperty m_feederSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FeederSpeed", 1);

    public FeederSubsystem() {
        m_feederMotor = new SparkFlex(Constants.FEEDER_MOTOR, MotorType.kBrushless);
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

    public void addFeederDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Feeder");
        tab.add(createFeederCommand());
        tab.add(createFeederReverseCommand());
    }

    public Command createFeederCommand() {
        return runEnd(this::feed, this::stop).withName("Feed🍕😎");
    }

    public Command createFeederReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the Feeder 😱");
    }

}
