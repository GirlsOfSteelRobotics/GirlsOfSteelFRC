package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

/**
 * Add your docs here.
 */
@SuppressWarnings("PMD")
public class Limelight extends SubsystemBase {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightDriveCommand = 0.0;
    private double m_LimelightSteerCommand = 0.0;
    //private double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    private double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    //private double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    private double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    public Limelight() {
        System.out.println("Limelight"); 
    }

    public double getSteerCommand() {

        double Kp = -0.1;                     //proportional control constant - TUNE VALUE
        double min_command = 0.04;    //TUNE VALUE
        double heading_error = -tx;

        double steering_adjust = 0.0;
        if (tx > 1.0){
                steering_adjust = Kp * heading_error - min_command;
        }
        else if (tx < 1.0){
                steering_adjust = Kp * heading_error + min_command;
        }
    
        return steering_adjust; 
            
        /*
        double STEER_K = 0.03;                                        // how hard to turn toward the target
        double steer_cmd = tx * STEER_K;
        m_LimelightSteerCommand = steer_cmd;
        return m_LimelightSteerCommand;
        */

    }

    public double getDriveCommand() {
        double DRIVE_K = 0.26;                                        // how hard to drive fwd toward the target
        double DESIRED_TARGET_AREA = 13.0;                // Area of the target when the robot reaches the wall
        double MAX_DRIVE = 0.7;                                     // Simple speed limit so we don't drive too fast
        double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;
        if (drive_cmd > MAX_DRIVE)
        {
            drive_cmd = MAX_DRIVE;
        }
        m_LimelightDriveCommand = drive_cmd;
        return m_LimelightDriveCommand;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
