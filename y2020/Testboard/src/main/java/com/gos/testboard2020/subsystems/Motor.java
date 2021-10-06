package com.gos.testboard2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.testboard2020.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Motor extends SubsystemBase {
    private static final double FAST_SPEED = 0.5;
    private static final double SLOW_SPEED = 0.25;

    private final WPI_TalonSRX m_mainMotor;

    public Motor() {
        m_mainMotor = new WPI_TalonSRX(Constants.MAIN_MOTOR_TALON);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public WPI_TalonSRX getTalon() {
        return m_mainMotor;
    }

    public void setSpeedMode() {
        m_mainMotor.set(0.25);
    }

    public void motorGoFast() {
        m_mainMotor.set(FAST_SPEED);
    }

    public void motorGoSlow() {
        m_mainMotor.set(SLOW_SPEED);
    }

    public void stop() {
        m_mainMotor.stopMotor();
    }
}
