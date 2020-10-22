package frc.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {

    private final SpeedController m_leftDriveA;
    private final SpeedController m_leftDriveB;
    private final SpeedController m_rightDriveA;
    private final SpeedController m_rightDriveB;

    private final DifferentialDrive m_differntialDrive;

    public ChassisSubsystem() {

        m_leftDriveA = new Talon(0);
        m_leftDriveB = new Talon(1);
        m_rightDriveA = new Talon(2);
        m_rightDriveB = new Talon(3);

        m_differntialDrive = new DifferentialDrive(new SpeedControllerGroup(m_leftDriveA, m_leftDriveB),
                new SpeedControllerGroup(m_rightDriveA, m_rightDriveB));
    }

    public void setSpeedAndSteer(double speed, double steer)
    {
        m_differntialDrive.arcadeDrive(speed, steer);
    }
}

