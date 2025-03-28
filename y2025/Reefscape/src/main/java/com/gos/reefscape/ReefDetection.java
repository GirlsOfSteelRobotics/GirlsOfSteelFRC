package com.gos.reefscape;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.List;

public class ReefDetection {
    private final PhotonCamera m_photonCamera;


    public ReefDetection() {
        m_photonCamera = new PhotonCamera("DriverCam");
    }

    public double getYaw() {
        List<PhotonPipelineResult> results = m_photonCamera.getAllUnreadResults();
        if (results.isEmpty()) {
            return -1;
        }
        PhotonPipelineResult lastOne = results.get(results.size() - 1);
        if (lastOne.getBestTarget() == null) {
            return -1;

        }
        return lastOne.getBestTarget().getYaw();
    }

}
