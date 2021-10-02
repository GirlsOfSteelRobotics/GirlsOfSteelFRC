package frc.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {

    private final SpeedController m_leftDriveA;
    private final SpeedController m_rightDriveA;

    private final DifferentialDrive m_differentialDrive;

    @SuppressWarnings("PMD.CloseResource")
    public ChassisSubsystem() {

        m_leftDriveA = new Talon(0);
        m_rightDriveA = new Talon(2);

        SpeedController leftDriveB = new Talon(1);
        SpeedController rightDriveB = new Talon(3);

        m_differentialDrive = new DifferentialDrive(new SpeedControllerGroup(m_leftDriveA, leftDriveB),
                new SpeedControllerGroup(m_rightDriveA, rightDriveB));
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

