package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import com.gos.steam_works.commands.StayClimbed;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */

public class Climber extends SubsystemBase {
    private final WPI_TalonSRX m_climbMotorA;
    private final WPI_TalonSRX m_climbMotorB;

    public Climber() {
        m_climbMotorA = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_A);
        m_climbMotorB = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_B);

        m_climbMotorA.setNeutralMode(NeutralMode.Brake);
        m_climbMotorB.setNeutralMode(NeutralMode.Brake);

        m_climbMotorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        m_climbMotorA.setSensorPhase(true);

        m_climbMotorA.config_kF(0, 0, 0);
        m_climbMotorA.config_kP(0, 0.5, 0);
        m_climbMotorA.config_kI(0, 0, 0);
        m_climbMotorA.config_kD(0, 0, 0);

        m_climbMotorB.follow(m_climbMotorA);
        // addChild("climbMotorA", climbMotorA);
        // addChild("climbMotorB", climbMotorB);
    }

    public void climb(double speed) {
        m_climbMotorA.set(ControlMode.PercentOutput, speed);
    }

    public void stopClimb() {
        m_climbMotorA.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new StayClimbed(this));
    }

    public double getPosition() {
        return m_climbMotorA.getSelectedSensorPosition();
    }

    public void goToPosition(double position) {
        m_climbMotorA.set(ControlMode.Position, position);
    }
}
