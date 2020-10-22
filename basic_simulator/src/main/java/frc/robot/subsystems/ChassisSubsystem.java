package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ChassisSubsystem extends SubsystemBase {

    private final CANSparkMax m_leftDriveA;
    private final CANSparkMax m_rightDriveA;

    private final CANEncoder m_leftEncoder;
    private final CANEncoder m_rightEncoder;

    private final DifferentialDrive m_differentialDrive;

    private final PigeonIMU m_gyro;

    public ChassisSubsystem() {

        m_leftDriveA = new CANSparkMax(Constants.CAN_CHASSIS_LEFT_A, CANSparkMaxLowLevel.MotorType.kBrushed);
        CANSparkMax leftDriveB = new CANSparkMax(Constants.CAN_CHASSIS_LEFT_B, CANSparkMaxLowLevel.MotorType.kBrushed);
        leftDriveB.follow(m_leftDriveA);

        m_rightDriveA = new CANSparkMax(Constants.CAN_CHASSIS_RIGHT_A, CANSparkMaxLowLevel.MotorType.kBrushed);
        CANSparkMax rightDriveB = new CANSparkMax(Constants.CAN_CHASSIS_RIGHT_B, CANSparkMaxLowLevel.MotorType.kBrushed);
        rightDriveB.follow(m_rightDriveA);

        m_leftEncoder = m_leftDriveA.getEncoder();
        m_rightEncoder = m_rightDriveA.getEncoder();

        m_gyro = new PigeonIMU(Constants.CAN_PIGEON);

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);
    }

    public void setThrottle(double speed) {
        // TODO implement
    }

    public void setSpin(double turningSpeed) {
        // TODO implement
    }

    public void setSpeedAndSteer(double speed, double steer) {
        // TODO implement
    }

    @Override
    public void periodic() {
        // TODO implement
    }

    public double getHeading() {
        double[] angles = new double[3];
        m_gyro.getYawPitchRoll(angles);
        return angles[0];
    }

    public double getLeftDistance() {
        return m_leftEncoder.getPosition();
    }

    public double getRightDistance() {
        return m_rightEncoder.getPosition();
    }

    public double getAverageDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }
}
