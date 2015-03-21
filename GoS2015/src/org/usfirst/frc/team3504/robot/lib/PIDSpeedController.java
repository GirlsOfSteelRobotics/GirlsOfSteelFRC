package org.usfirst.frc.team3504.robot.lib;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		SmartDashboard.putNumber("output", output);
		pid.setSetpoint(output);
		// pid.setSetpoint(output/SmartDashboard.getNumber("P val"));
	}

	@Override
	public double get() {
		// return pid.getSetpoint()*SmartDashboard.getNumber("P val");
		return (pid.getSetpoint());
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
