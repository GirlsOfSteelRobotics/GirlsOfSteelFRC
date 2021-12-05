package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final CANTalon shooterMotor1;
    private final CANTalon shooterMotor2;
    private final DoubleSolenoid shooterPiston1;
    private final DoubleSolenoid shooterPiston2;


    public Shooter() {
        shooterMotor1 = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
        shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
        shooterMotor2 = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
        shooterMotor2.changeControlMode(TalonControlMode.PercentVbus);
        addChild("Talon", shooterMotor1);
        addChild("Talon", shooterMotor2);
        shooterPiston1 = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_LEFT_A, RobotMap.SHOOTER_PISTON_LEFT_B);
        shooterPiston2 = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_RIGHT_A, RobotMap.SHOOTER_PISTON_RIGHT_B);
    }

    public void pistonsOut(){
        shooterPiston1.set(DoubleSolenoid.Value.kForward);
        System.out.println("Left Piston out");
        shooterPiston2.set(DoubleSolenoid.Value.kForward);
        System.out.println("Right Piston out");
    }

    public void pistonsIn(){
        shooterPiston1.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Left Piston in");
        shooterPiston2.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Right Piston in");
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void spinWheels(double speed) {
        //add a wait
        shooterMotor1.set(speed);
        shooterMotor2.set(-speed);
    }

    public void stop() {
        //add a wait
        shooterMotor1.set(0.0);
        shooterMotor2.set(0.0);
    }
}
