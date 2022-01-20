package com.gos.codelabs.basic_simulator.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.codelabs.basic_simulator.Constants;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.module_wrappers.wpi.ADXRS450GyroWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

public class ChassisSubsystem extends SubsystemBase {

    private final SimableCANSparkMax m_leftDriveA;
    private final SimableCANSparkMax m_rightDriveA;

    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;

    private final DifferentialDrive m_differentialDrive;
    private final DifferentialDriveOdometry m_odometry;
    private final Field2d m_field;

    private final ADXRS450_Gyro m_gyro;

    private DifferentialDrivetrainSimWrapper m_simulator;

    public ChassisSubsystem() {

        m_leftDriveA = new SimableCANSparkMax(Constants.CAN_CHASSIS_LEFT_A, CANSparkMaxLowLevel.MotorType.kBrushed);
        CANSparkMax leftDriveB = new CANSparkMax(Constants.CAN_CHASSIS_LEFT_B, CANSparkMaxLowLevel.MotorType.kBrushed);
        leftDriveB.follow(m_leftDriveA);

        m_rightDriveA = new SimableCANSparkMax(Constants.CAN_CHASSIS_RIGHT_A, CANSparkMaxLowLevel.MotorType.kBrushed);
        CANSparkMax rightDriveB = new CANSparkMax(Constants.CAN_CHASSIS_RIGHT_B, CANSparkMaxLowLevel.MotorType.kBrushed);
        rightDriveB.follow(m_rightDriveA);

        m_leftEncoder = m_leftDriveA.getEncoder();
        m_rightEncoder = m_rightDriveA.getEncoder();

        m_gyro = new ADXRS450_Gyro();

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d());
        m_field = new Field2d();
        SmartDashboard.putData(m_field);

        if (RobotBase.isSimulation()) {
            m_simulator = new DifferentialDrivetrainSimWrapper(
                    Constants.DrivetrainConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_leftDriveA),
                    new RevMotorControllerSimWrapper(m_rightDriveA),
                    RevEncoderSimWrapper.create(m_leftDriveA),
                    RevEncoderSimWrapper.create(m_rightDriveA),
                    new ADXRS450GyroWrapper(m_gyro));
        }
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
        // TODO implement
        return 0;
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
