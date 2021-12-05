package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.drive.DriveByJoystick;

import com.kauailabs.navx.frc.AHRS;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
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
    private boolean getGyro;
    private double oldDirection;
    private final AHRS IMUGyro;
    private final double oldXGyroDisplacement = 0;
    private final double oldYGyroDisplacement = 0;


    // Variables used to reset the encoders
    private double initialFrontLeftEncoderDistance;
    private double initialFrontRightEncoderDistance;
    private double initialRearLeftEncoderDistance;
    private double initialRearRightEncoderDistance;

    private final double topSpeed = 400;

    private final RobotDrive gosDrive;

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

    private static final double MIN_SPEED = .5;
    private static final double MAX_SPEED = .75;
    private static final double RAMP_UP_DISTANCE = 5;
    private static final double RAMP_DOWN_DISTANCE = 40;

    private static final double MIN_SPEED_STRAFING = .3;
    private static final double MAX_SPEED_STRAFING = .7;

    public Chassis() {
        frontRightWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
        frontLeftWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
        rearRightWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
        rearLeftWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);

        frontRightWheel.setNeutralMode(NeutralMode.Brake);
        frontLeftWheel.setNeutralMode(NeutralMode.Brake);
        rearRightWheel.setNeutralMode(NeutralMode.Brake);
        rearLeftWheel.setNeutralMode(NeutralMode.Brake);

        frontRightWheel.changeControlMode(CANTalon.TalonControlMode.Speed);
        frontRightWheel.setPID(kP, kI, kD);
        frontRightWheel.reverseSensor(true);

        frontLeftWheel.changeControlMode(CANTalon.TalonControlMode.Speed);
        frontLeftWheel.setPID(kP, kI, kD);
        frontLeftWheel.reverseSensor(true);

        rearRightWheel.changeControlMode(CANTalon.TalonControlMode.Speed);
        rearRightWheel.setPID(kP, kI, kD);
        rearRightWheel.reverseSensor(true);

        rearLeftWheel.changeControlMode(CANTalon.TalonControlMode.Speed);
        rearLeftWheel.setPID(kP, kI, kD);
        rearLeftWheel.reverseSensor(true);

        getGyro = true;

        IMUGyro = new AHRS(Port.kMXP);

        IMUGyro.zeroYaw();

        gosDrive = new RobotDrive(rearLeftWheel, rearRightWheel, frontLeftWheel, frontRightWheel);

        gosDrive.setMaxOutput(topSpeed);

        // Invert the left side motors
        gosDrive.setInvertedMotor(MotorType.kRearRight, true);
        gosDrive.setInvertedMotor(MotorType.kFrontRight, true);
        gosDrive.setExpiration(0.1);
        gosDrive.setSafetyEnabled(false);
        SmartDashboard.putNumber("Front Left", 0.0);
        SmartDashboard.putNumber("Front Right", 0.0);
        SmartDashboard.putNumber("Rear Right", 0.0);
        SmartDashboard.putNumber("Rear Left", 0.0);
        SmartDashboard.putBoolean("Velocity?", true);
        SmartDashboard.putBoolean("Gyro: ", getGyro);

        SmartDashboard.putNumber("F val", 0);
    }

    public void spinWheelsSlowly() {
        SmartDashboard.putNumber("Bus Voltage", frontRightWheel.getBusVoltage());
        SmartDashboard.putNumber("Closed Loop Error", frontRightWheel.getClosedLoopError());

        frontRightWheel.setPID(SmartDashboard.getNumber("P value", 0),
        SmartDashboard.getNumber("I value", 0),
        SmartDashboard.getNumber("D value", 0),
        SmartDashboard.getNumber("F val", 0),
        0, 0, 0);
        printPositionsToSmartDashboard();

        frontLeftWheel.setPID(SmartDashboard.getNumber("P value", 0),
        SmartDashboard.getNumber("I value", 0),
        SmartDashboard.getNumber("D value", 0),
        SmartDashboard.getNumber("F val", 0),
        0, 0, 0);

        rearRightWheel.setPID(SmartDashboard.getNumber("P value", 0),
        SmartDashboard.getNumber("I value", 0),
        SmartDashboard.getNumber("D value", 0),
        SmartDashboard.getNumber("F val", 0),
        0, 0, 0);

        rearLeftWheel.setPID(SmartDashboard.getNumber("P value", 0),
        SmartDashboard.getNumber("I value", 0),
        SmartDashboard.getNumber("D value", 0),
        SmartDashboard.getNumber("F val", 0),
        0, 0, 0);

        frontLeftWheel.set((SmartDashboard.getNumber("Front Left", 0)) * 750);
        frontRightWheel.set((SmartDashboard.getNumber("Front Right", 0)) * 750);
        rearRightWheel.set((SmartDashboard.getNumber("Rear Right", 0)) * 750);
        rearLeftWheel.set(SmartDashboard.getNumber("Rear Left", 0) * 750);
    }

    /**
     * Improved deadband function that behaves consistently with positive and
     * negative inputs Uses the curve f(x) = x ^ (1/x) Type this into Google
     * Search to graph it: graph x^(1/x) Returns zero for values less than about
     * 0.25, then scales up to return 1 for an input of 1
     */
    private double beattieDeadBand(double x) {
        if (x > 0) {
            return Math.pow(x, 1.0 / x);
        }
        else if (x < 0) {
            return -beattieDeadBand(-x);
        }
        else {
            return 0;
        }
    }

    /**
     * Improved deadband function that behaves consistently with positive and
     * negative inputs Uses the curve f(x) = x ^ (4/x) Type this into Google
     * Search to graph it: graph x^(4/x) Returns zero for values less than about
     * 0.5, then scales up to return 1 for an input of 1
     */
    private double beattieTwistDeadBand(double x) {
        if (x > 0) {
            return Math.pow(x, 4.0 / x);
        }
        else if (x < 0) {
            return -beattieTwistDeadBand(-x);
        }
        else {
            return 0;
        }
    }

    /**
     * Translate throttle value from joystick (range -1..+1) into motor speed
     * (range 0..+1) The joystick value in the top position is -1, opposite of
     * what might be expected, so multiply by negative one to invert the
     * readings.
     */
    private double throttleSpeed(Joystick stick) {
        double temp = (-stick.getThrottle() + 1) / 2;
        if (temp < .1) {
            return .1;
        }

        else {
            return temp;
        }
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
    public void getGyro() { // NOPMD(LinguisticNaming)
        getGyro = !getGyro;
        SmartDashboard.putBoolean("Gyro: ", getGyro);
    }

    public void moveByJoystick(Joystick stick) {
        double temp = IMUGyro.getYaw();
        if (temp < 0) {
            temp = temp + 360;
        }

        SmartDashboard.putNumber("GYRO Get Yaw", temp);
        SmartDashboard.putNumber("Throttle Speeddddd", throttleSpeed(stick));
        SmartDashboard.putNumber("FUSED HEADING!!!!!", IMUGyro.getFusedHeading());
        SmartDashboard.putNumber("Closed Loop Error", frontRightWheel.getClosedLoopError());

        gosDrive.mecanumDrive_Cartesian(beattieDeadBand(-stick.getY()) * throttleSpeed(stick),
                beattieDeadBand(stick.getX()) * throttleSpeed(stick),
                (beattieTwistDeadBand(stick.getTwist())) * throttleSpeed(stick),
                getGyro ? temp : 0);

        SmartDashboard.putNumber("Sending Val Front Left", frontLeftWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Rear Left", rearLeftWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Front Right", frontRightWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Rear Right", rearRightWheel.getSetpoint());

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
        // figure out what the angle should be
        gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceRight()), 180, 0);
    }

    public void autoDriveLeft(double goalDist) {
        gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceLeft()), 0, 0);
    }

    public void autoDriveBackward(double goalDist) {
        // check to make sure this angle is correct
        gosDrive.mecanumDrive_Polar(calculateSpeedStrafing(goalDist, getDistanceBackwards()), 270, 0);
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
    //	SmartDashboard.putNumber("Front Left Velocity", frontLeftWheel.getEncPosition());//frontLeftWheel.getSpeed());
    //	SmartDashboard.putNumber("Front Right Velocity", frontRightWheel.getEncPosition());//frontRightWheel.getSpeed());
    //	SmartDashboard.putNumber("Back Left Velocity", rearLeftWheel.getEncPosition());//rearLeftWheel.getSpeed());
    //	SmartDashboard.putNumber("Back Right Velocity", rearRightWheel.getEncPosition());//rearRightWheel.getSpeed());

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

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveByJoystick());
    }
}
