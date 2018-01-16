package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.StayClimbed;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Climber extends Subsystem {
	public WPI_TalonSRX climbMotorA;
	public WPI_TalonSRX climbMotorB;

	public Climber() {
		climbMotorA = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_A);
		climbMotorB = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR_B);

		climbMotorA.setNeutralMode(NeutralMode.Brake);
		climbMotorB.setNeutralMode(NeutralMode.Brake);

		climbMotorA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		climbMotorA.setSensorPhase(true);

		climbMotorA.config_kF(0, 0, 0);
		climbMotorA.config_kP(0, 0.5, 0);
		climbMotorA.config_kI(0, 0, 0);
		climbMotorA.config_kD(0, 0, 0);

//		LiveWindow.addActuator("Climber", "climbMotorA", climbMotorA);
//		LiveWindow.addActuator("Climber", "climbMotorB", climbMotorB);
	}

	public void climb(double speed) {
		climbMotorA.set(ControlMode.PercentOutput, speed);
		climbMotorB.set(ControlMode.PercentOutput, speed);
	}

	public void stopClimb() {
		climbMotorA.set(ControlMode.PercentOutput, 0.0);
		climbMotorB.set(ControlMode.PercentOutput, 0.0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new StayClimbed());
	}
}
