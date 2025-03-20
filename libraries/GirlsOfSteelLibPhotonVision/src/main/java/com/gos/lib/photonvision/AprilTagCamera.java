package com.gos.lib.photonvision;

import com.gos.lib.field.AprilTagCameraObject;
import com.gos.lib.field.AprilTagCameraObject.DebugConfig;
import com.gos.lib.field.BaseGosField;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class for doing april tag work for a single photon vision camera. Reads the results and does basic STDDEV calculations.
 */
public class AprilTagCamera {
    public static final double DEFAULT_SINGLE_TAG_MAX_DISTANCE = 3.2;
    public static final double DEFAULT_SINGLE_TAG_MAX_AMBIGUITY = 1;
    public static final DebugConfig DEFAULT_DEBUG_CONFIG = new DebugConfig();
    public static final Matrix<N3, N1> DEFAULT_SINGLE_TAG_STDDEV = VecBuilder.fill(1.5, 1.5, 16); // (4, 4, 8)
    public static final Matrix<N3, N1> DEFAULT_MULTI_TAG_STDDEV = VecBuilder.fill(0.25, 0.25, 4); // (0.5, 0.5, 1)


    private final TunableTransform3d m_robotToCamera;
    private final String m_cameraName;
    private final Matrix<N3, N1> m_singleTagStddev;
    private final Matrix<N3, N1> m_multiTagStddev;
    private final double m_singleTagMaxDistance;
    private final double m_singleTagMaxAmbiguity;


    private final PhotonCamera m_photonCamera;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final PhotonCameraSim m_cameraSim;

    private final AprilTagCameraObject m_field;

    // Cached values
    private Optional<EstimatedRobotPose> m_maybeResult;
    private Optional<PhotonPipelineResult> m_lastPipelineResult;
    private int m_numTargetsSeen;
    private double m_avgDistanceToTag;
    private double m_avgAmbiguity;
    private boolean m_goodStddev;

    private final LoggingUtil m_logger;

    /**
     * Constructor. Uses default STDDEV values for single and multi-tag.
     *
     * @param aprilTagLayout The april tag layout
     * @param field The GOS field, used to draw results onto
     * @param name The name of the camera. Should match camera name in photonvision gui
     * @param transform3d The transform used to set the extrinsic location of the camera on the robot
     */
    @Deprecated
    public AprilTagCamera(AprilTagFieldLayout aprilTagLayout, BaseGosField field, String name, TunableTransform3d transform3d) {
        this(aprilTagLayout, field, name, transform3d, DEFAULT_SINGLE_TAG_STDDEV, DEFAULT_MULTI_TAG_STDDEV);
    }

    @Deprecated
    public AprilTagCamera(AprilTagFieldLayout aprilTagLayout, BaseGosField field, String name, TunableTransform3d transform3d, Matrix<N3, N1> singleTagStddev, Matrix<N3, N1> multiTagStddev) {
        this(aprilTagLayout, field, name, transform3d, DEFAULT_SINGLE_TAG_MAX_DISTANCE, DEFAULT_SINGLE_TAG_MAX_AMBIGUITY, singleTagStddev, multiTagStddev, DEFAULT_DEBUG_CONFIG, true, true, true);
    }


    /**
     * Constructor
     *
     * @param aprilTagLayout The april tag layout
     * @param field The GOS field, used to draw results onto
     * @param name The name of the camera. Should match camera name in photonvision gui
     * @param transform3d The transform used to set the extrinsic location of the camera on the robot
     * @param singleTagStddev The base STDDEV to use if only a single april tag is seen
     * @param multiTagStddev The base STDDEV to use if multiple april tags are seen
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    /* default */ AprilTagCamera(AprilTagFieldLayout aprilTagLayout, BaseGosField field, String name, TunableTransform3d transform3d, double singleTagMaxDistance, double singleTagMaxAmbiguity, Matrix<N3, N1> singleTagStddev, Matrix<N3, N1> multiTagStddev, DebugConfig fieldDebugSettings, boolean simEnableRawStream, boolean simEnableProcessedStream, boolean simEnableDrawWireframe) {
        m_cameraName = name;
        m_robotToCamera = transform3d;
        m_photonCamera = new PhotonCamera(m_cameraName);
        m_singleTagMaxDistance = singleTagMaxDistance;
        m_singleTagMaxAmbiguity = singleTagMaxAmbiguity;
        m_singleTagStddev = singleTagStddev;
        m_multiTagStddev = multiTagStddev;

        m_field = new AprilTagCameraObject(field, m_cameraName, fieldDebugSettings);

        m_photonPoseEstimator = new PhotonPoseEstimator(aprilTagLayout, PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, m_robotToCamera.getTransform());
        m_photonPoseEstimator.setMultiTagFallbackStrategy(PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY);

        if (RobotBase.isSimulation()) {
            m_cameraSim = new PhotonCameraSim(m_photonCamera);

            m_cameraSim.enableRawStream(simEnableRawStream);
            m_cameraSim.enableProcessedStream(simEnableProcessedStream);
            m_cameraSim.enableDrawWireframe(simEnableDrawWireframe);
        } else {
            m_cameraSim = null;
        }

        m_maybeResult = Optional.empty();

        m_logger = new LoggingUtil("GosCameras/" + m_cameraName);
        m_logger.addDouble("Targets Seen", () -> m_numTargetsSeen);
        m_logger.addDouble("Average Distance To Target", () -> m_avgDistanceToTag);
        m_logger.addDouble("Average Ambiguity", () -> m_avgAmbiguity);
        m_logger.addBoolean("Good Stddev", () -> m_goodStddev);
    }

