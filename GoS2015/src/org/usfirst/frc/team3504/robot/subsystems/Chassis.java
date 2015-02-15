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
	public static CANTalon frontRightWheel;
	public static CANTalon frontLeftWheel;
	public static CANTalon backRightWheel;
	public static CANTalon backLeftWheel;
	
	//PID Values
    double kP;
    double kI;
    double kD;
    
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
    private Joystick controlStick;
	
	public Chassis()
	{	
		ticksPerInch = Math.PI*8/(4*250);
		ticksPerInchStrafing = Math.PI*8/(4*250*4);
		autoSpeed = 0.75;
		
		frontRightWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
		frontLeftWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
		backRightWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
		backLeftWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);
		
		kP = .0001;//SmartDashboard.getNumber("p value");//.0001
	    kI = .00000001;//SmartDashboard.getNumber("i value");//.0000001
	    kD = .00000001;//SmartDashboard.getNumber("d value");//.0000001
	    
	    robotGyro = new Gyro(RobotMap.GYRO_PORT);
        LiveWindow.addSensor("Chassis", "Gyro", robotGyro);
	    getGyro = true;
		
		frontLeftEncoder = new TalonEncoder(frontLeftWheel);
    	rearLeftEncoder = new TalonEncoder(backLeftWheel);
    	frontRightEncoder = new TalonEncoder(frontRightWheel);
    	rearRightEncoder = new TalonEncoder(backLeftWheel);
    	
        gosDrive = new RobotDrive  (new PIDSpeedController(backLeftWheel, kP, kI, kD, rearLeftEncoder),
				new PIDSpeedController(backRightWheel, kP, kI, kD, rearRightEncoder),
				new PIDSpeedController(frontLeftWheel, kP, kI, kD, frontLeftEncoder),
				new PIDSpeedController(frontRightWheel, kP, kI, kD, frontRightEncoder));
        gosDrive.setInvertedMotor(MotorType.kRearRight, true);	//Invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	
    	gosDrive.setExpiration(0.1);
    	gosDrive.setSafetyEnabled(true); 
    	
    	controlStick = Robot.oi.getChassisJoystick();
	}
	
	/* Twist Dead Zone
	 * 
	 * Only activate twist action if the control is turned 50% of the way.
	 */
	private double twistDeadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .5)
			return rawVal;
		else
			return 0.0;
	}
	
	private double deadZone(double rawVal)
	{
		if(Math.abs(rawVal) > .3)
			return rawVal-.1;
		else
			return 0.0;
	}
	
	private double beattieDeadBand(double x)
	{
		// X^(1/1.75X)
		if (x > 0)
			return (Math.pow(x, 7.0 / (4.0 * x)));
		else if (x < 0)
			return -beattieDeadBand(-x);
		else
			return 0;
	}

	/** 
	 * Translate throttle value from joystick (range -1..+1) into motor speed (range 0..+1)
	 * The joystick value in the top position is -1, opposite of what might be expected,
	 * so multiply by negative one to invert the readings.
	 */
	private double throttleSpeed(Joystick stick)
	{
		return (-stick.getThrottle() + 1) / 2;
	}
	
	public void moveByJoystick(Joystick stick)
	{
		gosDrive.mecanumDrive_Cartesian(deadZone(-stick.getY()) * throttleSpeed(stick),
										deadZone(stick.getX()) * throttleSpeed(stick),
										twistDeadZone(stick.getTwist()) * throttleSpeed(stick),
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
		gosDrive.mecanumDrive_Cartesian(0, throttleSpeed(controlStick), 0, 0); //-.5
	}

	public void driveBackward()
	{
		gosDrive.mecanumDrive_Cartesian(0, -throttleSpeed(controlStick), 0, 0); //.5
	}
	
	public void driveRight()
	{
		gosDrive.mecanumDrive_Cartesian(-throttleSpeed(controlStick), 0, 0, 0); //.5
	}
	
	public void driveLeft()
	{
		gosDrive.mecanumDrive_Cartesian(throttleSpeed(controlStick), 0, 0,0); //.-5	
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
		SmartDashboard.putNumber("Drive Front Left Encoder Distance ", frontLeftWheel.getEncPosition());
		SmartDashboard.putNumber("Drive Front Right Encoder Distance ", frontRightWheel.getEncPosition());
		SmartDashboard.putNumber("Drive Rear Left Encoder Distance ", backLeftWheel.getEncPosition());
		SmartDashboard.putNumber("Drive Rear Right Encoder Distance ", backRightWheel.getEncPosition());
	}
	
	//Need to implement
	public void resetDistance(){
		initialFrontLeftEncoderDistance = frontLeftWheel.getEncPosition();
		initialFrontRightEncoderDistance = frontRightWheel.getEncPosition();
		initialRearLeftEncoderDistance = backLeftWheel.getEncPosition();
		initialRearRightEncoderDistance = backRightWheel.getEncPosition();
	}
	
	public void resetGyro() {
		robotGyro.reset();
	}
	
	public void getGyro() {
		getGyro = !getGyro;
	}
 
    private double rearRightEncoderDistance()
    {
    	return Math.abs(backRightWheel.getEncPosition() - initialRearRightEncoderDistance);
    }
    
    private double frontRightEncoderDistance()
    {
    	return Math.abs(frontRightWheel.getEncPosition() - initialFrontRightEncoderDistance);
    }
    
    private double frontLeftEncoderDistance()
    {
    	return Math.abs(frontLeftWheel.getEncPosition() - initialFrontLeftEncoderDistance);
    }
    
    private double rearLeftEncoderDistance()
    {
    	return Math.abs(backLeftWheel.getEncPosition() - initialRearLeftEncoderDistance);
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

