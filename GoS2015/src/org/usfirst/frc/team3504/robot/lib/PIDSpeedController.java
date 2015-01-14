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
		// TODO Auto-generated constructor stub
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		pid = new PIDController(Kp, Ki, Kd, source, controller);
		pid.enable();
	}
	
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		pid.setSetpoint(output/Kp);
	}

	@Override
	public double get() {
		// TODO Auto-generated method stub
		return (pid.getSetpoint()*Kp);
	}

	@Override
	public void set(double speed, byte syncGroup) {
		// TODO Auto-generated method stub
		pidWrite(speed);
	}

	@Override
	public void set(double speed) {
		// TODO Auto-generated method stub
		pidWrite(speed);
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		pid.disable();
	}

}
