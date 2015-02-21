package org.usfirst.frc.team3504.robot;


import org.usfirst.frc.team3504.robot.lib.PIDSpeedController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
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
    
    private Encoder frontLeftEncoder;
    private Encoder rearLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder rearRightEncoder;
    
    public static final int FRONT_LEFT_WHEEL_ENCODER_A = 0; //Encoders
    public static final int FRONT_LEFT_WHEEL_ENCODER_B = 1;
    public static final int REAR_LEFT_WHEEL_ENCODER_A = 2;
    public static final int REAR_LEFT_WHEEL_ENCODER_B = 3;
    public static final int FRONT_RIGHT_WHEEL_ENCODER_A = 4;
    public static final int FRONT_RIGHT_WHEEL_ENCODER_B = 5;
    public static final int REAR_RIGHT_WHEEL_ENCODER_A = 6;
    public static final int REAR_RIGHT_WHEEL_ENCODER_B = 7;
    
    public static final int FRONT_LEFT_WHEEL_CHANNEL = 0; //Motors
    public static final int REAR_LEFT_WHEEL_CHANNEL	= 1;
    public static final int FRONT_RIGHT_WHEEL_CHANNEL = 2;
    public static final int REAR_RIGHT_WHEEL_CHANNEL = 3;
    
    double Kp;
    double Ki;
    double Kd;
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;

    public Robot() {
    	
    	Kp = .0001;//SmartDashboard.getNumber("p value");
	    Ki = .0000001;//SmartDashboard.getNumber("i value");
	    Kd = .0000001;//SmartDashboard.getNumber("d value");
    	
		frontLeftEncoder = new Encoder(FRONT_LEFT_WHEEL_ENCODER_A, FRONT_LEFT_WHEEL_ENCODER_B);
    	rearLeftEncoder = new Encoder(REAR_LEFT_WHEEL_ENCODER_A, REAR_LEFT_WHEEL_ENCODER_B);
    	frontRightEncoder = new Encoder(FRONT_RIGHT_WHEEL_ENCODER_A, FRONT_RIGHT_WHEEL_ENCODER_B);
    	rearRightEncoder = new Encoder(REAR_RIGHT_WHEEL_ENCODER_A, REAR_RIGHT_WHEEL_ENCODER_B);

//	    robotDrive = new RobotDrive(new PIDSpeedController(new Talon(FRONT_LEFT_WHEEL_CHANNEL), Kp, Ki, Kd, frontLeftEncoder), 
//				new PIDSpeedController(new Talon(REAR_LEFT_WHEEL_CHANNEL), Kp, Ki, Kd, rearLeftEncoder), 
//				new PIDSpeedController(new Talon(FRONT_RIGHT_WHEEL_CHANNEL), Kp, Ki, Kd, frontRightEncoder),
//				new PIDSpeedController(new Talon(REAR_RIGHT_WHEEL_CHANNEL), Kp, Ki, Kd, rearRightEncoder));
	    robotDrive = new RobotDrive(new CANTalon(FRONT_LEFT_WHEEL_CHANNEL), 
				new CANTalon(REAR_LEFT_WHEEL_CHANNEL), 
				new CANTalon(FRONT_RIGHT_WHEEL_CHANNEL),
				new CANTalon(REAR_RIGHT_WHEEL_CHANNEL));
	    robotDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
	    robotDrive.setInvertedMotor(MotorType.kRearRight, true);		// may need to change or remove this to match robot
	    robotDrive.setExpiration(0.1);
	    robotDrive.setSafetyEnabled(true);

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
            //robotDrive.mecanumDrive_Polar(stick.getMagnitude() * ((stick.getThrottle() + 1) / 2), stick.getDirectionDegrees(), stick.getTwist());
            
            
            Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
        }
    }
    
}
