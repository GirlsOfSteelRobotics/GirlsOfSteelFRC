package com.gos.stronghold.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Flap extends SubsystemBase {

    private static final double maxEncoder = 360; //max encoder val

    private final WPI_TalonSRX m_flapTalon;
    private double m_encOffsetValue;

    public Flap() {
        m_flapTalon = new WPI_TalonSRX(RobotMap.FLAP_MOTOR);
        addChild("Talon", m_flapTalon);

        m_flapTalon.overrideLimitSwitchesEnable(!RobotMap.USING_LIMIT_SWITCHES);
        m_flapTalon.setNeutralMode(NeutralMode.Brake);
    }



    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setTalon(double speed) {
        m_flapTalon.set(ControlMode.PercentOutput, speed);
    }

    public void stopTalon() {
        m_flapTalon.set(ControlMode.PercentOutput, 0.0);
    }

    public double getMaxEnc() {
        return maxEncoder;
    }

    //assuming that going forward will raise the flap and going backwards will lower the flap
    public boolean getTopLimitSwitch() {
        return m_flapTalon.isFwdLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch() {
        return m_flapTalon.isRevLimitSwitchClosed() == 0;
    }

    public double getFlapEncoder() {
        return m_flapTalon.getSelectedSensorPosition();
    }

    public double getFlapEncoderDistance() {
        return (getFlapEncoder() - m_encOffsetValue); //TODO: know how far encoder is
    }

    public void resetDistance() {
        m_encOffsetValue = getFlapEncoder();
    }
}
