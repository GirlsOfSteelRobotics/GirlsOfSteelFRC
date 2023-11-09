package com.gos.lib.sensors;

import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSensor {

    private static final double INVALID_VALUE = -999;

    public enum LedMode {
        USE_PIPELINE(0),
        FORCE_OFF(1),
        FORCE_BLINK(2),
        FORCE_ON(3);

        private final double m_val;

        LedMode(double val) {
            m_val = val;
        }
    }

    public enum CamMode {
        VISION_PROCESSING(0),
        DRIVER_CAMERA(1);

        private final double m_val;

        CamMode(double val) {
            m_val = val;
        }
    }

    // Camera -> Robot
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_area;
    private final NetworkTableEntry m_latency;
    private final NetworkTableEntry m_pythonLimelight;
    private final NetworkTableEntry m_botPoseWpiBlue;
    private final NetworkTableEntry m_botPoseWpiRed;



    // Robot -> Camera
    private final NetworkTableEntry m_ledMode;
    private final NetworkTableEntry m_camMode;
    private final NetworkTableEntry m_pipeline;
    private final NetworkTableEntry m_pythonRobot;

    public LimelightSensor() {
        this("limelight");
    }

    public LimelightSensor(String limelightName) {

        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable(limelightName);

        // Camera -> Robot
        m_isVisible = limelightTable.getEntry("tv");
        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_area = limelightTable.getEntry("ta");
        m_latency = limelightTable.getEntry("tl");
        m_pythonLimelight = limelightTable.getEntry("llpython");
        m_botPoseWpiBlue = limelightTable.getEntry("botpose_wpiblue");
        m_botPoseWpiRed = limelightTable.getEntry("botpose_wpired");

        // Robot -> Camera
        m_ledMode = limelightTable.getEntry("ledMode");
        m_camMode = limelightTable.getEntry("camMode");
        m_pipeline = limelightTable.getEntry("pipeline");
        m_pythonRobot = limelightTable.getEntry("llrobot");
    }

    // distance from wpilib docs
    public double getDistance(
        double cameraHeightMeters,
        double targetHeightMeters,
        double cameraPitchRadians) {
        return calculateDistanceToTarget(cameraHeightMeters, targetHeightMeters, cameraPitchRadians,
            getVerticalAngleRadians(), getHorizontalAngleRadians());
    }

    public double getHorizontalAngleDegrees() {
        return m_horizontalAngle.getDouble(INVALID_VALUE);
    }

    public double getHorizontalAngleRadians() {
        return Units.degreesToRadians(getHorizontalAngleDegrees());
    }

    public double getVerticalAngleDegrees() {
        return m_verticalAngle.getDouble(INVALID_VALUE);
    }

    public double getVerticalAngleRadians() {
        return Units.degreesToRadians(getVerticalAngleDegrees());
    }

    public double getArea() {
        return m_area.getDouble(INVALID_VALUE);
    }

    /**
     * Gets the latency from the camera, in seconds.
     * @return The latency
     */
    public double getLatency() {
        return Units.millisecondsToSeconds(m_latency.getDouble(INVALID_VALUE));
    }

    public Pose3d getRobotPose() {
        NetworkTableEntry botPose; // NOPMD(CloseResource)
        if (GetAllianceUtil.isBlueAlliance()) {
            botPose = m_botPoseWpiBlue;
        } else {
            botPose = m_botPoseWpiRed;
        }

        double [] botPoseArray = botPose.getDoubleArray(new double[6]);
        Rotation3d rotation3d = new Rotation3d(botPoseArray[3], Math.toRadians(botPoseArray[4]), Math.toRadians(botPoseArray[5]));
        return new Pose3d(botPoseArray[0], botPoseArray[1], botPoseArray[2], rotation3d);
    }

    public Number[] getPythonData() {
        return m_pythonLimelight.getNumberArray(new Number[]{});
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public void setPythonNumber(Number... number) {
        m_pythonRobot.setNumberArray(number);
    }

    public void setPipeline(double pipelineNum) {
        m_pipeline.setNumber(pipelineNum);
    }

    public void setLeds(LedMode ledMode) {
        m_ledMode.setDouble(ledMode.m_val);
    }

    public void setCamMode(CamMode camMode) {
        m_camMode.setDouble(camMode.m_val);
    }

    /**
     * Algorithm from https://docs.limelightvision.io/en/latest/cs_estimating_distance.html Estimates
     * range to a target using the target's elevation. This method can produce more stable results
     * than SolvePNP when well tuned, if the full 6d robot pose is not required. Note that this method
     * requires the camera to have 0 roll (not be skewed clockwise or CCW relative to the floor), and
     * for there to exist a height differential between goal and camera. The larger this differential,
     * the more accurate the distance estimate will be.
     *
     * <p>Units can be converted using the {@link edu.wpi.first.math.util.Units} class.
     *
     * @param cameraHeightMeters The physical height of the camera off the floor in meters.
     * @param targetHeightMeters The physical height of the target off the floor in meters. This
     *     should be the height of whatever is being targeted (i.e. if the targeting region is set to
     *     top, this should be the height of the top of the target).
     * @param cameraPitchRadians The pitch of the camera from the horizontal plane in radians.
     *     Positive values up.
     * @param targetPitchRadians The pitch of the target in the camera's lens in radians. Positive
     *     values up.
     * @param targetYawRadians The yaw of the target in the camera's lens in radians.
     * @return The estimated distance to the target in meters.
     */
    public static double calculateDistanceToTarget(
        double cameraHeightMeters,
        double targetHeightMeters,
        double cameraPitchRadians,
        double targetPitchRadians,
        double targetYawRadians) {
        // This was removed from wpilib in https://github.com/wpilibsuite/allwpilib/commit/f18fd41ac3701d9685e20112646b07d0263b299b
        return (targetHeightMeters - cameraHeightMeters)
            / (Math.tan(cameraPitchRadians + targetPitchRadians) * Math.cos(targetYawRadians));
    }
}
