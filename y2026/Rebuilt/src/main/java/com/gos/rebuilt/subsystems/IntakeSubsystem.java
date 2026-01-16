package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkFlex m_intakeMotor;
    private final GosDoubleProperty m_intakeSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "intakeSpeed", 1);


    public IntakeSubsystem() {
        m_intakeMotor = new SparkFlex(Constants.INTAKE_MOTOR, MotorType.kBrushless);


    }


    public void intake() {
        m_intakeMotor.set(m_intakeSpeed.getValue());
    }

    public void outtake() {
        m_intakeMotor.set(-m_intakeSpeed.getValue());
    }

    public void stop() {
        m_intakeMotor.stopMotor();
    }

    public void addIntakeDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Intake");
        tab.add(createIntakeInCommand());
        tab.add(createIntakeOutCommand());
    }

    public Command createIntakeInCommand() {
        return runEnd(this::intake, this::stop).withName("Intake In! :)");
    }

    public Command createIntakeOutCommand() {
        return runEnd(this::outtake, this::stop).withName("Intake Out! :(");
    }
}

