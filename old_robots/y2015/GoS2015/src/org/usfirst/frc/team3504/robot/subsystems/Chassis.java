package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;


@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public class Chassis extends Subsystem {

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

    private static final double topSpeed = 400;

    // CANTalons
    private final CANTalon m_frontRightWheel;
    private final CANTalon m_frontLeftWheel;
    private final CANTalon m_rearRightWheel;
    private final CANTalon m_rearLeftWheel;

    // Gyro
    private boolean m_getGyro;
    private final AHRS m_gyro;


    // Variables used to reset the encoders
    private double m_initialFrontLeftEncoderDistance;
    private double m_initialFrontRightEncoderDistance;
    private double m_initialRearLeftEncoderDistance;
    private double m_initialRearRightEncoderDistance;

    private final RobotDrive m_gosDrive;

    public Chassis() {
        m_frontRightWheel = new CANTalon(RobotMap.FRONT_RIGHT_WHEEL_CHANNEL);
        m_frontLeftWheel = new CANTalon(RobotMap.FRONT_LEFT_WHEEL_CHANNEL);
        m_rearRightWheel = new CANTalon(RobotMap.REAR_RIGHT_WHEEL_CHANNEL);
        m_rearLeftWheel = new CANTalon(RobotMap.REAR_LEFT_WHEEL_CHANNEL);

        m_frontRightWheel.setNeutralMode(NeutralMode.Brake);
        m_frontLeftWheel.setNeutralMode(NeutralMode.Brake);
        m_rearRightWheel.setNeutralMode(NeutralMode.Brake);
        m_rearLeftWheel.setNeutralMode(NeutralMode.Brake);

        m_frontRightWheel.changeControlMode(ControlMode.Velocity);
        m_frontRightWheel.config_kP(0, kP);
        m_frontRightWheel.config_kI(0, kI);
        m_frontRightWheel.config_kD(0, kD);
        m_frontRightWheel.reverseSensor(true);

        m_frontLeftWheel.changeControlMode(ControlMode.Velocity);
        m_frontLeftWheel.config_kP(0, kP);
        m_frontLeftWheel.config_kI(0, kI);
        m_frontLeftWheel.config_kD(0, kD);
        m_frontLeftWheel.reverseSensor(true);

        m_rearRightWheel.changeControlMode(ControlMode.Velocity);
        m_rearRightWheel.config_kP(0, kP);
        m_rearRightWheel.config_kI(0, kI);
        m_rearRightWheel.config_kD(0, kD);
        m_rearRightWheel.reverseSensor(true);

        m_rearLeftWheel.changeControlMode(ControlMode.Velocity);
        m_rearLeftWheel.config_kP(0, kP);
        m_rearLeftWheel.config_kI(0, kI);
        m_rearLeftWheel.config_kD(0, kD);
        m_rearLeftWheel.reverseSensor(true);

        m_getGyro = true;

        m_gyro = new AHRS(Port.kMXP);

        m_gyro.zeroYaw();

        m_gosDrive = new RobotDrive(m_rearLeftWheel, m_rearRightWheel, m_frontLeftWheel, m_frontRightWheel);

        m_gosDrive.setMaxOutput(topSpeed);

        // Invert the left side motors
        m_gosDrive.setInvertedMotor(MotorType.kRearRight, true);
        m_gosDrive.setInvertedMotor(MotorType.kFrontRight, true);
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

        m_frontLeftWheel.set((SmartDashboard.getNumber("Front Left", 0)) * 750);
        m_frontRightWheel.set((SmartDashboard.getNumber("Front Right", 0)) * 750);
        m_rearRightWheel.set((SmartDashboard.getNumber("Rear Right", 0)) * 750);
        m_rearLeftWheel.set(SmartDashboard.getNumber("Rear Left", 0) * 750);
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

        m_gosDrive.mecanumDrive_Cartesian(beattieDeadBand(-stick.getY()) * throttleSpeed(stick),
                beattieDeadBand(stick.getX()) * throttleSpeed(stick),
                (beattieTwistDeadBand(stick.getTwist())) * throttleSpeed(stick),
                m_getGyro ? temp : 0);

        SmartDashboard.putNumber("Sending Val Front Left", m_frontLeftWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Rear Left", m_rearLeftWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Front Right", m_frontRightWheel.getSetpoint());
        SmartDashboard.putNumber("Sending Val Rear Right", m_rearRightWheel.getSetpoint());

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
        m_gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceRight()), 180, 0);
    }

    public void autoDriveLeft(double goalDist) {
        m_gosDrive.mecanumDrive_Polar(calculateSpeed(goalDist, getDistanceLeft()), 0, 0);
    }

    public void autoDriveBackward(double goalDist) {
        // check to make sure this angle is correct
        m_gosDrive.mecanumDrive_Polar(calculateSpeedStrafing(goalDist, getDistanceBackwards()), 270, 0);
    }

    public void autoDriveForward(double goalDist) {
        m_gosDrive.mecanumDrive_Polar(calculateSpeedStrafing(goalDist, getDistanceForward()), 90, 0);
    }

    public void autoTurnClockwise() {
        m_gosDrive.mecanumDrive_Cartesian(0, 0, autoSpeed, 0);
    }

    public void autoTurnCounterclockwise() {
        m_gosDrive.mecanumDrive_Cartesian(0, 0, -autoSpeed, 0);
    }

    public void driveForward() {
        m_gosDrive.mecanumDrive_Polar(1, 90, 0);
    }

    public void driveBackward(Joystick chassisJoystick) {
        m_gosDrive.mecanumDrive_Cartesian(0, -throttleSpeed(chassisJoystick), 0, 0);
    }

    public void driveRight(Joystick chassisJoystick) {
        m_gosDrive.mecanumDrive_Cartesian(-throttleSpeed(chassisJoystick), 0, 0, 0);
    }

    public void driveLeft(Joystick chassisJoystick) {
        m_gosDrive.mecanumDrive_Cartesian(throttleSpeed(chassisJoystick), 0, 0, 0);
    }

    public void stop() {
        m_gosDrive.stopMotor();
    }

    public void printPositionsToSmartDashboard() {
    //	SmartDashboard.putNumber("Front Left Velocity", frontLeftWheel.getEncPosition());//frontLeftWheel.getSpeed());
    //	SmartDashboard.putNumber("Front Right Velocity", frontRightWheel.getEncPosition());//frontRightWheel.getSpeed());
    //	SmartDashboard.putNumber("Back Left Velocity", rearLeftWheel.getEncPosition());//rearLeftWheel.getSpeed());
    //	SmartDashboard.putNumber("Back Right Velocity", rearRightWheel.getEncPosition());//rearRightWheel.getSpeed());

        SmartDashboard.putNumber("Front Left Velocity", m_frontLeftWheel.getSpeed());
        SmartDashboard.putNumber("Front Right Velocity", m_frontRightWheel.getSpeed());
        SmartDashboard.putNumber("Back Left Velocity", m_rearLeftWheel.getSpeed());
        SmartDashboard.putNumber("Back Right Velocity", m_rearRightWheel.getSpeed());
    }

    /**
     * Start distance measurements from the robot's current position Records the
     * current encoder values for comparison after driving
     */
    public void resetDistance() {
        m_initialFrontLeftEncoderDistance = m_frontLeftWheel.getEncPosition();
        m_initialFrontRightEncoderDistance = m_frontRightWheel.getEncPosition();
        m_initialRearLeftEncoderDistance = m_rearLeftWheel.getEncPosition();
        m_initialRearRightEncoderDistance = m_rearRightWheel.getEncPosition();
    }

    /**
     * Calculates the change in encoder reading since the last time
     * resetDistance() was called. Always returns a positive number, so we get
     * consistent results whether driving forward vs. backward or left vs.
     * right.
     */
    private double rearRightEncoderDistance() {
        return Math.abs(m_rearRightWheel.getEncPosition() - m_initialRearRightEncoderDistance);
    }

    private double frontRightEncoderDistance() {
        return Math.abs(m_frontRightWheel.getEncPosition() - m_initialFrontRightEncoderDistance);
    }

    private double frontLeftEncoderDistance() {
        return Math.abs(m_frontLeftWheel.getEncPosition() - m_initialFrontLeftEncoderDistance);
    }

    private double rearLeftEncoderDistance() {
        return Math.abs(m_rearLeftWheel.getEncPosition() - m_initialRearLeftEncoderDistance);
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
    }
}
