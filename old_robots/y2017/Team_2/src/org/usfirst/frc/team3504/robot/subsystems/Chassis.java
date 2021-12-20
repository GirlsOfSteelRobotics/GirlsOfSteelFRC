package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;
/**
 *
 */
public class Chassis extends Subsystem {
    private final CANTalon m_driveLeftA;
    private final CANTalon m_driveLeftB;

    private final CANTalon m_driveRightA;
    private final CANTalon m_driveRightB;

    private final RobotDrive m_robotDrive;


    public Chassis(){
        m_driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
        m_driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);

        m_driveLeftB.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);


        m_robotDrive = new RobotDrive(m_driveLeftA, m_driveRightA);
        m_robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
        m_robotDrive.setInvertedMotor(MotorType.kRearRight, true);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
    }

    public void driveByJoystick(Joystick joystick) {
        m_robotDrive.arcadeDrive(joystick);
    }

    public void driveSpeed(double speed) {
        m_robotDrive.drive(-speed, 0);
    }

    public void stop() {
        // TODO Auto-generated method stub
        m_robotDrive.drive(/* speed */0, /* curve */0);
    }

    public double getRightEncoderPosition(){
        return m_driveRightA.getSelectedSensorPosition();
    }

    public double getLeftEncoderPosition(){
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public void ahrsToSmartDashboard(){
        getEncoderDistance();
    }

    public void getEncoderDistance(){ // NOPMD(LinguisticNaming)
        SmartDashboard.putNumber("Right Encoder", getRightEncoderPosition());
        SmartDashboard.putNumber("Left Encoder", getLeftEncoderPosition());
    }

}
