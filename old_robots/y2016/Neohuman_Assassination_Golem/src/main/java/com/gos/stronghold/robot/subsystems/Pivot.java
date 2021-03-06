package com.gos.stronghold.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Pivot extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final WPI_TalonSRX m_pivotMotor;

    private double m_encOffsetValue;

    public Pivot() {

        m_pivotMotor = new WPI_TalonSRX(RobotMap.PIVOT_MOTOR);
        addChild("Talon", m_pivotMotor);

        m_pivotMotor.overrideSoftLimitsEnable(!RobotMap.USING_LIMIT_SWITCHES);
        m_pivotMotor.setNeutralMode(NeutralMode.Brake);

    }

    public int getPosition() {
        if (getTopLimitSwitch()) {
            return 1;
        } else if (getBottomLimitSwitch()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void tiltUpandDown(double speed) {
        m_pivotMotor.set(ControlMode.PercentOutput, -speed);
    }



    public boolean getTopLimitSwitch() {
        return m_pivotMotor.isRevLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch() {
        return m_pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public double getEncoderRight() {
        return m_pivotMotor.getSelectedSensorPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderRight() - m_encOffsetValue); //TODO: Know where encoder is
    }

    public void resetDistance() {
        m_encOffsetValue = getEncoderRight();
    }
}
