package com.gos.stronghold.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Shooter extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final WPI_TalonSRX m_shooterMotor1;
    private final WPI_TalonSRX m_shooterMotor2;
    private final DoubleSolenoid m_shooterPiston1;
    private final DoubleSolenoid m_shooterPiston2;


    public Shooter() {
        m_shooterMotor1 = new WPI_TalonSRX(RobotMap.SHOOTER_MOTOR_A);
        m_shooterMotor2 = new WPI_TalonSRX(RobotMap.SHOOTER_MOTOR_B);
        addChild("Talon", m_shooterMotor1);
        addChild("Talon", m_shooterMotor2);
        m_shooterPiston1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHOOTER_PISTON_LEFT_A, RobotMap.SHOOTER_PISTON_LEFT_B);
        m_shooterPiston2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHOOTER_PISTON_RIGHT_A, RobotMap.SHOOTER_PISTON_RIGHT_B);
    }

    public void pistonsOut() {
        m_shooterPiston1.set(DoubleSolenoid.Value.kForward);
        System.out.println("Left Piston out");
        m_shooterPiston2.set(DoubleSolenoid.Value.kForward);
        System.out.println("Right Piston out");
    }

    public void pistonsIn() {
        m_shooterPiston1.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Left Piston in");
        m_shooterPiston2.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Right Piston in");
    }



    public void spinWheels(double speed) {
        //add a wait
        m_shooterMotor1.set(ControlMode.PercentOutput, speed);
        m_shooterMotor2.set(ControlMode.PercentOutput, -speed);
    }

    public void stop() {
        //add a wait
        m_shooterMotor1.set(ControlMode.PercentOutput, 0.0);
        m_shooterMotor2.set(ControlMode.PercentOutput, 0.0);
    }
}
