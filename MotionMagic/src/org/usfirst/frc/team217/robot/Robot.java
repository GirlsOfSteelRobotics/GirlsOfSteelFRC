/**
 * Example demonstrating the motion magic control mode.
 * Tested with Logitech F710 USB Gamepad inserted into Driver Station.
 * 
 * Be sure to select the correct feedback sensor using configSelectedFeedbackSensor() below.
 *
 * After deploying/debugging this to your RIO, first use the left Y-stick 
 * to throttle the Talon manually.  This will confirm your hardware setup/sensors
 * and will allow you to take initial measurements.
 * 
 * Be sure to confirm that when the Talon is driving forward (green) the 
 * position sensor is moving in a positive direction.  If this is not the 
 * cause, flip the boolean input to the setSensorPhase() call below.
 *
 * Once you've ensured your feedback device is in-phase with the motor,
 * and followed the walk-through in the Talon SRX Software Reference Manual,
 * use button1 to motion-magic servo to target position specified by the gamepad stick.
 */
package org.usfirst.frc.team217.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends IterativeRobot {
	TalonSRX _talon = new TalonSRX(4);
	TalonSRX _talon2 = new TalonSRX(5);
	TalonSRX _talon3 = new TalonSRX(6);
	Joystick _joy = new Joystick(0);
	StringBuilder _sb = new StringBuilder();
	private final static double GOAL = 118157; // 290 in, backwall to scale white line
//	private final static double GOAL = 44000; // 108 in, switch white line to scale white line
	
	public void robotInit() {
		// Set the second and third Talons to follow the master Talon
		_talon2.follow(_talon);
		_talon3.follow(_talon);
		
		// Enable brake mode to stop the motors quickly instead of coasting
		_talon.setNeutralMode(NeutralMode.Brake);
		_talon2.setNeutralMode(NeutralMode.Brake);
		_talon3.setNeutralMode(NeutralMode.Brake);
		
		/* first choose the sensor */
		_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		_talon.setSensorPhase(true);
		_talon.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		_talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		_talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		_talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		_talon.config_kF(0, 0.25, Constants.kTimeoutMs);
		_talon.config_kP(0, 0.2, Constants.kTimeoutMs);
		_talon.config_kI(0, 0, Constants.kTimeoutMs);
		_talon.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		_talon.configMotionCruiseVelocity(4000, Constants.kTimeoutMs);
		_talon.configMotionAcceleration(4000, Constants.kTimeoutMs);
	}

	@Override
	public void teleopInit() {
		/* zero the sensor */
		_talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);		
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		/* get gamepad axis - forward stick is positive */
		double leftYstick = -1.0 * _joy.getY();
		/* calculate the percent motor output */
		double motorOutput = _talon.getMotorOutputPercent();
		/* prepare line to print */
		_sb.append("\tOut%:");
		_sb.append(motorOutput);
		_sb.append("\tVel:");
		_sb.append(_talon.getSelectedSensorVelocity(Constants.kPIDLoopIdx));

		if (_joy.getRawButton(1)) {
			/* Motion Magic - 4096 ticks/rev * 10 Rotations in either direction */
			//double targetPos = leftYstick * 4096 * 10.0;
			// Hard-code our target position for now
			double targetPos = GOAL;
			_talon.set(ControlMode.MotionMagic, targetPos);

			/* append more signals to print when in speed mode. */
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants.kPIDLoopIdx));
			_sb.append("\ttrg:");
			_sb.append(targetPos);
		} else {
			/* Percent voltage mode */
			_talon.set(ControlMode.PercentOutput, leftYstick);
		}
		/* instrumentation */
		Instrum.Process(_talon, _sb);
		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (Exception e) {
		}
	}
}
