package org.usfirst.frc.team3504.robot.lib;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

public class PIDSpeedController implements SpeedController {
	
	PIDController pid;
	double Kp;
	double Ki;
	double Kd;

	public PIDSpeedController(SpeedController controller, double Kp, double Ki, double Kd, PIDSource source) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		pid = new PIDController(Kp, Ki, Kd, source, controller);
		pid.enable();
	}
	
	@Override
	public void pidWrite(double output) {
		pid.setSetpoint(output/Kp);
	}

	@Override
	public double get() {
		return (pid.getSetpoint()*Kp);
	}

	@Override
	public void set(double speed, byte syncGroup) {
		pidWrite(speed);
	}

	@Override
	public void set(double speed) {
		pidWrite(speed);
	}

	@Override
	public void disable() {
		pid.disable();
	}

}
