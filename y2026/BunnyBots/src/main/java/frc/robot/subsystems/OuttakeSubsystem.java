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
    private final GosDoubleProperty m_scoreOvenSpeed = new GosDoubleProperty(false, "Outtake: Score Oven Speed", 0.75);
    private final GosDoubleProperty m_scorePantrySpeed = new GosDoubleProperty(false, "Outtake: Score Pantry Speed", 0.75);
    private final GosDoubleProperty m_reverseSpeed = new GosDoubleProperty(false, "Outtake: Reverse Speed", 0.75);

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
        ShuffleboardTab tab = Shuffleboard.getTab("Intake");

    }

    public Command createOuttakeScoreOvenCommand() {

    }

    public Command createIntakeInCommand() {
        return runEnd(this::intake, this::stop).withName("Intake in!! <3");
    }

    public Command createIntakeOutCommand() {
        return runEnd(this::outtake, this::stop).withName("Intake out!! <3");
    }
}
