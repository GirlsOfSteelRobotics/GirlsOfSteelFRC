package com.gos.codelabs.gitflow.subsystems;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {

    private final PWMMotorController m_leftDriveA;
    private final PWMMotorController m_rightDriveA;

    private final DifferentialDrive m_differentialDrive;

    @SuppressWarnings("PMD.CloseResource")
    public ChassisSubsystem() {

        m_leftDriveA = new Talon(0);
        m_rightDriveA = new Talon(2);

        PWMMotorController leftDriveB = new Talon(1);
        PWMMotorController rightDriveB = new Talon(3);

        m_leftDriveA.addFollower(leftDriveB);
        m_rightDriveA.addFollower(rightDriveB);

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);
    }

    public void setSpeedAndSteer(double speed, double steer) {
        m_differentialDrive.arcadeDrive(speed, steer);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Drive", m_leftDriveA.get());
        SmartDashboard.putNumber("Right Drive", m_rightDriveA.get());
    }
}
