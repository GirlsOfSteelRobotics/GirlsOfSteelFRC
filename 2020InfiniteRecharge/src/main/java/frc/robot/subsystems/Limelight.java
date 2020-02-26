package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.lib.PropertyManager;

/**
 * Add your docs here.
 */
@SuppressWarnings("PMD")
public class Limelight extends SubsystemBase {

    /// how hard to turn toward the target
    private static final PropertyManager.IProperty<Double> STEER_K = new PropertyManager.DoubleProperty("LimelightSteerK", 0.05);

    // how hard to drive fwd toward the target
    private static final PropertyManager.IProperty<Double> DRIVE_K = new PropertyManager.DoubleProperty("LimelightDriveK", 0.3);

    // Area of the target when the robot reaches the wall
    private static final PropertyManager.IProperty<Double> DESIRED_TARGET_AREA = new PropertyManager.DoubleProperty("LimelightTargetArea", 13.0);

    // Simple speed limit so we don't drive too fast
    private static final PropertyManager.IProperty<Double> MAX_DRIVE = new PropertyManager.DoubleProperty("LimelihtMaxDrive", 0.3);

    private static final PropertyManager.IProperty<Double> CAMERA_HEIGHT_OFFSET = new PropertyManager.ConstantProperty<>("LimelightHeightOffset", 48.75);
    private static final PropertyManager.IProperty<Double> CAMERA_ANGLE_OFFSET = new PropertyManager.ConstantProperty<>("LimelightAngleOffset", 20.0);

    private static final double ALLOWABLE_ERROR = 2;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //private boolean m_LimelightHasValidTarget = false;
    private double m_limelightDriveCommand = 0.0;
    private double m_limelightSteerCommand = 0.0;
    //private double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    private double m_tx;
    private double m_ty;
    private double m_ta;

    private final NetworkTableEntry m_limelightIsAimedEntry;


    public Limelight() {
        System.out.println("Limelight"); 

        ShuffleboardTab tab = Shuffleboard.getTab("Driver Tab");
        m_limelightIsAimedEntry = tab
            .add("Limelight Is Aimed", limelightIsAimed())
            .withSize(3, 1)
            .withPosition(0, 1)
            .getEntry();
        Shuffleboard.selectTab("Driver Tab");
       
        VideoSource limelightSource = CameraServer.getInstance().getServer("limelight").getSource();
        tab.add("limelight", limelightSource)
            .withSize(4, 3)
            .withPosition(0, 5);
    }

    public double getSteerCommand() {

        // double Kp = -0.1;                     //proportional control constant - TUNE VALUE
        // double min_command = 0.04;    //TUNE VALUE
        // double heading_error = -tx;

        // double steering_adjust = 0.0;
        // if (tx > 1.0){
        //         steering_adjust = Kp * heading_error - min_command;
        // }
        // else if (tx < 1.0){
        //         steering_adjust = Kp * heading_error + min_command;
        // }
    
        // return steering_adjust; 
            
        
        double steerCmd = m_tx * STEER_K.getValue();
        m_limelightSteerCommand = steerCmd;
        return m_limelightSteerCommand;
    }

    public double estimateDistance() {
        double distance = CAMERA_HEIGHT_OFFSET.getValue() / (Math.tan(Math.toRadians(m_ty + CAMERA_ANGLE_OFFSET.getValue())));
        System.out.println("ty: " + m_ty);
        System.out.println("distance: " + distance);
        return distance;
    }

    public double getDriveCommand() {
        double driveCmd = (DESIRED_TARGET_AREA.getValue() - m_ta) * DRIVE_K.getValue();
        if (driveCmd > MAX_DRIVE.getValue()) {
            driveCmd = MAX_DRIVE.getValue();
        }
        m_limelightDriveCommand = driveCmd;
        return m_limelightDriveCommand;
    }

    @Override
    public void periodic() {
        m_tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        m_ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        m_ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        // This method will be called once per scheduler run

        m_limelightIsAimedEntry.setBoolean(limelightIsAimed());
    }

    public boolean limelightIsAimed() {
        return Math.abs(m_tx) <= ALLOWABLE_ERROR;
    }
}
