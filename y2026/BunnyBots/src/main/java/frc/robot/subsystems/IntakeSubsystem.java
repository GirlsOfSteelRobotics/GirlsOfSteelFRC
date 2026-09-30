package frc.robot.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;


public class IntakeSubsystem {
    private final SparkFlex m_motor;
    private final GosDoubleProperty m_motorSpeed = new GosDoubleProperty(false, "motorSpeed", 0.75);

    public IntakeSubsystem() {
        m_motor = new SparkFlex(Constants.INTAKE_MOTOR, MotorType.kBrushless);

    }

    public void intake() {
        m_motor.set(m_motorSpeed.getValue());
    }

    public void outtake() {
        m_motor.set(-m_motorSpeed.getValue());
    }

    public void stop() {
        m_motor.stopMotor();
    }

    public void addIntakeDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Intake");
        tab.add(createIntakeInCommand());
        tab.add(createIntakeOutCommand());
    }

    public Command createIntakeInCommand() {
        return runEnd(this::intake, this::stop).withName("Intake in!! <3");
    }

    public Command createIntakeOutCommand() {
        return runEnd(this::outtake, this::stop).withName("Intake out!! <3");
    }
}
