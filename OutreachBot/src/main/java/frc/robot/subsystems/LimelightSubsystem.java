package frc.robot.subsystems;


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    public static final double OBJECT_HEIGHT = Units.feetToMeters(10);
    public static final double ROBOT_HEIGHT = Units.feetToMeters(3);
    private final NetworkTableEntry m_yawAngle;
    private final NetworkTableEntry m_elevationAngle;
    //private final NetworkTableEntry m_targetArea;
    private final NetworkTableEntry m_targetVisibility;

    @SuppressWarnings("PMD")
    public LimelightSubsystem() {
        m_yawAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        //m_targetArea = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta");
        m_elevationAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
        m_targetVisibility = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
    }

    public double limelightAngle() {
        return m_yawAngle.getDouble(-999);
    }

    public boolean targetExists() {
        return m_targetVisibility.getDouble(0) != 0;
    }

    public double limelightDistance() {
        double d = (OBJECT_HEIGHT - ROBOT_HEIGHT) / Math.tan(Math.toRadians(m_elevationAngle.getDouble(-999)));
        return d;
    }


}



