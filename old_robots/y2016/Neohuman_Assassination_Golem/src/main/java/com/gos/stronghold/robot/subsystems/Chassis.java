package com.gos.stronghold.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.RobotMap;

/**
 *
 */
public class Chassis extends SubsystemBase {
    public enum TeleDriveDirection { FWD, REV }

    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;
    private final WPI_TalonSRX m_driveLeftC;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;
    private final WPI_TalonSRX m_driveRightC;

    private final DifferentialDrive m_robotDrive;

    private double m_encOffsetValueRight;
    private double m_encOffsetValueLeft;

    private double m_rotateToAngleRate;
    private final Shifters m_shifters;

    private TeleDriveDirection m_driveDirection = TeleDriveDirection.FWD;

    public Chassis(Shifters shifters) {
        m_shifters = shifters;
        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
        m_driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C);
        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
        m_driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveLeftC.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);
        m_driveRightC.setNeutralMode(NeutralMode.Brake);

        m_robotDrive = new DifferentialDrive(m_driveLeftA, m_driveRightA);

        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setMaxOutput(1.0);

        m_driveLeftB.follow(m_driveLeftA);
        m_driveLeftC.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);
        m_driveRightC.follow(m_driveRightA);


        addChild("driveLeftA", m_driveLeftA);
        addChild("driveRightA", m_driveRightA);

        //for the NavBoards
        /*
         try {
         <<<<<<< HEAD
         /* Communicate w/navX MXP via the MXP SPI Bus.
         /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB
         /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details.
         ahrs = new AHRS(SPI.Port.kMXP);
         } catch (RuntimeException ex ) {
         DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
         }
         //turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
         /*turnController.setInputRange(-180.0f,  180.0f);
         turnController.setOutputRange(-1.0, 1.0);
         turnController.setAbsoluteTolerance(kToleranceDegrees);
         turnController.setContinuous(true);
         turnController.enable();
         **/

    }

    public void setTeleDriveDirection(TeleDriveDirection driveDirection) {
        this.m_driveDirection = driveDirection;
        System.out.println("Drive direction set to: " + driveDirection);
    }

    public TeleDriveDirection getTeleDrivingDirection() {
        return m_driveDirection;
    }


    public void driveByJoystick(double y, double x) {
        SmartDashboard.putString("driveByJoystick?", y + "," + x);
        m_robotDrive.arcadeDrive(y, x);
    }

    public void drive(double moveValue, double rotateValue) {
        m_robotDrive.arcadeDrive(moveValue, rotateValue);
    }

    public void driveSpeed(double speed) {
        m_robotDrive.arcadeDrive(-speed, 0);
    }

    public void stop() {
        m_robotDrive.arcadeDrive(0, 0);
    }


    public void printEncoderValues() {
        getEncoderDistance();
    }

    public double getEncoderRight() {
        return -m_driveRightA.getSelectedSensorPosition();
    }

    public double getEncoderLeft() {
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public double getEncoderDistance() {
        if (m_shifters.getGearSpeed()) {
            SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - m_encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            return (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
        } else {
            SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
            SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - m_encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
            return (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR;
        }
    }

    public void resetEncoderDistance() {
        m_encOffsetValueRight = getEncoderRight();
        m_encOffsetValueLeft = getEncoderLeft();
        //ahrs.resetDisplacement();
        getEncoderDistance();
    }

    public double getRotationAngleRate() {
        return m_rotateToAngleRate;
    }

    /*public double getGyroAngle() {
        return ahrs.getYaw();
    }

    public void resetGyro() {
        ahrs.zeroYaw();
    }*/


    public void ahrsToSmartDashboard() {
        /*    SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
            SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
            SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
            SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
            SmartDashboard.putNumber(    "IMU_RotateToAngleRate",    rotateToAngleRate);
            SmartDashboard.putNumber("IMU_X_Displacement", ahrs.getDisplacementX());
            SmartDashboard.putNumber("IMU_Y_Displacement", ahrs.getDisplacementY());
            SmartDashboard.putNumber("IMU_Z_Displacement", ahrs.getDisplacementZ());
    */
        getEncoderDistance();
    }
}
