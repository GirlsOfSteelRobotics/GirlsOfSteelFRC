package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shooter extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private CANTalon shooterMotor1;
	private CANTalon shooterMotor2;
	private DoubleSolenoid shooterPiston1;
	private DoubleSolenoid shooterPiston2;
	
	
	public Shooter() {
		shooterMotor1 = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
		shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
		shooterMotor2 = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
		shooterMotor2.changeControlMode(TalonControlMode.PercentVbus);
		LiveWindow.addActuator("Shooter", "Talon", shooterMotor1);
		LiveWindow.addActuator("Shooter", "Talon", shooterMotor2);
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
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void spinWheels(double speed) {
    	pistonsOut();
    	//add a wait
    	shooterMotor1.set(speed);
    	shooterMotor2.set(-speed);
    }
    

    public void collectWheels(double speed) {
    	pistonsIn();
    	//add a wait
    	shooterMotor1.set(-speed);
    	shooterMotor2.set(speed);
    }
    
    public void stop() {
    	pistonsIn();
    	//add a wait
    	shooterMotor1.set(0.0);
    	shooterMotor2.set(0.0);
    }
}

