package com.gos.kitbot2026.subsystems;

import com.gos.kitbot2026.Constants;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkFlex m_shooterMotor;
    private final RelativeEncoder m_motorEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final GosDoubleProperty m_shooterSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "shooterSpeed", 1);

    public ShooterSubsystem() {
        m_shooterMotor = new SparkFlex(Constants.SHOOTER_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_shooterMotor.getEncoder();
        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");


        m_networkTableEntries.addDouble("Shooter rpm", this::getRPM);
    }

    public void spinMotorForward() {
        m_shooterMotor.set(m_shooterSpeed.getValue());
    }


    public void spinMotorForward(double pow) {
        m_shooterMotor.set(pow);
    }

    public double getRPM() {
        return m_motorEncoder.getVelocity();
    }


    public void spinMotorBackward() {
        m_shooterMotor.set(-m_shooterSpeed.getValue());
    }

    public void stop() {
        m_shooterMotor.stopMotor();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
    }

    public void addShooterDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
        tab.add(createShooterSpinMotorForwardCommand());
        tab.add(createShooterSpinMotorBackwardCommand());

    }

    public Command createShooterSpinMotorForwardCommand() {
        return runEnd(this::spinMotorForward, this::stop).withName("Shooter Forward! :)");
    }

    public Command createShooterSpinMotorBackwardCommand() {
        return runEnd(this::spinMotorBackward, this::stop).withName("Shooter Backward! :(");
    }


}

