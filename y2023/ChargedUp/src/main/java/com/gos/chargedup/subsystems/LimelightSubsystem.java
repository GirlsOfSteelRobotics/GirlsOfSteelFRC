package com.gos.chargedup.subsystems;

import java.util.List;
//import edu.wpi.first.math.util.Units;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.PhotonPipelineResult;
//import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    PhotonCamera m_camera;

    public LimelightSubsystem() {
        m_camera = new PhotonCamera("photonvision");
    }

    public PhotonPipelineResult getResult() {
        return m_camera.getLatestResult();
    }

    public List<PhotonTrackedTarget> getTargets(PhotonPipelineResult result) {
        if (result.hasTargets()) {
            return result.getTargets();
        } else {
            return null;
        }
    }

    public void targetData2D(PhotonTrackedTarget target) {
        double yaw = target.getYaw();
        double pitch = target.getPitch();
        double area = target.getArea();
        double skew = target.getSkew();
        //Transform2d pose = target.getCameraToTarget();
        //List<TargetCorner> corners = target.getCorners();
    }



}

