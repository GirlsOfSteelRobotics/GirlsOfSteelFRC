package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	RobotDrive gosDrive;
    Gyro robotGyro;
	
	public Chassis()
	{
		gosDrive.setExpiration(0.1);
        gosDrive = new RobotDrive(RobotMap.FRONT_LEFT_CHANNEL, RobotMap.REAR_LEFT_CHANNEL,
        							RobotMap.FRONT_RIGHT_CHANNEL, RobotMap.REAR_RIGHT_CHANNEL);
    	gosDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kRearLeft, true);		// may need to change or remove this to match robot
    	gosDrive.setSafetyEnabled(true);
    	
        robotGyro = new Gyro(RobotMap.GYRO_PORT);
	}
	
	public void moveByJoystick(Joystick stick)
	{
		gosDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), robotGyro.getAngle());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

