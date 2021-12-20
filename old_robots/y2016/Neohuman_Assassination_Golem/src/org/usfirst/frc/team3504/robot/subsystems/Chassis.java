package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Chassis extends Subsystem implements PIDOutput{
    private final CANTalon m_driveLeftA;
    private final CANTalon m_driveLeftB;
    private final CANTalon m_driveLeftC;

    private final CANTalon m_driveRightA;
    private final CANTalon m_driveRightB;
    private final CANTalon m_driveRightC;

    private final RobotDrive m_robotDrive;

    private double m_encOffsetValueRight;
    private double m_encOffsetValueLeft;

    private double m_rotateToAngleRate;
    private final Shifters m_shifters;

    public Chassis(Shifters shifters) {
        m_shifters = shifters;
        m_driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
        m_driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C);
        m_driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);
        m_driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveLeftC.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);
        m_driveRightC.setNeutralMode(NeutralMode.Brake);

        m_robotDrive = new RobotDrive(m_driveLeftA, m_driveRightA);

        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);

        m_driveLeftB.changeControlMode(ControlMode.Follower);
        m_driveLeftC.changeControlMode(ControlMode.Follower);
        m_driveRightB.changeControlMode(ControlMode.Follower);
        m_driveRightC.changeControlMode(ControlMode.Follower);
        m_driveLeftB.set(m_driveLeftA.getDeviceID());
        m_driveLeftC.set(m_driveLeftA.getDeviceID());
        m_driveRightB.set(m_driveRightA.getDeviceID());
        m_driveRightC.set(m_driveRightA.getDeviceID());


        addChild("driveLeftA", m_driveLeftA);
        addChild("driveRightA", m_driveRightA);

        //for the NavBoards
/**
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

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }

    public void driveByJoystick(double y, double x) {
        SmartDashboard.putString("driveByJoystick?", y + "," + x);
        m_robotDrive.arcadeDrive(y, x);
    }

    public void drive(double moveValue, double rotateValue){
        m_robotDrive.arcadeDrive(moveValue, rotateValue);
    }

    public void driveSpeed(double speed){
        m_robotDrive.drive(-speed, 0);
    }

    public void stop() {
        m_robotDrive.drive(0, 0);
    }


    public void printEncoderValues() {
        getEncoderDistance();
    }

    public double getEncoderRight() {
        return -m_driveRightA.getEncPosition();
    }

    public double getEncoderLeft() {
        return m_driveLeftA.getEncPosition();
    }

    public double getEncoderDistance() {
        if (m_shifters.getGearSpeed()) {
            SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - m_encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            return (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
        }
        else {
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

    @Override
    public void pidWrite(double output) {
        m_rotateToAngleRate = output;
    }


    public void ahrsToSmartDashboard() {
    /*	SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
        SmartDashboard.putNumber(	"IMU_RotateToAngleRate",	rotateToAngleRate);
        SmartDashboard.putNumber("IMU_X_Displacement", ahrs.getDisplacementX());
        SmartDashboard.putNumber("IMU_Y_Displacement", ahrs.getDisplacementY());
        SmartDashboard.putNumber("IMU_Z_Displacement", ahrs.getDisplacementZ());
*/
        getEncoderDistance();
    }
}
