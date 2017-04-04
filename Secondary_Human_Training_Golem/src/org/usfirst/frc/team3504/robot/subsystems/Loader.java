package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Loader extends Subsystem {

	private CANTalon loaderMotor;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Loader() {
		loaderMotor = new CANTalon(RobotMap.LOADER_MOTOR);

		LiveWindow.addActuator("Loader", "loaderMotor", loaderMotor);
	}

	public void loadBall(double speed) {
		loaderMotor.set(speed);
	}

	public void stopLoader() {
		loaderMotor.set(0.0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
