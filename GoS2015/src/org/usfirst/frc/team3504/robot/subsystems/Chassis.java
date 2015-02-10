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
		if (getGyro)
			gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY())* ((-stick.getThrottle() + 1) / 2), deadZone(stick.getX())* ((-stick.getThrottle() + 1) / 2), twistDeadZone(stick.getTwist())*((-stick.getThrottle() + 1) / 2), robotGyro.getAngle());
		else
			gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY())* ((-stick.getThrottle() + 1) / 2), deadZone(stick.getX())* ((-stick.getThrottle() + 1) / 2), twistDeadZone(stick.getTwist())*((-stick.getThrottle() + 1) / 2), 0);
	}
	
	public void autoDriveSideways(double speed){
		gosDrive.mecanumDrive_Polar(speed, 90, 0);//figure out what the angle should be
	}
	
	public void autoDriveBackward(double speed){
		gosDrive.mecanumDrive_Polar(speed, 45, 0);
	}
	
	public void autoDriveForward(double speed){
		gosDrive.mecanumDrive_Polar(speed, 90, 0);
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
	
	//Need to implement
	public void resetEncoders(){
	}
	
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
	
    public void resetFrontLeftEncoder(double newVal) {
    	initialFrontLeftEncoderDistance = newVal;
    }
    
    public boolean isFrontLeftEncoderFinished(double finalVal, double currentVal)
    {
    	return Math.abs(getFrontLeftEncoderDistance())- Math.abs(initialFrontLeftEncoderDistance) == finalVal;
    }
    
    public void resetFrontRightEncoderDistance(double newVal){
		initialFrontRightEncoderDistance = newVal;
    }
    
    public boolean isFrontRightEncoderFinished(double finalVal, double currentVal)
    {
    	return Math.abs(getFrontRightEncoderDistance())- Math.abs(initialFrontRightEncoderDistance) == finalVal;
    }
    
    public void resetRearLeftEncoderDistance(double newVal){
		initialRearLeftEncoderDistance = newVal;
    }
    
    public boolean isRearLeftEncoderFinished(double finalVal, double currentVal)
    {
    	return Math.abs(getRearLeftEncoderDistance())- Math.abs(initialRearLeftEncoderDistance) == finalVal;
    }
  
    public void resetRearRightEncoderDistance(double newVal){
		initialRearRightEncoderDistance = newVal;
    }
    
    public boolean isRearRightEncoderFinished(double finalVal, double currentVal)
    {
    	return Math.abs(getRearRightEncoderDistance())- Math.abs(initialRearRightEncoderDistance) == finalVal;
    }
 
    public double RearRightEncoderDistance()
    {
    	return Math.abs(getRearRightEncoderDistance())- Math.abs(initialRearRightEncoderDistance);
    }
    
    public double FrontRightEncoderDistance()
    {
    	return Math.abs(getFrontRightEncoderDistance())- Math.abs(initialFrontRightEncoderDistance);
    }
    
    public double FrontLeftEncoderDistance()
    {
    	return Math.abs(getFrontLeftEncoderDistance())- Math.abs(initialFrontLeftEncoderDistance);
    }
    
    public double RearLeftEncoderDistance()
    {
    	return Math.abs(getRearLeftEncoderDistance())- Math.abs(initialRearLeftEncoderDistance);
    }
    
    public double AverageOfEncoderDistance()
    {	
    	return (initialRearLeftEncoderDistance + initialFrontLeftEncoderDistance + initialRearRightEncoderDistance + initialFrontRightEncoderDistance)/4; 
    			
    }
    public double getDistanceLeft() {
    	return 0;
    }

    public double getDistanceHorizontal() {
    	return ((RearLeftEncoderDistance() + FrontLeftEncoderDistance() + FrontRightEncoderDistance() + RearRightEncoderDistance())/4);
    }
    
    public double getDistanceVertical() {
    	return 0;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick());
    	//setDefaultCommand(new TestPhotoSensor());
   
    }
    
}

