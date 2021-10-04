package com.gos.testboard2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.testboard2020.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Motor extends SubsystemBase {

	private WPI_TalonSRX mainMotor;

	private final double speed = 0.5;
	private final double slowSpeed = 0.25;

	public Motor() {
		mainMotor = new WPI_TalonSRX(Constants.MAIN_MOTOR_TALON);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public WPI_TalonSRX getTalon() {
		return mainMotor;
	}

	public void setSpeedMode() {
		mainMotor.set(0.25);
	}

	public void motorGoFast() {
		mainMotor.set(speed);
	}

	public void motorGoSlow() {
		mainMotor.set(slowSpeed);
	}

	public void stop() {
		mainMotor.stopMotor();
	}
}
