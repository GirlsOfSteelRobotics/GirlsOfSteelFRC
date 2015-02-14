package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.drive.DriveByJoystick;
import org.usfirst.frc.team3504.robot.lib.PIDSpeedController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
	
	//CANTalons
	public static CANTalon rightFrontWheel;
	public static CANTalon leftFrontWheel;
	public static CANTalon rightBackWheel;
	public static CANTalon leftBackWheel;
	
	//PID Values
    double Kp;
    double Ki;
    double Kd;
    
    double ticksPerInch;
    double ticksPerInchStrafing;
    
    double autoSpeed;
	
    //Gyro
    private Gyro robotGyro;
    boolean getGyro;
	
	//Encoders
    private TalonEncoder frontLeftEncoder;
    private TalonEncoder rearLeftEncoder;
    private TalonEncoder frontRightEncoder;
    private TalonEncoder rearRightEncoder;
    
    //Variables used to reset the encoders
    double initialFrontLeftEncoderDistance;
    double initialFrontRightEncoderDistance;
    double initialRearLeftEncoderDistance;
    double initialRearRightEncoderDistance;
    
    private RobotDrive gosDrive;
	
	public Chassis()
	{	
		ticksPerInch = Math.PI*8/(4*250);
		ticksPerInchStrafing = Math.PI*8/(4*250*4);
		autoSpeed = 0.75;
		
		rightFrontWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
		leftFrontWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
		rightBackWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
		leftBackWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);
		
		Kp = .0001;//SmartDashboard.getNumber("p value");//.0001
	    Ki = .00000001;//SmartDashboard.getNumber("i value");//.0000001
	    Kd = .00000001;//SmartDashboard.getNumber("d value");//.0000001
	    
	    robotGyro = new Gyro(RobotMap.GYRO_PORT);
        LiveWindow.addSensor("Chassis", "Gyro", robotGyro);
	    getGyro = true;
		
		frontLeftEncoder = new TalonEncoder(leftFrontWheel);
    	rearLeftEncoder = new TalonEncoder(leftBackWheel);
    	frontRightEncoder = new TalonEncoder(rightFrontWheel);
    	rearRightEncoder = new TalonEncoder(leftBackWheel);
    	
        gosDrive = new RobotDrive  (new PIDSpeedController(leftBackWheel, Kp, Ki, Kd, rearLeftEncoder),
				new PIDSpeedController(rightBackWheel, Kp, Ki, Kd, rearRightEncoder),
				new PIDSpeedController(leftFrontWheel, Kp, Ki, Kd, frontLeftEncoder),
				new PIDSpeedController(rightFrontWheel, Kp, Ki, Kd, frontRightEncoder));
        gosDrive.setInvertedMotor(MotorType.kRearRight, true);	//Invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	
    	gosDrive.setExpiration(0.1);
    	gosDrive.setSafetyEnabled(true); 
	}
	
	/* Twist Dead Zone
	 * 
	 * Only activate twist action if the control is turned 50% of the way.
	 */
	public double twistDeadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .5)
			return rawVal;
		else
			return 0.0;
	}
	
	public double deadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .3)
			return rawVal-.1;
		else
			return 0.0;
	}
	
	public void moveByJoystick(Joystick stick)
	{
		
		gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY())* ((-stick.getThrottle() + 1) / 2),
										deadZone(stick.getX())*((-stick.getThrottle() + 1) / 2),
										twistDeadZone(stick.getTwist())*((-stick.getThrottle() + 1) / 2),
										getGyro ? robotGyro.getAngle() : 0);
		
	}
	
	public void autoDriveRight(){
		gosDrive.mecanumDrive_Polar(autoSpeed, 180, 0);//figure out what the angle should be
	}
	
	public void autoDriveLeft()
	{
		gosDrive.mecanumDrive_Polar(autoSpeed, 0, 0);
	}
	
	public void autoDriveBackward(){
		gosDrive.mecanumDrive_Polar(autoSpeed, 270, 0); //check to make sure this angle is correcct
	}
	
	public void autoDriveForward(){
		gosDrive.mecanumDrive_Polar(autoSpeed, 90, 0);
	}
	
	public void autoTurnClockwise()
	{
		gosDrive.mecanumDrive_Cartesian(0, 0, autoSpeed, 0);
	}
	
	public void autoTurnCounterclockwise()
	{
		gosDrive.mecanumDrive_Cartesian(0, 0, -autoSpeed, 0);
	}
	
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
	
	public void printPositionsToSmartDashboard()
	{
		SmartDashboard.putNumber("Drive Front Left Encoder Distance ", getFrontLeftEncoderPosition());
		SmartDashboard.putNumber("Drive Front Right Encoder Distance ", getFrontRightEncoderPosition());
		SmartDashboard.putNumber("Drive Rear Left Encoder Distance ", getRearLeftEncoderPosition());
		SmartDashboard.putNumber("Drive Rear Right Encoder Distance ", getRearRightEncoderPosition());
	}
	
	//Need to implement
	public void resetDistance(){
		initialFrontLeftEncoderDistance = leftFrontWheel.getEncPosition();
		initialFrontRightEncoderDistance = rightFrontWheel.getEncPosition();
		initialRearLeftEncoderDistance = leftBackWheel.getEncPosition();
		initialRearRightEncoderDistance = rightBackWheel.getEncPosition();
	}
	
	private double getFrontLeftEncoderPosition(){
		return leftFrontWheel.getEncPosition();
	}
	
	private double getRearLeftEncoderPosition(){
		return leftBackWheel.getEncPosition();
	}
	
	private double getFrontRightEncoderPosition(){
		return rightFrontWheel.getEncPosition();
	}
	
	private double getRearRightEncoderPosition(){
		return rightBackWheel.getEncPosition();
	}
	
	public void resetGyro() {
		robotGyro.reset();
	}
	
	public void getGyro() {
		getGyro = !getGyro;
	}
 
    private double rearRightEncoderDistance()
    {
    	return Math.abs(getRearRightEncoderPosition())- Math.abs(initialRearRightEncoderDistance);
    }
    
    private double frontRightEncoderDistance()
    {
    	return Math.abs(getFrontRightEncoderPosition())- Math.abs(initialFrontRightEncoderDistance);
    }
    
    private double frontLeftEncoderDistance()
    {
    	return Math.abs(getFrontLeftEncoderPosition())- Math.abs(initialFrontLeftEncoderDistance);
    }
    
    private double rearLeftEncoderDistance()
    {
    	return Math.abs(getRearLeftEncoderPosition())- Math.abs(initialRearLeftEncoderDistance);
    }
    
    public double getDistanceForward()
    {
    	return (rearLeftEncoderDistance()+frontRightEncoderDistance())/(2*ticksPerInchStrafing);
    }
    
    public double getDistanceBackwards()
    {
    	return (rearLeftEncoderDistance()+frontRightEncoderDistance())/(2*ticksPerInchStrafing);
    }
    
    public double getDistanceLeft()
    {
    	//calculates the average of the encoder distances
    	return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance())/(4*ticksPerInch));
    }
    
    public double getDistanceRight()
    {
    	//calculates the average of the encoder distances
    	return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance())/(4*ticksPerInch));
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick());
   
    }
    
}

