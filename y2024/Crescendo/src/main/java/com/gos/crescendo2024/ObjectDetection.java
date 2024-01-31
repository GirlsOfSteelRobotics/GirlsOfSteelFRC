package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.estimation.TargetModel;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.simulation.VisionTargetSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;

import static com.gos.crescendo2024.FieldConstants.StagingLocations.CENTERLINE_TRANSLATIONS;
import static com.gos.crescendo2024.FieldConstants.StagingLocations.SPIKE_TRANSLATIONS;

public class ObjectDetection {

    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(new Translation3d(Units.inchesToMeters(12), Units.inchesToMeters(11), Units.inchesToMeters(15)), new Rotation3d(0, 0, 0));
    private final PhotonCamera m_photonCamera;
    private final TargetModel m_targetModel;
    private final VisionSystemSim m_visionSim;

    private final PhotonCameraSim m_cameraSim;

    public ObjectDetection() {
        m_photonCamera = new PhotonCamera("objectDetection");

        m_visionSim = new VisionSystemSim("objectDetectionSim");

        m_targetModel = new TargetModel(Units.inchesToMeters(18));

        m_cameraSim = new PhotonCameraSim(m_photonCamera);
        m_visionSim.addCamera(m_cameraSim, ROBOT_TO_CAMERA);
        m_cameraSim.enableRawStream(true);
        m_cameraSim.enableProcessedStream(true);
        m_cameraSim.enableDrawWireframe(true);

        for (Translation2d centerlineTranslation : CENTERLINE_TRANSLATIONS) {
            Pose3d targetPose = new Pose3d(new Translation3d(centerlineTranslation.getX(), centerlineTranslation.getY(), 0), new Rotation3d());
            VisionTargetSim visionTarget = new VisionTargetSim(targetPose, m_targetModel);
            m_visionSim.addVisionTargets(visionTarget);
        }

        for (Translation2d spikeTranslation : SPIKE_TRANSLATIONS) {
            Pose3d targetPose = new Pose3d(new Translation3d(spikeTranslation.getX(), spikeTranslation.getY(), 0), new Rotation3d());
            VisionTargetSim visionTarget = new VisionTargetSim(targetPose, m_targetModel);
            m_visionSim.addVisionTargets(visionTarget);
        }

    }

    public void updateObjectDetectionSimulation(Pose2d chassisLocation) {
        m_visionSim.update(chassisLocation);
    }

    public static double calculateDistanceToTarget(
        double cameraHeightMeters,
        double targetHeightMeters,
        double cameraPitchRadians,
        double targetPitchRadians,
        double targetYawRadians) {
        return (targetHeightMeters - cameraHeightMeters)
            / (Math.tan(cameraPitchRadians + targetPitchRadians) * Math.cos(targetYawRadians));
    }

    public List<Pose2d> objectLocations(Pose2d chassisLocation) {
        List<Pose2d> objectLocationsList = new ArrayList<>();
        PhotonPipelineResult lastestResult = m_photonCamera.getLatestResult();
        for (PhotonTrackedTarget result : lastestResult.getTargets()) {
            double distance = calculateDistanceToTarget(
                ROBOT_TO_CAMERA.getZ(),
                0,
                ROBOT_TO_CAMERA.getRotation().getY(),
                Units.degreesToRadians(result.getPitch()),
                Units.degreesToRadians(result.getYaw())
                );
            Translation2d relToCamera = PhotonUtils.estimateCameraToTargetTranslation(distance, Rotation2d.fromDegrees(result.getYaw()));
            Transform2d adjustForRot = new Transform2d(relToCamera, new Rotation2d());
            objectLocationsList.add(chassisLocation.transformBy(adjustForRot));
        }
        return objectLocationsList;
    }
}
