package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.MotorStop;

public class Motor extends Subsystem {

	private WPI_TalonSRX mainMotor;

	private final double speed = 0.5;
	private final double slowSpeed = 0.25;


	public Motor() {
		mainMotor = new WPI_TalonSRX(Constants.MAIN_MOTOR_TALON);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MotorStop());
	}

	public WPI_TalonSRX getTalon(){
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
