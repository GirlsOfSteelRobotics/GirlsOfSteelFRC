package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkFlex m_intakeMotor;
    private final GosDoubleProperty m_intakeSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "intakeSpeed", 1);

    private final SparkMaxAlerts m_intakeMotorAlert;


    public IntakeSubsystem() {
        m_intakeMotor = new SparkFlex(Constants.INTAKE_MOTOR, MotorType.kBrushless);
        m_intakeMotorAlert = new SparkMaxAlerts(m_intakeMotor, "Intake Motor");

        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.idleMode(IdleMode.kCoast);
        intakeConfig.smartCurrentLimit(60);
        intakeConfig.inverted(false);


    }
    @Override
    public void periodic(){
        m_intakeMotorAlert.checkAlerts();
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

