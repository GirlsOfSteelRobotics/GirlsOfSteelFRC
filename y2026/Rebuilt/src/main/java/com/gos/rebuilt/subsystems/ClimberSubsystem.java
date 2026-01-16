package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final SparkFlex m_climberMotor;
    private final RelativeEncoder m_climberEncoder;
    private final GosDoubleProperty m_climbingSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "climbingSpeed", 1);
    private final LoggingUtil m_networkTableEntries;

    public ClimberSubsystem() {
        m_climberMotor = new SparkFlex(Constants.CLIMBER_MOTOR, MotorType.kBrushless);
        m_climberEncoder = m_climberMotor.getEncoder();
        m_networkTableEntries = new LoggingUtil("ClimberSubsystem");
        m_networkTableEntries.addDouble("Climber rpm", this::getRPM);
    }

    public void climbUp() {
        m_climberMotor.set(m_climbingSpeed.getValue());
    }

    public void climbDown() {
        m_climberMotor.set(-m_climbingSpeed.getValue());
    }

    public double getRPM() {
        return m_climberEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
    }

    public void stop() {
        m_climberMotor.stopMotor();
    }


    public Command createClimbingUpCommand() {
        return this.runEnd(this::climbUp, this::stop).withName("Climb Up");
    }

    public Command createClimbingDownCommand() {
        return this.runEnd(this::climbDown, this::stop).withName("Climb Down");
    }

    public void addClimberDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Climber");
        tab.add(createClimbingUpCommand());
        tab.add(createClimbingDownCommand());
    }
}
