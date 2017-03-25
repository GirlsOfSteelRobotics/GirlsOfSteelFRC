package org.usfirst.frc.team3504.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.SampleRobot;

/* Joseph Jackson
 * Girls of Steel, FRC Team #3504
 * 20-Mar-2017
 *
 * Attempt to reproduce a problem where talon.getError() returns zero the first time it is called,
 * but only when the robot was just turned on.
 */
public class Robot extends SampleRobot {
	private CANTalon talon;
	double rotations;

	public Robot() {
		talon = new CANTalon(1);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev(256);
		talon.reverseSensor(false);

		rotations = 5;
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void autonomous() {
		talon.changeControlMode(TalonControlMode.Position);

		talon.setP(0.17);
		talon.setI(0.0);
		talon.setD(0.02);
		talon.setF(0.0);

		// Initialize encoder position to zero
		talon.setPosition(0.0);

		// Request some number of rotations
		talon.set(rotations);

		// Immediately check the closed loop error value and some other related parameters
		System.out.println("Talon Goal:              " + rotations);
		System.out.println("Talon Position:          " + talon.getPosition());
		System.out.println("Talon Closed Loop Error: " + talon.getClosedLoopError());
		System.out.println("Talon Error:             " + talon.getError());
	}

	@Override
	public void operatorControl() {
	}

	@Override
	public void test() {
	}
}
