package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.StayClimbed;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */

public class Climber extends Subsystem {
	public CANTalon climbMotorA;
	public CANTalon climbMotorB;

	public Climber() {
		climbMotorA = new CANTalon(RobotMap.CLIMB_MOTOR_A);
		climbMotorB = new CANTalon(RobotMap.CLIMB_MOTOR_B);

		climbMotorA.enableBrakeMode(true);
		climbMotorB.enableBrakeMode(true);

		climbMotorA.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		climbMotorA.reverseSensor(true);

		climbMotorA.setF(0);
		climbMotorA.setP(0.5);
		climbMotorA.setI(0);
		climbMotorA.setD(0);

		LiveWindow.addActuator("Climber", "climbMotorA", climbMotorA);
		LiveWindow.addActuator("Climber", "climbMotorB", climbMotorB);
	}

	public void climb(double speed) {
		climbMotorA.set(speed);
		climbMotorB.set(speed);
	}

	public void stopClimb() {
		climbMotorA.set(0.0);
		climbMotorB.set(0.0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new StayClimbed());
	}
}
