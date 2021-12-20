package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.StayClimbed;

/**
 *
 */

public class Climber extends Subsystem {
    private final CANTalon m_climbMotorA;
    private final CANTalon m_climbMotorB;

    public Climber() {
        m_climbMotorA = new CANTalon(RobotMap.CLIMB_MOTOR_A);
        m_climbMotorB = new CANTalon(RobotMap.CLIMB_MOTOR_B);

        m_climbMotorB.follow(m_climbMotorA);

        m_climbMotorA.setNeutralMode(NeutralMode.Brake);
        m_climbMotorB.setNeutralMode(NeutralMode.Brake);

        m_climbMotorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        m_climbMotorA.reverseSensor(true);

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

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new StayClimbed(this));
    }
}
