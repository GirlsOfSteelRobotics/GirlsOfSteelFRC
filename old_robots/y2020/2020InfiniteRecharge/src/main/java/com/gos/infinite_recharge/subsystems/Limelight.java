package com.gos.infinite_recharge.subsystems;

import com.gos.infinite_recharge.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.sensors.LidarLite;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Add your docs here.
 */
@SuppressWarnings("PMD")
public class Limelight extends SubsystemBase {

    /// how hard to turn toward the target
    private static final GosDoubleProperty STEER_KP_PROPERTY = new GosDoubleProperty(false,
            "LimelightSteerK", 0.05);

    private static final GosDoubleProperty STEER_KI_PROPERTY = new GosDoubleProperty(false,
            "LimelightSteerKI", 0.0);

    private static final GosDoubleProperty STEER_KD_PROPERTY = new GosDoubleProperty(false,
            "LimelightSteerKD", 0.0);

    // how hard to drive fwd toward the target
    private static final GosDoubleProperty DRIVE_K = new GosDoubleProperty(false,
            "LimelightDriveK", 0.3);

    // Area of the target when the robot reaches the wall
    private static final GosDoubleProperty DESIRED_TARGET_AREA = new GosDoubleProperty(false,
            "LimelightTargetArea", 13.0);

    // Simple speed limit so we don't drive too fast
    private static final GosDoubleProperty MAX_DRIVE = new GosDoubleProperty(false,
            "LimelihtMaxDrive", 0.3);

    // Measured from middle of Limelight to middle of high target
    private static final GosDoubleProperty CAMERA_HEIGHT_OFFSET = new GosDoubleProperty(true,
            "LimelightHeightOffset", 60.0);
    private static final GosDoubleProperty CAMERA_ANGLE_OFFSET = new GosDoubleProperty(true,
            "LimelightAngleOffset", 20.0);

    private static final double ALLOWABLE_ERROR = 2;
    private static final double MIN_AREA = 0.5;
    private final PIDController m_steerPID;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    // private boolean m_LimelightHasValidTarget = false;
    private double m_limelightDriveCommand = 0.0;
    private final double m_limelightSteerCommand = 0.0;
    private final LidarLite m_lidarLite;
    // private double tv =
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    private double m_tx;
    private double m_ty;
    private double m_ta;

    private final GenericEntry m_limelightIsAimedEntry;

    public Limelight(ShuffleboardTab driverDisplayTab, LidarLite lidarLite) {
        System.out.println("Limelight");

        m_lidarLite = lidarLite;

        m_limelightIsAimedEntry = driverDisplayTab.add("Limelight Is Aimed", limelightIsAimed()).withSize(4, 1)
                .withPosition(4, 0).getEntry();

        if (RobotBase.isReal()) {
            HttpCamera limelightFeed = new HttpCamera("Limelight Camera", "http://10.35.4.11:5800/stream.mjpg", HttpCameraKind.kMJPGStreamer);
            driverDisplayTab.add("limelight", limelightFeed)
                     .withSize(4, 3)
                     .withPosition(3, 5)
                     .withWidget(BuiltInWidgets.kCameraStream);
        }

        m_steerPID = new PIDController(0, 0, 0);

        turnLimelightOff();
    }

    public void zoom1X() {
        //        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }

    public void zoomIfNeeded() {
        //        if( m_ta < 3.5 && m_ty < 12.0) {
        //            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(2);
        //        }
        //        //if( m_ta < 3.5 && m_ty < 12.0) {
        //           // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(2);
        //        //}
    }

    public double getSteerCommand() {

        // double Kp = -0.1; //proportional control constant - TUNE VALUE
        // double min_command = 0.04; //TUNE VALUE
        // double heading_error = -tx;

        // double steering_adjust = 0.0;
        // if (tx > 1.0){
        // steering_adjust = Kp * heading_error - min_command;
        // }
        // else if (tx < 1.0){
        // steering_adjust = Kp * heading_error + min_command;
        // }

        // return steering_adjust;
        if (m_ta < MIN_AREA) {
            return 0;
        }

        // double steerCmd = m_tx * STEER_K.getValue();
        // m_limelightSteerCommand = steerCmd;
        // return m_limelightSteerCommand;
        double steeringSpeed =  m_steerPID.calculate(-m_tx, 0);
        steeringSpeed += Math.copySign(Constants.MINIMUM_TURN_SPEED, steeringSpeed);

        return steeringSpeed;
    }

    public double estimateDistance() {
        double distance = CAMERA_HEIGHT_OFFSET.getValue()
                / (Math.tan(Math.toRadians(m_ty + CAMERA_ANGLE_OFFSET.getValue())));
        System.out.println("ty: " + m_ty);
        System.out.println("distance: " + distance);
        return distance;
    }

    public double estimateDistanceWithLidar() {
        double limelightDistance = estimateDistance();
        double lidarDistance = m_lidarLite.getDistance();

        double limelightLidarCompare = Math.abs((limelightDistance - lidarDistance) / lidarDistance);

        System.out.println("Lidar distance: " + lidarDistance);
        System.out.println("Limelight distance: " + limelightDistance);

        if (limelightLidarCompare <= 0.20) {
            System.out.println("Returning Lidar distance");
            return lidarDistance;
        } else {
            System.out.println("Returning Limelight distance");
            return limelightDistance;
        }
    }

    public double getDriveCommand() {
        double driveCmd = (DESIRED_TARGET_AREA.getValue() - m_ta) * DRIVE_K.getValue();
        if (driveCmd > MAX_DRIVE.getValue()) {
            driveCmd = MAX_DRIVE.getValue();
        }
        m_limelightDriveCommand = driveCmd;
        return m_limelightDriveCommand;

    }

    public void turnLimelightOn() {
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setDouble(3);
    }

    public void turnLimelightOff() {
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setDouble(1);
    }

    @Override
    public void periodic() {
        m_tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        m_ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        m_ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        // This method will be called once per scheduler run
        m_steerPID.setP(STEER_KP_PROPERTY.getValue());
        m_steerPID.setI(STEER_KI_PROPERTY.getValue());
        m_steerPID.setD(STEER_KD_PROPERTY.getValue());
        m_limelightIsAimedEntry.setBoolean(limelightIsAimed());
    }

    public boolean limelightIsAimed() {
        boolean isAimed = Math.abs(m_tx) <= ALLOWABLE_ERROR;
        boolean isBig = m_ta > MIN_AREA;
        return isAimed && isBig;
    }
}
