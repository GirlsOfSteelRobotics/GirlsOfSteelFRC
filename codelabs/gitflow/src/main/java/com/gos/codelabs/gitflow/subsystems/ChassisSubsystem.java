package com.gos.codelabs.gitflow.subsystems;


import org.wpilib.drive.DifferentialDrive;
import org.wpilib.hardware.motor.PWMMotorController;
import org.wpilib.drivers.motor.PWMTalonSRX;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.command2.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {

    private final PWMMotorController m_leftDriveA;
    private final PWMMotorController m_rightDriveA;

    private final DifferentialDrive m_differentialDrive;

    @SuppressWarnings("PMD.CloseResource")
    public ChassisSubsystem() {

        m_leftDriveA = new PWMTalonSRX(0);
        m_rightDriveA = new PWMTalonSRX(2);

        PWMMotorController leftDriveB = new PWMTalonSRX(1);
        PWMMotorController rightDriveB = new PWMTalonSRX(3);

        m_leftDriveA.addFollower(leftDriveB);
        m_rightDriveA.addFollower(rightDriveB);

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);
    }

    public void setSpeedAndSteer(double speed, double steer) {
        m_differentialDrive.arcadeDrive(speed, steer);
    }

    @Override
    public void periodic() {
        Telemetry.log("Left Drive", m_leftDriveA.getThrottle());
        Telemetry.log("Right Drive", m_rightDriveA.getThrottle());
    }
}
