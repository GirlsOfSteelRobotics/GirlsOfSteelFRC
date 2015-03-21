package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.drive.DriveByJoystick;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort.Port;


public class Chassis extends Subsystem {

	// CANTalons
	public static CANTalon frontRightWheel;
	public static CANTalon frontLeftWheel;
	public static CANTalon rearRightWheel;
	public static CANTalon rearLeftWheel;

	// Gyro
	boolean getGyro;
	double oldDirection;
	private AHRS IMUGyro;
	private double oldXGyroDisplacement = 0;
	private double oldYGyroDisplacement = 0;


	// Variables used to reset the encoders
	double initialFrontLeftEncoderDistance;
	double initialFrontRightEncoderDistance;
	double initialRearLeftEncoderDistance;
	double initialRearRightEncoderDistance;

	private double topSpeed = 400;

	private RobotDrive gosDrive;

	// PID Constants
	private static final double kP = 1.0;
	private static final double kI = 0.01;
	private static final double kD = 20;

	// Encoder to Distance Constants
	private static final double inchesPerTick = Math.PI * 6 / (4 * 256);
	private static final double inchesPerTickStrafing = inchesPerTick / 2;

	// Speed constant for autonomous driving (
	// (may need to change for strafing vs. normal driving)
	private static final double autoSpeed = 0.25;

	private static final double MIN_SPEED = .5;// .15;
	private static final double MAX_SPEED = .75;// .3;
	private static final double RAMP_UP_DISTANCE = 5;
	private static final double RAMP_DOWN_DISTANCE = 40;

	private static final double MIN_SPEED_STRAFING = .3;
	private static final double MAX_SPEED_STRAFING = .7;

	public Chassis() {
		frontRightWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
		frontLeftWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
		rearRightWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
		rearLeftWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);

		frontRightWheel.enableBrakeMode(true);
		frontLeftWheel.enableBrakeMode(true);
		rearRightWheel.enableBrakeMode(true);
		rearLeftWheel.enableBrakeMode(true);

		// SmartDashboard.putNumber("P value", 1);
		// SmartDashboard.putNumber("I value", 0.01);
		// SmartDashboard.putNumber("D value", 20);
		// SmartDashboard.putNumber("F value", 0.0);

		frontRightWheel.changeControlMode(ControlMode.Speed);
		frontRightWheel.setPID(kP, kI, kD, 0, 0, 0, 0);
		frontRightWheel.reverseSensor(true);

		frontLeftWheel.changeControlMode(ControlMode.Speed);
		frontLeftWheel.setPID(kP, kI, kD, 0, 0, 0, 0);
		frontLeftWheel.reverseSensor(true);

		rearRightWheel.changeControlMode(ControlMode.Speed);
		rearRightWheel.setPID(kP, kI, kD, 0, 0, 0, 0);
		rearRightWheel.reverseSensor(true);

		rearLeftWheel.changeControlMode(ControlMode.Speed);
		rearLeftWheel.setPID(kP, kI, kD, 0, 0, 0, 0);
		rearLeftWheel.reverseSensor(true);

		getGyro = true;

		SerialPort temp = new SerialPort(57600, Port.kMXP);

		IMUGyro = new AHRS(temp);

		IMUGyro.zeroYaw();

		gosDrive = new RobotDrive(rearLeftWheel, rearRightWheel, frontLeftWheel, frontRightWheel);
		// new RobotDrive (new PIDSpeedController(rearLeftWheel, kP, kI, kD,
		// rearLeftEncoder),
		// new PIDSpeedController(rearRightWheel, kP, kI, kD, rearRightEncoder),
		// new PIDSpeedController(frontLeftWheel, kP, kI, kD, frontLeftEncoder),
		// new PIDSpeedController(frontRightWheel, kP, kI, kD,
		// frontRightEncoder));

		gosDrive.setMaxOutput(topSpeed);

