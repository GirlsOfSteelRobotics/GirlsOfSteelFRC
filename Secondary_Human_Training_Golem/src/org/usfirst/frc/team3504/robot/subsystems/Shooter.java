package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private CANTalon lowShooterMotorA;
	private CANTalon lowShooterMotorB;
	private CANTalon highShooterMotorA;
	private CANTalon highShooterMotorB;


	public Shooter(){
		lowShooterMotorA = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
		lowShooterMotorB = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
		highShooterMotorA = new CANTalon(RobotMap.SHOOTER_MOTOR_C);
		highShooterMotorB = new CANTalon(RobotMap.SHOOTER_MOTOR_D);	

	}

	public void shootBall(double lowSpeed, double highSpeed){
		lowShooterMotorA.set(lowSpeed);
		lowShooterMotorB.set(-lowSpeed);
		highShooterMotorA.set(highSpeed);
		highShooterMotorB.set(-highSpeed);
	}

	public void stopShoot(){
		lowShooterMotorA.set(0);
		lowShooterMotorB.set(0);
		highShooterMotorA.set(0);
		highShooterMotorB.set(0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

}
