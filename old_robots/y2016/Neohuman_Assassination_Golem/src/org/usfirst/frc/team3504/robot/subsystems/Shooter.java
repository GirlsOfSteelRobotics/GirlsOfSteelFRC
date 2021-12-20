package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final CANTalon m_shooterMotor1;
    private final CANTalon m_shooterMotor2;
    private final DoubleSolenoid m_shooterPiston1;
    private final DoubleSolenoid m_shooterPiston2;


    public Shooter() {
        m_shooterMotor1 = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
        m_shooterMotor2 = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
        addChild("Talon", m_shooterMotor1);
        addChild("Talon", m_shooterMotor2);
        m_shooterPiston1 = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_LEFT_A, RobotMap.SHOOTER_PISTON_LEFT_B);
        m_shooterPiston2 = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_RIGHT_A, RobotMap.SHOOTER_PISTON_RIGHT_B);
    }

    public void pistonsOut(){
        m_shooterPiston1.set(DoubleSolenoid.Value.kForward);
        System.out.println("Left Piston out");
        m_shooterPiston2.set(DoubleSolenoid.Value.kForward);
        System.out.println("Right Piston out");
    }

    public void pistonsIn(){
        m_shooterPiston1.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Left Piston in");
        m_shooterPiston2.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Right Piston in");
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