		gosDrive.setInvertedMotor(MotorType.kRearRight, true); // Invert the
																// left side
																// motors
		gosDrive.setInvertedMotor(MotorType.kFrontRight, true);
		gosDrive.setExpiration(0.1);
		gosDrive.setSafetyEnabled(false);
		SmartDashboard.putNumber("Front Left", 0.0);
		SmartDashboard.putNumber("Front Right", 0.0);
		SmartDashboard.putNumber("Rear Right", 0.0);
		SmartDashboard.putNumber("Rear Left", 0.0);
		SmartDashboard.putBoolean("Velocity?", true);
	}

	public void spinWheelsSlowly() {
		if (SmartDashboard.getBoolean("Velocity?"))
			frontRightWheel.changeControlMode(ControlMode.Speed);
		else
			frontRightWheel.changeControlMode(ControlMode.PercentVbus);
		SmartDashboard.putNumber("Bus Voltage", frontRightWheel.getBusVoltage());
		SmartDashboard.putNumber("Closed Loop Error", frontRightWheel.getClosedLoopError());

		// frontRightWheel.setPID(SmartDashboard.getNumber("P value"),
		// SmartDashboard.getNumber("I value"),
		// SmartDashboard.getNumber("D value"));
		printPositionsToSmartDashboard();
		frontLeftWheel.set((SmartDashboard.getNumber("Front Left")) * 750);
		frontRightWheel.set((SmartDashboard.getNumber("Front Right")) * 750);
		rearRightWheel.set((SmartDashboard.getNumber("Rear Right")) * 750);
		rearLeftWheel.set(SmartDashboard.getNumber("Rear Left") * 750);
	}

	/*
	 * Twist Dead Zone
	 * 
	 * Only activate twist action if the control is turned 50% of the way.
	 */
	private double twistDeadZone(double rawVal) {
		if (Math.abs(rawVal) > .4)
			return rawVal - .1;
		else
			return 0.0;
	}

	// This is incorrect for negative vs. positive inputs
	private double deadZone(double rawVal) {
		if (Math.abs(rawVal) > .3)
			return rawVal;// .1;

		else
			return 0.0;
	}

	/**
	 * Improved deadband function that behaves consistently with positive and
	 * negative inputs Uses the curve f(x) = x ^ (1/x) Type this into Google
	 * Search to graph it: graph x^(1/x) Returns zero for values less than about
	 * 0.25, then scales up to return 1 for an input of 1
	 */
	private double beattieDeadBand(double x) {
		if (x > 0)
			return Math.pow(x, 1.0 / x);
		else if (x < 0)
			return -beattieDeadBand(-x);
		else
			return 0;
	}

	/**
	 * Improved deadband function that behaves consistently with positive and
	 * negative inputs Uses the curve f(x) = x ^ (4/x) Type this into Google
	 * Search to graph it: graph x^(4/x) Returns zero for values less than about
	 * 0.5, then scales up to return 1 for an input of 1
	 */
	private double beattieTwistDeadBand(double x) {
		if (x > 0)
			return Math.pow(x, 4.0 / x);
		else if (x < 0)
			return -beattieTwistDeadBand(-x);
		else
			return 0;
	}

	/**
	 * Translate throttle value from joystick (range -1..+1) into motor speed
	 * (range 0..+1) The joystick value in the top position is -1, opposite of
	 * what might be expected, so multiply by negative one to invert the
	 * readings.
	 */
	private double throttleSpeed(Joystick stick) {
		double temp = (-stick.getThrottle() + 1) / 2;
		if (temp < .1)
			return .1;
		// else if(temp > .7)
		// return .7;
		else
			return temp;
		// double min = 0.01;
		// return //(stick.getThrottle()*(1-min))/2 + min;
	}

	public double getGyroAngle() {
		return IMUGyro.getYaw();
	}

	public void resetGyro() {
		IMUGyro.zeroYaw();
	}

	/**
	 * This method toggles whether the driving by joystick is relative to the
	 * robot or the field It enables or disables the use of the current gyro
	 * reading in mecanumDrive_Cartesian()
	 */
	public void getGyro() {
		getGyro = !getGyro;
		SmartDashboard.putBoolean("Gyro: ", getGyro);
	}

	public double determineTwistFromGyro(Joystick stick) {
		double desiredDirection;
		double temp = stick.getDirectionDegrees();
		if (temp < 0)
			desiredDirection = temp - 360;
		else
			desiredDirection = temp;
		double change = (oldDirection - IMUGyro.getYaw());

		SmartDashboard.putNumber("Desired Direction", desiredDirection);
		SmartDashboard.putNumber("Change", change);

		if ((desiredDirection - change) > 0) // the robot angle is less than the
												// desired angle (to the right
												// of)
			return -0.2;
		else if ((desiredDirection - change) < 0) // robot angle is greater than
													// the desired angle (to the
													// left)
			return 0.2;
		else
			return 0; // robot angle = desired angle
	}

	public void moveByJoystick(Joystick stick) {
		double temp = IMUGyro.getYaw();
		if (temp < 0)
			temp = temp + 360;

		SmartDashboard.putNumber("I valueeeee", frontRightWheel.getI());

		SmartDashboard.putNumber("GYRO Get Yaw", temp);

		SmartDashboard.putNumber("x dir", deadZone(-stick.getY()) * throttleSpeed(stick));
		SmartDashboard.putNumber("y dir", deadZone(-stick.getX()) * throttleSpeed(stick));
		SmartDashboard.putNumber("rot", twistDeadZone(stick.getTwist()) * throttleSpeed(stick));

		SmartDashboard.putNumber("Throttle Speeddddd", throttleSpeed(stick));

		double tempX = IMUGyro.getDisplacementX();
		double tempXCorrected = tempX - oldXGyroDisplacement;
		double tempY = IMUGyro.getDisplacementY();
		double tempYCorrected = tempY - oldYGyroDisplacement;

		SmartDashboard.putNumber("X Displacement Gyro", tempX);
		SmartDashboard.putNumber("Y Displacement Gyro", tempY);

		SmartDashboard.putNumber("X Displacement Gyro Corrected", tempXCorrected);
		SmartDashboard.putNumber("Y Displacement Gyro Corrected", tempYCorrected);

		SmartDashboard.putNumber("FUSED HEADING!!!!!", IMUGyro.getFusedHeading());

		SmartDashboard.putNumber("Direction we want to go", stick.getDirectionDegrees());
		SmartDashboard.putNumber("Direction headed from Gyro", Math.toDegrees(Math.atan(tempY / tempX)));
		SmartDashboard
				.putNumber("Direction headed from Gyro Corrected", Math.toDegrees(Math.atan(tempYCorrected / tempXCorrected)));

		SmartDashboard.putNumber("Gyro Compass Heading", IMUGyro.getCompassHeading());

		SmartDashboard.putNumber("Closed Loop Error", frontRightWheel.getClosedLoopError());

		gosDrive.mecanumDrive_Cartesian(beattieDeadBand(-stick.getY()) * throttleSpeed(stick), beattieDeadBand(stick.getX())
				* throttleSpeed(stick), (beattieTwistDeadBand(stick.getTwist())) * throttleSpeed(stick), getGyro ? temp : 0);

		SmartDashboard.putNumber("Sending Val Front Left", frontLeftWheel.getSetpoint());
		SmartDashboard.putNumber("Sending Val Rear Left", rearLeftWheel.getSetpoint());
		SmartDashboard.putNumber("Sending Val Front Right", frontRightWheel.getSetpoint());
		SmartDashboard.putNumber("Sending Val Rear Right", rearRightWheel.getSetpoint());

		// oldDirection = IMUGyro.getYaw();
		// gyroAngleCounter++;
	}

	public double calculateSpeedStrafing(double goalDist, double currentDist) {
		double speed;
		if (currentDist < RAMP_UP_DISTANCE) {
			speed = (((MAX_SPEED_STRAFING - MIN_SPEED_STRAFING) / RAMP_UP_DISTANCE) * currentDist + MIN_SPEED_STRAFING);
		} else if (goalDist - currentDist < RAMP_DOWN_DISTANCE) {
			speed = (((MIN_SPEED_STRAFING - MAX_SPEED_STRAFING) / RAMP_DOWN_DISTANCE)
					* (currentDist - (goalDist - RAMP_DOWN_DISTANCE)) + MAX_SPEED_STRAFING);
		} else {
			speed = MAX_SPEED_STRAFING;
		}

		SmartDashboard.putNumber("speed", speed);
		System.out.print("Speed " + speed);
		SmartDashboard.putNumber("current distance", currentDist);
		System.out.println(" Current distance " + currentDist);
		SmartDashboard.putNumber("goal distance", goalDist);

		return speed;
	}

	public double calculateSpeed(double goalDist, double currentDist) {
		double speed;
		if (currentDist < RAMP_UP_DISTANCE) {
			speed = (((MAX_SPEED - MIN_SPEED) / RAMP_UP_DISTANCE) * currentDist + MIN_SPEED);
		} else if (goalDist - currentDist < RAMP_DOWN_DISTANCE) {
			speed = (((MIN_SPEED - MAX_SPEED) / RAMP_DOWN_DISTANCE) * (currentDist - (goalDist - RAMP_DOWN_DISTANCE)) + MAX_SPEED);
		} else {
			speed = MAX_SPEED;
		}

		SmartDashboard.putNumber("speed", speed);
		System.out.print("Speed " + speed);
		SmartDashboard.putNumber("current distance", currentDist);
		System.out.println(" Current distance " + currentDist);
		SmartDashboard.putNumber("goal distance", goalDist);

		return speed;
	}

	public void autoDriveRight(double goalDist) {
		gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceRight()), 180, 0);// figure
																							// out
																							// what
																							// the
																							// angle
																							// should
																							// be
	}

	public void autoDriveLeft(double goalDist) {
		gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceLeft()), 0, 0);
	}

	public void autoDriveBackward(double goalDist) {
		gosDrive.mecanumDrive_Polar(calculateSpeedStrafing(goalDist, getDistanceBackwards()), 270, 0); // check
																										// to
																										// make
																										// sure
																										// this
																										// angle
																										// is
																										// correct
	}

	public void autoDriveForward(double goalDist) {
		gosDrive.mecanumDrive_Polar(calculateSpeedStrafing(goalDist, getDistanceForward()), 90, 0);
	}

	public void autoTurnClockwise() {
		gosDrive.mecanumDrive_Cartesian(0, 0, autoSpeed, 0);
	}

	public void autoTurnCounterclockwise() {
		gosDrive.mecanumDrive_Cartesian(0, 0, -autoSpeed, 0);
	}

	public void driveForward() {
		gosDrive.mecanumDrive_Polar(1, 90, 0);
	}

	public void driveBackward() {
		gosDrive.mecanumDrive_Cartesian(0, -throttleSpeed(Robot.oi.getChassisJoystick()), 0, 0);
	}

	public void driveRight() {
		gosDrive.mecanumDrive_Cartesian(-throttleSpeed(Robot.oi.getChassisJoystick()), 0, 0, 0);
	}

	public void driveLeft() {
		gosDrive.mecanumDrive_Cartesian(throttleSpeed(Robot.oi.getChassisJoystick()), 0, 0, 0);
	}

	public void stop() {
		gosDrive.stopMotor();
	}

	public void printPositionsToSmartDashboard() {
		SmartDashboard.putNumber("Front Left Velocity", frontLeftWheel.getSpeed());
		SmartDashboard.putNumber("Front Right Velocity", frontRightWheel.getSpeed());
		SmartDashboard.putNumber("Back Left Velocity", rearLeftWheel.getSpeed());
		SmartDashboard.putNumber("Back Right Velocity", rearRightWheel.getSpeed());

	}

	/**
	 * Start distance measurements from the robot's current position Records the
	 * current encoder values for comparison after driving
	 */
	public void resetDistance() {
		initialFrontLeftEncoderDistance = frontLeftWheel.getEncPosition();
		initialFrontRightEncoderDistance = frontRightWheel.getEncPosition();
		initialRearLeftEncoderDistance = rearLeftWheel.getEncPosition();
		initialRearRightEncoderDistance = rearRightWheel.getEncPosition();
	}

	/**
	 * Calculates the change in encoder reading since the last time
	 * resetDistance() was called. Always returns a positive number, so we get
	 * consistent results whether driving forward vs. backward or left vs.
	 * right.
	 */
	private double rearRightEncoderDistance() {
		return Math.abs(rearRightWheel.getEncPosition() - initialRearRightEncoderDistance);
	}

	private double frontRightEncoderDistance() {
		return Math.abs(frontRightWheel.getEncPosition() - initialFrontRightEncoderDistance);
	}

	private double frontLeftEncoderDistance() {
		return Math.abs(frontLeftWheel.getEncPosition() - initialFrontLeftEncoderDistance);
	}

	private double rearLeftEncoderDistance() {
		return Math.abs(rearLeftWheel.getEncPosition() - initialRearLeftEncoderDistance);
	}

	/**
	 * Returns the distance traveled in inches, assuming the robot is driving in
	 * the forward direction
	 */
	public double getDistanceForward() {
		return (rearLeftEncoderDistance() + frontRightEncoderDistance()) / 2 * inchesPerTickStrafing;
	}

	/**
	 * Returns the distance traveled in inches, assuming the robot is driving in
	 * the backward direction
	 */
	public double getDistanceBackwards() {
		return (rearLeftEncoderDistance() + frontRightEncoderDistance()) / 2 * inchesPerTickStrafing;
	}

	/**
	 * Returns the distance traveled in inches, assuming the robot is driving to
	 * the left
	 */
	public double getDistanceLeft() {
		// calculates the average of the encoder distances
		return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance()) / 4 * inchesPerTick);
	}

	/**
	 * Returns the distance traveled in inches, assuming the robot is driving to
	 * the right
	 */
	public double getDistanceRight() {
		// calculates the average of the encoder distances
		return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance()) / 4 * inchesPerTick);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveByJoystick());
		// setDefaultCommand(new TestWheels());
	}

}
