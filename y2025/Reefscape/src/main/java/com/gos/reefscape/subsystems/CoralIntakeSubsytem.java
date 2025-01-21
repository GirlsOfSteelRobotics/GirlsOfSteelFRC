package com.gos.reefscape.subsystems;

import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralIntakeSubsytem extends SubsystemBase {
    private final SparkFlex m_intakeMotor;
    private final DigitalInput m_intakeSensor;

    public CoralIntakeSubsytem() {
        m_intakeMotor = new SparkFlex(Constants.CORAL_INTAKE_MOTOR_ID, MotorType.kBrushless);
        m_intakeSensor = new DigitalInput(Constants.INTAKE_SENSOR_ID);
    }
    public void intakeIn () {

        m_intakeMotor.set(1);

    }
    public void intakeOut () {
        m_intakeMotor.set(-1);
    }
    public boolean hasCoral () {
        return m_intakeSensor.get();
    }
    public void intakeStop () {
        m_intakeMotor.set (0);
    }


    // Command Factories
    public Command createIntakeInCommand() {
        return runEnd(this::intakeIn,this::intakeStop).withName("Intake IN");
    }
    public Command createIntakeOutCommand() {
        return runEnd(this::intakeOut,this::intakeStop).withName("Intake OUT :(");
    }
    public Command createIntakeUntilCoral() {
        return runEnd(this::intakeIn,this::intakeStop).until(this::hasCoral).withName("intake until coral :)");
    }
}
