package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.steam_works.RobotMap;

/**
 *
 */

public class Climber extends SubsystemBase {
    private final WPI_TalonSRX m_climbMotorA;
    private final WPI_TalonSRX m_climbMotorB;

    public Climber() {
        m_climbMotorA = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_A);
        m_climbMotorB = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_B);

        m_climbMotorB.follow(m_climbMotorA);

        m_climbMotorA.setNeutralMode(NeutralMode.Brake);
        m_climbMotorB.setNeutralMode(NeutralMode.Brake);

        m_climbMotorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        m_climbMotorA.setSensorPhase(false);

        m_climbMotorA.config_kF(0, 0);
        m_climbMotorA.config_kP(0, 0.5);
        m_climbMotorA.config_kI(0, 0);
        m_climbMotorA.config_kD(0, 0);


        addChild("climbMotorA", m_climbMotorA);
        addChild("climbMotorB", m_climbMotorB);
    }

    public void climb(double speed) {
        m_climbMotorA.set(ControlMode.PercentOutput, speed);
    }

    public void goToPosition(double encPosition) {
        m_climbMotorA.set(ControlMode.Position, encPosition);
    }

    public double getPosition() {
        return m_climbMotorA.getSelectedSensorPosition();
    }

    public void stopClimb() {
        m_climbMotorA.set(ControlMode.PercentOutput, 0.0);
        m_climbMotorB.set(ControlMode.PercentOutput, 0.0);
    }
}
