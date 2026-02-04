package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final SparkFlex m_climberLeftMotor;
    private final SparkFlex m_climberRightMotor;
    private final RelativeEncoder m_climberLeftEncoder;
    private final RelativeEncoder m_climberRightEncoder;

    private final GosDoubleProperty m_climbingSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "climbingSpeed", 1);
    private final LoggingUtil m_networkTableEntries;

    public ClimberSubsystem() {
        m_climberLeftMotor = new SparkFlex(Constants.CLIMBER_LEFT_MOTOR, MotorType.kBrushless);
        m_climberLeftEncoder = m_climberLeftMotor.getEncoder();
        m_climberRightMotor = new SparkFlex(Constants.CLIMBER_RIGHT_MOTOR, MotorType.kBrushless);
        m_climberRightEncoder = m_climberRightMotor.getEncoder();
        m_networkTableEntries = new LoggingUtil("ClimberSubsystem");
        m_networkTableEntries.addDouble("Climber Left RPM", this::getLeftRPM);
        m_networkTableEntries.addDouble("Climber Right RPM", this::getRightRPM);

        SparkMaxConfig climberLeftMotorConfig = new SparkMaxConfig();
        climberLeftMotorConfig.idleMode(IdleMode.kBrake);
        climberLeftMotorConfig.smartCurrentLimit(60);
        climberLeftMotorConfig.inverted(true);

        SparkMaxConfig climberRightMotorConfig = new SparkMaxConfig();
        climberRightMotorConfig.idleMode(IdleMode.kBrake);
        climberRightMotorConfig.smartCurrentLimit(60);


    }


    public void climbUp() {
        m_climberLeftMotor.set(m_climbingSpeed.getValue());
        m_climberRightMotor.set(m_climbingSpeed.getValue());
    }

    public void climbDown() {
        m_climberLeftMotor.set(-m_climbingSpeed.getValue());
        m_climberRightMotor.set(-m_climbingSpeed.getValue());
    }

    public void leftClimbUp() {
        m_climberLeftMotor.set(m_climbingSpeed.getValue());
    }

    public void leftClimbDown() {
        m_climberLeftMotor.set(-m_climbingSpeed.getValue());
    }

    public void rightClimbUp() {
        m_climberRightMotor.set(m_climbingSpeed.getValue());
    }

    public void rightClimbDown() {
        m_climberRightMotor.set(-m_climbingSpeed.getValue());
    }

    public double getLeftRPM() {
        return m_climberLeftEncoder.getVelocity();
    }

    public double getRightRPM() {
        return m_climberRightEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
    }

    public void stop() {
        m_climberLeftMotor.stopMotor();
        m_climberRightMotor.stopMotor();
    }

    public void leftStop() {
        m_climberLeftMotor.stopMotor();
    }

    public void rightStop() {
        m_climberRightMotor.stopMotor();
    }


    public Command createClimbingUpCommand() {
        return this.runEnd(this::climbUp, this::stop).withName("Climb Up");
    }

    public Command createClimbingDownCommand() {
        return this.runEnd(this::climbDown, this::stop).withName("Climb Down");
    }

    public Command createLeftClimbingUpCommand() {
        return this.runEnd(this::leftClimbUp, this::leftStop).withName("Left Climb Up");
    }

    public Command createLeftClimbingDownCommand() {
        return this.runEnd(this::leftClimbDown, this::leftStop).withName("Left Climb Down");
    }

    public Command createRightClimbingUpCommand() {
        return this.runEnd(this::rightClimbUp, this::rightStop).withName("Right Climb Up");
    }

    public Command createRightClimbingDownCommand() {
        return this.runEnd(this::rightClimbDown, this::rightStop).withName("Right Climb Down");
    }

    public void addClimberDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Climber");
        tab.add(createClimbingUpCommand());
        tab.add(createClimbingDownCommand());
        tab.add(createLeftClimbingUpCommand());
        tab.add(createLeftClimbingDownCommand());
        tab.add(createRightClimbingUpCommand());
        tab.add(createRightClimbingDownCommand());
    }
}
