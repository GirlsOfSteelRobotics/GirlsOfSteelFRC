package com.gos.recycle_rush.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.recycle_rush.robot.RobotMap;


@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public class Chassis extends SubsystemBase {

    // PID Constants
    private static final double KP = 1.0;
    private static final double KI = 0.01;
    private static final double KD = 20;

    // Encoder to Distance Constants
    private static final double INCHES_PER_TICK = Math.PI * 6 / (4 * 256);
    private static final double INCHES_PER_TICK_STRAFING = INCHES_PER_TICK / 2;

    // Speed constant for autonomous driving (
    // (may need to change for strafing vs. normal driving)
    private static final double AUTO_SPEED = 0.25;

    private static final double MIN_SPEED = .5;
    private static final double MAX_SPEED = .75;
    private static final double RAMP_UP_DISTANCE = 5;
    private static final double RAMP_DOWN_DISTANCE = 40;

    private static final double MIN_SPEED_STRAFING = .3;
    private static final double MAX_SPEED_STRAFING = .7;

    private static final double TOP_SPEED = 400;

    // WPI_TalonSRXs
    private final WPI_TalonSRX m_frontRightWheel;
    private final WPI_TalonSRX m_frontLeftWheel;
    private final WPI_TalonSRX m_rearRightWheel;
    private final WPI_TalonSRX m_rearLeftWheel;

    // Gyro
    private boolean m_getGyro;
    private final AHRS m_gyro;


    // Variables used to reset the encoders
    private double m_initialFrontLeftEncoderDistance;
    private double m_initialFrontRightEncoderDistance;
    private double m_initialRearLeftEncoderDistance;
    private double m_initialRearRightEncoderDistance;

    private final MecanumDrive m_gosDrive;

    public Chassis() {
        m_frontRightWheel = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
        m_frontLeftWheel = new WPI_TalonSRX(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
        m_rearRightWheel = new WPI_TalonSRX(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
        m_rearLeftWheel = new WPI_TalonSRX(RobotMap.REAR_LEFT_WHEEL_CHANNEL);

        m_frontRightWheel.setNeutralMode(NeutralMode.Brake);
        m_frontLeftWheel.setNeutralMode(NeutralMode.Brake);
        m_rearRightWheel.setNeutralMode(NeutralMode.Brake);
        m_rearLeftWheel.setNeutralMode(NeutralMode.Brake);

        m_frontRightWheel.config_kP(0, KP);
        m_frontRightWheel.config_kI(0, KI);
        m_frontRightWheel.config_kD(0, KD);
        m_frontRightWheel.setSensorPhase(false);

        m_frontLeftWheel.config_kP(0, KP);
        m_frontLeftWheel.config_kI(0, KI);
        m_frontLeftWheel.config_kD(0, KD);
        m_frontLeftWheel.setSensorPhase(false);

        m_rearRightWheel.config_kP(0, KP);
        m_rearRightWheel.config_kI(0, KI);
        m_rearRightWheel.config_kD(0, KD);
        m_rearRightWheel.setSensorPhase(false);

        m_rearLeftWheel.config_kP(0, KP);
        m_rearLeftWheel.config_kI(0, KI);
        m_rearLeftWheel.config_kD(0, KD);
        m_rearLeftWheel.setSensorPhase(false);

        m_getGyro = true;

        m_gyro = new AHRS(Port.kMXP);

        m_gyro.zeroYaw();

        m_gosDrive = new MecanumDrive(m_rearLeftWheel, m_rearRightWheel, m_frontLeftWheel, m_frontRightWheel);

        m_gosDrive.setMaxOutput(TOP_SPEED);

        // Invert the left side motors


        m_gosDrive.setExpiration(0.1);
        m_gosDrive.setSafetyEnabled(false);
        SmartDashboard.putNumber("Front Left", 0.0);
        SmartDashboard.putNumber("Front Right", 0.0);
        SmartDashboard.putNumber("Rear Right", 0.0);
        SmartDashboard.putNumber("Rear Left", 0.0);
        SmartDashboard.putBoolean("Velocity?", true);
        SmartDashboard.putBoolean("Gyro: ", m_getGyro);

        SmartDashboard.putNumber("F val", 0);
    }

    public void spinWheelsSlowly() {
        SmartDashboard.putNumber("Bus Voltage", m_frontRightWheel.getBusVoltage());
        SmartDashboard.putNumber("Closed Loop Error", m_frontRightWheel.getClosedLoopError());

        m_frontRightWheel.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_frontRightWheel.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_frontRightWheel.config_kD(0, SmartDashboard.getNumber("D value", 0));
        m_frontRightWheel.config_kF(0, SmartDashboard.getNumber("F val", 0));
        printPositionsToSmartDashboard();

        m_frontRightWheel.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_frontRightWheel.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_frontRightWheel.config_kD(0, SmartDashboard.getNumber("D value", 0));
        m_frontRightWheel.config_kF(0, SmartDashboard.getNumber("F val", 0));

        m_rearRightWheel.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_rearRightWheel.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_rearRightWheel.config_kD(0, SmartDashboard.getNumber("D value", 0));
        m_rearRightWheel.config_kF(0, SmartDashboard.getNumber("F val", 0));

        m_rearLeftWheel.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_rearLeftWheel.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_rearLeftWheel.config_kD(0, SmartDashboard.getNumber("D value", 0));
        m_rearLeftWheel.config_kF(0, SmartDashboard.getNumber("F val", 0));

        m_frontLeftWheel.set(ControlMode.Velocity, (SmartDashboard.getNumber("Front Left", 0)) * 750);
        m_frontRightWheel.set(ControlMode.Velocity, (SmartDashboard.getNumber("Front Right", 0)) * 750);
        m_rearRightWheel.set(ControlMode.Velocity, (SmartDashboard.getNumber("Rear Right", 0)) * 750);
        m_rearLeftWheel.set(ControlMode.Velocity, SmartDashboard.getNumber("Rear Left", 0) * 750);
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
        } else if (x < 0) {
            return -beattieDeadBand(-x);
        } else {
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
        } else if (x < 0) {
            return -beattieTwistDeadBand(-x);
        } else {
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
        } else {
            return temp;
        }
    }

    public double getGyroAngle() {
        return m_gyro.getYaw();
    }

    public void resetGyro() {
        m_gyro.zeroYaw();
    }

    /**
     * This method toggles whether the driving by joystick is relative to the
     * robot or the field It enables or disables the use of the current gyro
     * reading in mecanumDrive_Cartesian()
     */
    public void getGyro() { // NOPMD(LinguisticNaming)
        m_getGyro = !m_getGyro;
        SmartDashboard.putBoolean("Gyro: ", m_getGyro);
    }

    public void moveByJoystick(Joystick stick) {
        double temp = m_gyro.getYaw();
        if (temp < 0) {
            temp = temp + 360;
        }

        SmartDashboard.putNumber("GYRO Get Yaw", temp);
        SmartDashboard.putNumber("Throttle Speeddddd", throttleSpeed(stick));
        SmartDashboard.putNumber("FUSED HEADING!!!!!", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Closed Loop Error", m_frontRightWheel.getClosedLoopError());

        m_gosDrive.driveCartesian(beattieDeadBand(-stick.getY()) * throttleSpeed(stick),
            beattieDeadBand(stick.getX()) * throttleSpeed(stick),
            (beattieTwistDeadBand(stick.getTwist())) * throttleSpeed(stick),
            Rotation2d.fromDegrees(m_getGyro ? temp : 0));

        SmartDashboard.putNumber("Sending Val Front Left", m_frontLeftWheel.getClosedLoopTarget());
        SmartDashboard.putNumber("Sending Val Rear Left", m_rearLeftWheel.getClosedLoopTarget());
        SmartDashboard.putNumber("Sending Val Front Right", m_frontRightWheel.getClosedLoopTarget());
        SmartDashboard.putNumber("Sending Val Rear Right", m_rearRightWheel.getClosedLoopTarget());

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
        m_gosDrive.drivePolar(calculateSpeed(goalDist, getDistanceRight()), Rotation2d.fromDegrees(180), 0);
    }

    public void autoDriveLeft(double goalDist) {
        m_gosDrive.drivePolar(calculateSpeed(goalDist, getDistanceLeft()), Rotation2d.fromDegrees(0), 0);
    }

    public void autoDriveBackward(double goalDist) {
        // check to make sure this angle is correct
        m_gosDrive.drivePolar(calculateSpeedStrafing(goalDist, getDistanceBackwards()), Rotation2d.fromDegrees(270), 0);
    }

    public void autoDriveForward(double goalDist) {
        m_gosDrive.drivePolar(calculateSpeedStrafing(goalDist, getDistanceForward()), Rotation2d.fromDegrees(90), 0);
    }

    public void autoTurnClockwise() {
        m_gosDrive.driveCartesian(0, 0, AUTO_SPEED, Rotation2d.fromDegrees(0));
    }

    public void autoTurnCounterclockwise() {
        m_gosDrive.driveCartesian(0, 0, -AUTO_SPEED, Rotation2d.fromDegrees(0));
    }

    public void driveForward() {
        m_gosDrive.drivePolar(1, Rotation2d.fromDegrees(90), 0);
    }

    public void driveBackward(Joystick chassisJoystick) {
        m_gosDrive.driveCartesian(0, -throttleSpeed(chassisJoystick), 0, Rotation2d.fromDegrees(0));
    }

    public void driveRight(Joystick chassisJoystick) {
        m_gosDrive.driveCartesian(-throttleSpeed(chassisJoystick), 0, 0, Rotation2d.fromDegrees(0));
    }

    public void driveLeft(Joystick chassisJoystick) {
        m_gosDrive.driveCartesian(throttleSpeed(chassisJoystick), 0, 0, Rotation2d.fromDegrees(0));
    }

    public void stop() {
        m_gosDrive.stopMotor();
    }

    public void printPositionsToSmartDashboard() {
        //    SmartDashboard.putNumber("Front Left Velocity", frontLeftWheel.getEncPosition()); //frontLeftWheel.getSpeed());
        //    SmartDashboard.putNumber("Front Right Velocity", frontRightWheel.getEncPosition()); //frontRightWheel.getSpeed());
        //    SmartDashboard.putNumber("Back Left Velocity", rearLeftWheel.getEncPosition()); //rearLeftWheel.getSpeed());
        //    SmartDashboard.putNumber("Back Right Velocity", rearRightWheel.getEncPosition()); //rearRightWheel.getSpeed());

        SmartDashboard.putNumber("Front Left Velocity", m_frontLeftWheel.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Front Right Velocity", m_frontRightWheel.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Back Left Velocity", m_rearLeftWheel.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Back Right Velocity", m_rearRightWheel.getSelectedSensorVelocity());
    }

    /**
     * Start distance measurements from the robot's current position Records the
     * current encoder values for comparison after driving
     */
    public void resetDistance() {
        m_initialFrontLeftEncoderDistance = m_frontLeftWheel.getSelectedSensorPosition();
        m_initialFrontRightEncoderDistance = m_frontRightWheel.getSelectedSensorPosition();
        m_initialRearLeftEncoderDistance = m_rearLeftWheel.getSelectedSensorPosition();
        m_initialRearRightEncoderDistance = m_rearRightWheel.getSelectedSensorPosition();
    }

    /**
     * Calculates the change in encoder reading since the last time
     * resetDistance() was called. Always returns a positive number, so we get
     * consistent results whether driving forward vs. backward or left vs.
     * right.
     */
    private double rearRightEncoderDistance() {
        return Math.abs(m_rearRightWheel.getSelectedSensorPosition() - m_initialRearRightEncoderDistance);
    }

    private double frontRightEncoderDistance() {
        return Math.abs(m_frontRightWheel.getSelectedSensorPosition() - m_initialFrontRightEncoderDistance);
    }

    private double frontLeftEncoderDistance() {
        return Math.abs(m_frontLeftWheel.getSelectedSensorPosition() - m_initialFrontLeftEncoderDistance);
    }

    private double rearLeftEncoderDistance() {
        return Math.abs(m_rearLeftWheel.getSelectedSensorPosition() - m_initialRearLeftEncoderDistance);
    }

    /**
     * Returns the distance traveled in inches, assuming the robot is driving in
     * the forward direction
     */
    public double getDistanceForward() {
        return (rearLeftEncoderDistance() + frontRightEncoderDistance()) / 2 * INCHES_PER_TICK_STRAFING;
    }

    /**
     * Returns the distance traveled in inches, assuming the robot is driving in
     * the backward direction
     */
    public double getDistanceBackwards() {
        return (rearLeftEncoderDistance() + frontRightEncoderDistance()) / 2 * INCHES_PER_TICK_STRAFING;
    }

    /**
     * Returns the distance traveled in inches, assuming the robot is driving to
     * the left
     */
    public double getDistanceLeft() {
        // calculates the average of the encoder distances
        return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance()) / 4 * INCHES_PER_TICK);
    }

    /**
     * Returns the distance traveled in inches, assuming the robot is driving to
     * the right
     */
    public double getDistanceRight() {
        // calculates the average of the encoder distances
        return ((rearLeftEncoderDistance() + frontLeftEncoderDistance() + frontRightEncoderDistance() + rearRightEncoderDistance()) / 4 * INCHES_PER_TICK);
    }


}
