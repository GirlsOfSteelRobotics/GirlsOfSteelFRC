package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.LidarLitePWM;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Motor extends Subsystem {

	private WPI_TalonSRX mainMotor;

	private LidarLitePWM lidar;

	private final double speed = 0.5;
	private final double slowSpeed = 0.25;

	public double LIDAR_TOLERANCE = 1; // tune

	public Motor() {
		mainMotor = new WPI_TalonSRX(RobotMap.MAIN_MOTOR_TALON);
		lidar = new LidarLitePWM(RobotMap.LIDAR_DIO);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MyCommand());
	}

	public double getLidarDistance() {
		return lidar.getDistance();
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
