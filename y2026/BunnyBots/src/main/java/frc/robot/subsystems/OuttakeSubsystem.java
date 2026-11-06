package frc.robot.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;


public class OuttakeSubsystem {
    private final SparkFlex m_motor;
    private final GosDoubleProperty m_scoreOvenSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Outtake: Score Oven Speed", 0.67);
    private final GosDoubleProperty m_scorePantrySpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Outtake: Score Pantry Speed", 0.45);
    private final GosDoubleProperty m_reverseSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Outtake: Reverse Speed", -1);

    public OuttakeSubsystem() {
        m_motor = new SparkFlex(Constants.OUTTAKE_MOTOR, MotorType.kBrushless);
        SparkMaxConfig followMotorConfig = new SparkMaxConfig();
        followMotorConfig.idleMode(IdleMode.kCoast);
        followMotorConfig.smartCurrentLimit(60);
        m_motor.configure(followMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void scoreOven() {
        m_motor.set(m_scoreOvenSpeed.getValue());
    }

    public void scorePantry() {
        m_motor.set(m_scorePantrySpeed.getValue());
    }

    public void reverse() {
        m_motor.set(m_reverseSpeed.getValue());
    }

    public void stop() {
        m_motor.stopMotor();
    }

    public void addOuttakeDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Outtake");
        tab.add(createOuttakeScoreOvenCommand());
        tab.add(createOuttakeScorePantryCommand());
        tab.add(createOuttakeReverseCommand());
    }

    public Command createOuttakeScoreOvenCommand() {
        return runEnd(this::scoreOven, this::stop).withName("Score oven");
    }

    public Command createOuttakeScorePantryCommand() {
        return runEnd(this::scorePantry, this::stop).withName("Score pantry");
    }

    public Command createOuttakeReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse");
    }
}
