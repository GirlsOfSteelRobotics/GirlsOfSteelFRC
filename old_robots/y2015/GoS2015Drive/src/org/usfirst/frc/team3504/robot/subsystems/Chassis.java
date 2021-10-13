package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;
import org.usfirst.frc.team3504.robot.lib.PIDSpeedController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private RobotDrive gosDrive;
	private Gyro robotGyro;
 
    private Encoder frontLeftEncoder;
    private Encoder rearLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder rearRightEncoder;
    
    private CANTalon rightFrontWheel;
    private CANTalon rightBackWheel;
    private CANTalon leftFrontWheel;
    private CANTalon leftBackWheel;
    
    double Kp;
    double Ki;
    double Kd;
	
	public Chassis()
	{
		Kp = .0001;//SmartDashboard.getNumber("p value");
	    Ki = .0000001;//SmartDashboard.getNumber("i value");
	    Kd = .0000001;//SmartDashboard.getNumber("d value");
		
	/*	frontLeftEncoder = new Encoder(RobotMap.FRONT_LEFT_WHEEL_ENCODER_A, RobotMap.FRONT_LEFT_WHEEL_ENCODER_B);
    	rearLeftEncoder = new Encoder(RobotMap.REAR_LEFT_WHEEL_ENCODER_A, RobotMap.REAR_LEFT_WHEEL_ENCODER_B);
    	frontRightEncoder = new Encoder(RobotMap.FRONT_RIGHT_WHEEL_ENCODER_A, RobotMap.FRONT_RIGHT_WHEEL_ENCODER_B);
    	rearRightEncoder = new Encoder(RobotMap.REAR_RIGHT_WHEEL_ENCODER_A, RobotMap.REAR_RIGHT_WHEEL_ENCODER_B);
		
    	frontLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	rearLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	frontRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	rearRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	
       // gosDrive = new RobotDrive(RobotMap.FRONT_LEFT_CHANNEL, RobotMap.REAR_LEFT_CHANNEL,
        						//	RobotMap.FRONT_RIGHT_CHANNEL, RobotMap.REAR_RIGHT_CHANNEL);
        gosDrive = new RobotDrive  (new PIDSpeedController(RobotMap.leftFrontWheel, Kp, Ki, Kd, frontLeftEncoder),
				new PIDSpeedController(RobotMap.leftBackWheel, Kp, Ki, Kd, rearLeftEncoder),
				new PIDSpeedController(RobotMap.rightFrontWheel, Kp, Ki, Kd, frontRightEncoder),
				new PIDSpeedController(RobotMap.rightBackWheel, Kp, Ki, Kd, rearRightEncoder));
       */
	    
	    rightFrontWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
		leftFrontWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
		rightBackWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
		leftBackWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);
		
		
		
		
	    gosDrive = new RobotDrive(leftBackWheel, rightBackWheel, leftFrontWheel, rightFrontWheel);
	    
        gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kRearRight, true);		// may need to change or remove this to match robot
    	gosDrive.setExpiration(0.1);
    	gosDrive.setSafetyEnabled(true);
    	
    		
    	
    	leftBackWheel.setPID(Kp, Ki, Kd);
    	rightBackWheel.setPID(Kp, Ki, Kd);
    	leftFrontWheel.setPID(Kp, Ki, Kd);
    	rightFrontWheel.setPID(Kp, Ki, Kd);
    	
    	robotGyro = new Gyro(RobotMap.GYRO_PORT);
	}
	
	public double twistDeadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .9)
			return rawVal/5;
		else
			return 0.0;
	}
	
	public double deadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .1)
			return rawVal-.1;
		else
			return 0.0;
	}
	
	public void moveByJoystick(Joystick stick)
	{
		//if(stick.getMagnitude() > 0.02)
		//gosDrive.mecanumDrive_Polar(stick.getMagnitude() * ((stick.getThrottle() + 1) / 2), stick.getDirectionDegrees(), stick.getTwist());
		gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY()* ((-stick.getThrottle() + 1) / 2)), deadZone(stick.getX()* ((-stick.getThrottle() + 1) / 2)), twistDeadZone(stick.getTwist()), robotGyro.getAngle());
	}
	
	public double getRightFrontEncoderRate(){
		
		return rightFrontWheel.getEncVelocity();
	}
	
	public double getLeftFrontEncoderRate(){
		
		return leftFrontWheel.getEncVelocity();
		
	}
	
	public double getRightBackEncoderRate(){
		
		return rightBackWheel.getEncVelocity();
	
	}
	
	public double getLeftBackEncoderRate(){
		
		return leftBackWheel.getEncVelocity();
	
	}
	
	public void stop()
	{
		gosDrive.stopMotor();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick());
    }
}
