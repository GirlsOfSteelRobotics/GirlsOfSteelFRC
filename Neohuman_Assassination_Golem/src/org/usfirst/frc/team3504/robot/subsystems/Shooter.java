package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shooter extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private CANTalon shooterMotor1;
	private CANTalon shooterMotor2;
	
	public Shooter() {
		shooterMotor1 = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
		shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
		shooterMotor2 = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
		shooterMotor2.changeControlMode(TalonControlMode.PercentVbus);
		LiveWindow.addActuator("Shooter", "Talon", shooterMotor1);
		LiveWindow.addActuator("Shooter", "Talon", shooterMotor2);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
<<<<<<< HEAD
    public void spinWheels() {
    	shooterMotor1.set(1.0);
    	shooterMotor2.set(1.0);
=======
    public void spinWheels(double speed) {
    	shooterMotor1.set(speed);
    	shooterMotor2.set(-speed);
>>>>>>> branch 'master' of https://github.com/GirlsOfSteelRobotics/2016GirlsOfSteel.git
    }
    
<<<<<<< HEAD
    public void collectWheels() {
    	shooterMotor1.set(-0.4);
    	shooterMotor2.set(-0.4);
=======
    public void collectWheels(double speed) {
    	shooterMotor1.set(-speed);
    	shooterMotor2.set(speed);
>>>>>>> branch 'master' of https://github.com/GirlsOfSteelRobotics/2016GirlsOfSteel.git
    }
    
    public void stop() {
    	shooterMotor1.set(0.0);
    	shooterMotor2.set(0.0);
    }
}

