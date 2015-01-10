package org.usfirst.frc.team3504.robot;


import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public   class Robot extends SampleRobot {
	
    RobotDrive robotDrive;
    Joystick stick;
    Gyro robotGyro;

    // Channels for the wheels
    final int frontLeftChannel	= 0;
    final int rearLeftChannel	= 1;
    final int frontRightChannel	= 3;
    final int rearRightChannel	= 2;
    final int gyro = 0;
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;

    public Robot() {
    	
    	robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);	
    	robotDrive.setExpiration(0.1);
    	robotDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
    	robotDrive.setInvertedMotor(MotorType.kRearRight, true);		// you may need to change or remove this to match your robot

        stick = new Joystick(joystickChannel);
        robotGyro = new Gyro(gyro);
    }
        

    /**
     * Runs the motors with Mecanum drive.
     */
    public void operatorControl() {
        robotDrive.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	
        	// Use the joystick X axis for lateral movement, Y axis for forward movement, and Z axis for rotation.
        	// This sample does not use field-oriented drive, so the gyro input is set to zero.
            robotDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), robotGyro.getAngle());
            
            Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
        }
    }
    
}
