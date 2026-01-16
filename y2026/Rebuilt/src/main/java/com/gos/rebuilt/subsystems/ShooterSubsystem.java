package com.gos.rebuilt.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
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
    private final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "shooterSpeed", 1);

    public ShooterSubsystem() {
        m_shooterMotor = new SparkFlex(Constants.SHOOTER_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_shooterMotor.getEncoder();
    }

    public void spinMotorForward() {
        m_shooterMotor.set(SHOOTER_SPEED.getValue());
    }

    public void spinMotorForward(double pow) {
        m_shooterMotor.set(pow);
    }

    public void spinMotorBackward() {
        m_shooterMotor.set(-SHOOTER_SPEED.getValue());
    }

    public void stop() {
        m_shooterMotor.stopMotor();
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

