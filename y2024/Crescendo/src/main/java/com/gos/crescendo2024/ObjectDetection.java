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
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.estimation.TargetModel;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.simulation.VisionTargetSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;

import static com.gos.crescendo2024.FieldConstants.StagingLocations.centerlineTranslations;
import static com.gos.crescendo2024.FieldConstants.StagingLocations.spikeTranslations;

public class ObjectDetection {

    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(new Translation3d(Units.inchesToMeters(12), Units.inchesToMeters(11), Units.inchesToMeters(15)), new Rotation3d(0, 0, 0));
    private final PhotonCamera m_photonCamera;
    private final TargetModel targetModel;
    private final VisionSystemSim visionSim;

    private final PhotonCameraSim cameraSim;

    private final GoSField24 m_gosField;
    public ObjectDetection() {
        m_photonCamera = new PhotonCamera("objectDetection");

        visionSim = new VisionSystemSim("objectDetectionSim");

        targetModel = new TargetModel(Units.inchesToMeters(18));

        m_gosField = new GoSField24();

        cameraSim = new PhotonCameraSim(m_photonCamera);
        visionSim.addCamera(cameraSim, ROBOT_TO_CAMERA);
        cameraSim.enableRawStream(true);
        cameraSim.enableProcessedStream(true);
        cameraSim.enableDrawWireframe(true);

        for (Translation2d centerlineTranslation : centerlineTranslations) {
            Pose3d targetPose = new Pose3d(new Translation3d(centerlineTranslation.getX(), centerlineTranslation.getY(), 0), new Rotation3d());
            VisionTargetSim visionTarget = new VisionTargetSim(targetPose, targetModel);
            visionSim.addVisionTargets(visionTarget);
        }

        for (Translation2d spikeTranslation : spikeTranslations) {
            Pose3d targetPose = new Pose3d(new Translation3d(spikeTranslation.getX(), spikeTranslation.getY(), 0), new Rotation3d());
            VisionTargetSim visionTarget = new VisionTargetSim(targetPose, targetModel);
            visionSim.addVisionTargets(visionTarget);
        }

    }

    public void updateObjectDetectionSimulation(Pose2d chassisLocation) {
        visionSim.update(chassisLocation);
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

    public List<Pose2d> objectLocations(Pose2d ChassisLocation) {
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
            objectLocationsList.add(ChassisLocation.transformBy(adjustForRot));
        }
        return objectLocationsList;
    }
}
