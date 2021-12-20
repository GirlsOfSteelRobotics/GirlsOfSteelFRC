package com.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;

@SuppressWarnings("PMD.GodClass")
public class CANTalon extends WPI_TalonSRX implements Sendable, SpeedController {

    private TalonControlMode m_activeControlMode = TalonControlMode.PercentVbus;


    public enum TalonControlMode {
        Follower,
        Position,
        Speed,
        PercentVbus,
        MotionProfile,
        kPercentVoltage,
        kVoltage,
    }

    private final int m_deviceNumber; // NOPMD
    private double m_encoderCodesPerRev = 1;

    public CANTalon(int deviceNumber) {
        super(deviceNumber);
        m_deviceNumber = deviceNumber;
    }

    public void changeControlMode(TalonControlMode controlMode) {
        m_activeControlMode = controlMode;
    }

    public double getSetpoint() {
        return getClosedLoopTarget();
    }

    public double getSpeed() {
        return getSelectedSensorVelocity();
    }

    public void setFeedbackDevice(FeedbackDevice feedbackDevice) {
        configSelectedFeedbackSensor(feedbackDevice);
    }

    @Override
    public void set(double val) {

        double actualVal = val;
        ControlMode controlMode;

        switch (m_activeControlMode) {
        case Follower:
            controlMode = ControlMode.Follower;
            break;
        case Position:
            controlMode = ControlMode.Position;
            actualVal = val * m_encoderCodesPerRev;
            break;
        case PercentVbus:
        case kPercentVoltage:
            controlMode = ControlMode.PercentOutput;
            break;
        case kVoltage:
            controlMode = ControlMode.PercentOutput;
            actualVal = val / RobotController.getBatteryVoltage();
            break;
        case Speed:
            controlMode = ControlMode.Velocity;
            break;
        case MotionProfile:
            controlMode = ControlMode.MotionProfile;
            break;
        default:
            throw new IllegalArgumentException();
        }

        set(controlMode, actualVal);
    }

    public int getPosition() {
        return (int) getSelectedSensorPosition();
    }

    public void setPosition(int position) {
        setSelectedSensorPosition(position);
    }

    public int getEncPosition() {
        return getPosition();
    }

    public int getEncVelocity() {
        throw new UnsupportedOperationException("");
    }

    public double getError() {
        return getClosedLoopError();
    }

    public void setVoltageRampRate(double voltsPerSecond) {
        double rampRate = 12.0 / voltsPerSecond;
        configOpenloopRamp(rampRate);
    }

    public void reverseSensor(boolean reverse) {
        setSensorPhase(!reverse);
    }



    public void configEncoderCodesPerRev(int codesPerWheelRev) {
        m_encoderCodesPerRev = codesPerWheelRev;
    }

    public void configFwdLimitSwitchNormallyOpen(boolean b) {
        throw new UnsupportedOperationException();
    }

    public void configRevLimitSwitchNormallyOpen(boolean b) {
        throw new UnsupportedOperationException();
    }

    public void enableLimitSwitch(boolean fwd, boolean rev) {
        if (fwd && rev) {
            overrideLimitSwitchesEnable(true);
        } else if (!fwd && !rev) {
            overrideLimitSwitchesEnable(false);
        } else {
            throw new IllegalArgumentException("Fwd and reverse must be the same");
        }
    }
}
