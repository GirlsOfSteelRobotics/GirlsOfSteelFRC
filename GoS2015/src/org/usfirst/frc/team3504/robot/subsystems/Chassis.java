package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;
import org.usfirst.frc.team3504.robot.commands.TestPhotoSensor;
import org.usfirst.frc.team3504.robot.lib.PIDSpeedController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.PIDSource;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private RobotDrive gosDrive;
	private Gyro robotGyro;
 
    private TalonEncoder frontLeftEncoder;
    private TalonEncoder rearLeftEncoder;
    private TalonEncoder frontRightEncoder;
    private TalonEncoder rearRightEncoder;
    
    double Kp;
    double Ki;
    double Kd;
    
    boolean getGyro;
    
	// CANTalons
	public static CANTalon rightFrontWheel;
	public static CANTalon leftFrontWheel;
	public static CANTalon rightBackWheel;
	public static CANTalon leftBackWheel;
	
	public Chassis()
	{
		rightFrontWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
		leftFrontWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
		rightBackWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
		leftBackWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);
		
		Kp = .0001;//SmartDashboard.getNumber("p value");
	    Ki = .0000001;//SmartDashboard.getNumber("i value");
	    Kd = .0000001;//SmartDashboard.getNumber("d value");
	    
	    getGyro = true;
		
		frontLeftEncoder = new TalonEncoder(leftFrontWheel);
    	rearLeftEncoder = new TalonEncoder(leftBackWheel);
    	frontRightEncoder = new TalonEncoder(rightFrontWheel);
    	rearRightEncoder = new TalonEncoder(leftBackWheel);
		
    	//frontLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	//rearLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	//frontRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	//rearRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
    	
       // gosDrive = new RobotDrive(RobotMap.FRONT_LEFT_CHANNEL, RobotMap.REAR_LEFT_CHANNEL,
        						//	RobotMap.FRONT_RIGHT_CHANNEL, RobotMap.REAR_RIGHT_CHANNEL);
        gosDrive = new RobotDrive  (new PIDSpeedController(leftBackWheel, Kp, Ki, Kd, rearLeftEncoder),
				new PIDSpeedController(rightBackWheel, Kp, Ki, Kd, rearRightEncoder),
				new PIDSpeedController(leftFrontWheel, Kp, Ki, Kd, frontLeftEncoder),
				new PIDSpeedController(rightFrontWheel, Kp, Ki, Kd, frontRightEncoder));
        gosDrive.setInvertedMotor(MotorType.kRearRight, true);	// invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kFrontRight, true);		// may need to change or remove this to match robot
    	gosDrive.setExpiration(0.1);
    	gosDrive.setSafetyEnabled(true);

        robotGyro = new Gyro(RobotMap.GYRO_PORT);
        LiveWindow.addSensor("Chassis", "Gyro", robotGyro);
	}
	
	/* twistDeadZone
	 * 
	 * Only activate twist action if the control is turned 90% of the way.
	 * At that point, reduce the value by a factor of 5 so the robot doesn't twist too fast.
	 */
	public double twistDeadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .5)
			return rawVal-.5;
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
		if (getGyro)
		//if(stick.getMagnitude() > 0.02)
		//gosDrive.mecanumDrive_Polar(stick.getMagnitude() * ((stick.getThrottle() + 1) / 2), stick.getDirectionDegrees(), stick.getTwist());
			gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY()* ((-stick.getThrottle() + 1) / 2)), deadZone(stick.getX()* ((-stick.getThrottle() + 1) / 2)), twistDeadZone(stick.getTwist()*((-stick.getThrottle() + 1) / 2)), robotGyro.getAngle());
		else
			gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY()* ((-stick.getThrottle() + 1) / 2)), deadZone(stick.getX()* ((-stick.getThrottle() + 1) / 2)), twistDeadZone(stick.getTwist()*((-stick.getThrottle() + 1) / 2)), 0);
	}
	
	public void autoDriveSideways(double speed){
		gosDrive.mecanumDrive_Polar(0.75, 90, 0);//figure out what the angle should be
	}
	
	public void autoDriveBackward(double speed){
		gosDrive.mecanumDrive_Polar(speed, 45, 0);
	}
	
	public void autoDriveForward(double speed){
		gosDrive.mecanumDrive_Polar(speed, 90, 0);
	}
	
	//TODO; move Joystick to subsystem
	public void driveForward()
	{
		gosDrive.mecanumDrive_Cartesian(0, -((Robot.oi.getChassisJoystick().getThrottle() + 1) / 2), 0, 0); //-.5
	}

	public void driveBackward()
	{
		gosDrive.mecanumDrive_Cartesian(0, ((Robot.oi.getChassisJoystick().getThrottle() + 1) / 2), 0, 0); //.5
	}
	
	public void driveRight()
	{
		gosDrive.mecanumDrive_Cartesian(((Robot.oi.getChassisJoystick().getThrottle() + 1) / 2), 0, 0, 0); //.5
	}
	
	public void driveLeft()
	{
		gosDrive.mecanumDrive_Cartesian(-((Robot.oi.getChassisJoystick().getThrottle() + 1) / 2), 0, 0,0); //.-5
		
	}
	
	public void driveInCircle()
	{
		gosDrive.mecanumDrive_Cartesian(75, 25, 0, robotGyro.getAngle()); //May be equal -> change x and y vals
	}
	
	public void stop()
	{
		gosDrive.stopMotor();
	}
	
	public double getGyroAngle()
	{
		return robotGyro.getAngle();
	}
	
	public void resetEncoders(){
		//leftFrontWheel.reset();
		//rightFrontWheel.reset();
		//leftBackWheel.reset();
		//rightBackWheel.reset();
	}
	//need to change getEncVelocity to getDistance later
	public double getFrontLeftEncoderRate()
	{
		return leftFrontWheel.getEncVelocity();
	}
	
	public double getFrontLeftEncoderDistance(){
		return leftFrontWheel.getEncPosition();
	}
	
	public double getRearLeftEncoderRate()
	{
		return leftBackWheel.getEncVelocity();
	}
	
	public double getRearLeftEncoderDistance(){
		return leftBackWheel.getEncPosition();
	}
	
	public double getFrontRightEncoderRate()
	{
		return rightFrontWheel.getEncVelocity();
	}
	
	public double getFrontRightEncoderDistance(){
		return rightFrontWheel.getEncPosition();
	}
	
	public double getRearRightEncoderRate()
	{
		return rightBackWheel.getEncVelocity();
	}
	
	public double getRearRightEncoderDistance(){
		return rightBackWheel.getEncPosition();
	}
	public void resetGyro() {
		robotGyro.reset();
	}
	
	public void getGyro() {
		getGyro = !getGyro;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick());
    	//setDefaultCommand(new TestPhotoSensor());
    }
}
