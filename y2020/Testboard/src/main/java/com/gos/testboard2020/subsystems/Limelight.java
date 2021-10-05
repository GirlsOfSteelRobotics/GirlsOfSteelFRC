package com.gos.testboard2020.subsystems;

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
    private double m_limelightDriveCommand = 0.0;
    private double m_limelightSteerCommand = 0.0;
    //private double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    private double m_tx;
    private double m_ty;
    private double m_ta;
    private static final double STEER_K = 0.05;                                        // how hard to turn toward the target
    private static final double DRIVE_K = 0.3;                                        // how hard to drive fwd toward the target
    private static final double DESIRED_TARGET_AREA = 13.0;                           // Area of the target when the robot reaches the wall
    private static final double MAX_DRIVE = 0.3;                                     // Simple speed limit so we don't drive too fast


    public Limelight() {
        System.out.println("Limelight");
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


        double steerCmd = m_tx * STEER_K;
        m_limelightSteerCommand = steerCmd;
        return m_limelightSteerCommand;

    }

    public double estimateDistance() {
        double distance = 48.75 / (Math.tan(Math.toRadians(m_ty + 20)));
        System.out.println("ty: " + m_ty);
        System.out.println("distance: " + distance);
        return distance;
    }

    public double getDriveCommand(double distance) {
        // double driveCmd = (DESIRED_TARGET_AREA - m_ta) * DRIVE_K;
        // if (driveCmd > MAX_DRIVE)
        // {
        //     driveCmd = MAX_DRIVE;
        // }
        // m_limelightDriveCommand = driveCmd;
        // return m_limelightDriveCommand;

        double driveCmd = distance*0.1;
        m_limelightDriveCommand = driveCmd;
        return m_limelightDriveCommand;
    }

    @Override
    public void periodic() {
        m_tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        m_ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        m_ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        // This method will be called once per scheduler run
    }
}