    /**
     * Asks the camera for a new pose estimate.
     * @param prevEstimatedRobotPose The current estimate for the robots location on the field.
     */
    public void update(Pose2d prevEstimatedRobotPose) {
        Transform3d robotToCamera = m_robotToCamera.getTransform();

        m_photonPoseEstimator.setRobotToCameraTransform(robotToCamera);
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        m_maybeResult = Optional.empty();
        m_lastPipelineResult = Optional.empty();

        for (PhotonPipelineResult result : m_photonCamera.getAllUnreadResults()) {
            update(robotToCamera, result);
        }
    }

    private void update(Transform3d robotToCamera, PhotonPipelineResult result) {
        m_maybeResult = m_photonPoseEstimator.update(result);

        if (m_maybeResult.isEmpty()) {
            m_field.clearCameraResult();
        } else {
            List<Pose3d> aprilTags = new ArrayList<>();
            EstimatedRobotPose estimatedRobotPose = m_maybeResult.get();
            for (PhotonTrackedTarget targetUsed : m_maybeResult.get().targetsUsed) {
                Pose3d bestTransformPosition =
                    estimatedRobotPose.estimatedPose
                        .transformBy(robotToCamera)
                        .transformBy(targetUsed.getBestCameraToTarget());
                aprilTags.add(bestTransformPosition);
            }
            m_field.setCameraResult(m_maybeResult.get().estimatedPose, aprilTags);
        }

        m_lastPipelineResult = Optional.of(result);

        m_logger.updateLogs();
    }

    /**
     * Gets the last pose estimate from the camera, if it exists.
     * @return The last estimate
     */
    public Optional<EstimatedRobotPose> getEstimateGlobalPose() {
        return m_maybeResult;
    }

    /**
     * Calculates the STDDEV for the measurement. This is based on how many tags are seen, the ambiguity of the tags, and how far away they are.
     *
     * @param estimatedPose3d The estimated pose
     * @return A corrected estimate of the cameras STDDEV measurement
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public Matrix<N3, N1> getEstimationStdDevs(Pose3d estimatedPose3d) {
        Matrix<N3, N1> estStdDevs = m_singleTagStddev;
        if (m_lastPipelineResult.isEmpty()) {
            return estStdDevs;
        }
        List<PhotonTrackedTarget> targets = m_lastPipelineResult.get().getTargets();
        m_numTargetsSeen = 0;
        double sumDist = 0;
        double sumAmbiguity = 0;
        Pose2d estimatedPose = estimatedPose3d.toPose2d();
        for (PhotonTrackedTarget tgt : targets) {
            Optional<Pose3d> tagPose = m_photonPoseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
            if (tagPose.isEmpty()) {
                continue;
            }
            m_numTargetsSeen++;
            sumDist +=
                tagPose.get().toPose2d().getTranslation().getDistance(estimatedPose.getTranslation());
            sumAmbiguity += tgt.getPoseAmbiguity();
        }
        if (m_numTargetsSeen == 0) {
            return estStdDevs;
        }
        m_goodStddev = true;
        m_avgDistanceToTag = sumDist / m_numTargetsSeen;
        m_avgAmbiguity = sumAmbiguity / m_numTargetsSeen;
        // Decrease std devs if multiple targets are visible
        if (m_numTargetsSeen > 1) {
            estStdDevs = m_multiTagStddev;
        }
        // Increase std devs based on (average) distance
        if (m_numTargetsSeen == 1 && m_avgDistanceToTag > m_singleTagMaxDistance) {
            estStdDevs = VecBuilder.fill(1000, 1000, 1000);
            m_goodStddev = false;
        }
        else if (m_numTargetsSeen == 1 && m_avgAmbiguity > m_singleTagMaxAmbiguity) {
            estStdDevs = VecBuilder.fill(1000, 1000, 1000);
            m_goodStddev = false;
        }
        else if (m_numTargetsSeen == 1 && estimatedPose3d.getZ() > Units.inchesToMeters(10)) {
            estStdDevs = VecBuilder.fill(1000, 1000, 1000);
            m_goodStddev = false;
        }
        else {
            estStdDevs = estStdDevs.times(1 + (m_avgDistanceToTag * m_avgDistanceToTag / 30));
        }

        return estStdDevs;
    }

    /**
     * Tells the camera to take a screenshot. Will do it on both the input and output images.
     */
    public void takeScreenshot() {
        m_photonCamera.takeInputSnapshot();
        m_photonCamera.takeOutputSnapshot();
    }

    /**
     * Gets the latest results from the camera
     * @return The result
     */
    public Optional<PhotonPipelineResult> getLatestResult() {
        return m_lastPipelineResult;
    }

    /**
     * Returns the single camera simulator
     * @return The simulator
     */
    public PhotonCameraSim getSimulator() {
        return m_cameraSim;
    }

    /**
     * Gets the robot-to-camera transform
     * @return The transform
     */
    public Transform3d getRobotToCamera() {
        return m_robotToCamera.getTransform();
    }
}
