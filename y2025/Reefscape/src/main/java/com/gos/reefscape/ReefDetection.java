package com.gos.reefscape;

import com.gos.lib.logging.LoggingUtil;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.List;
import java.util.Optional;

public class ReefDetection {
    private static final double OFFSET =  - 1.35;
    private final PhotonCamera m_photonCamera;

    private Optional<Double> m_bestYaw;

    private final LoggingUtil m_logging;


    public ReefDetection() {
        m_photonCamera = new PhotonCamera("USB_Camera");

        m_logging = new LoggingUtil("Reef Camera");
        m_logging.addDouble("Best Yaw", () -> m_bestYaw.orElse(999.0));
    }

    public Optional<Double> getYaw() {
        return m_bestYaw;
    }

    public void periodic() {
        List<PhotonPipelineResult> results = m_photonCamera.getAllUnreadResults();
        if (results.isEmpty()) {
            m_bestYaw = Optional.empty();
            return;
        }
        PhotonPipelineResult lastOne = results.get(results.size() - 1);
        if (lastOne.getBestTarget() == null) {
            m_bestYaw = Optional.empty();
            return;
        }
        m_bestYaw = Optional.of(lastOne.getBestTarget().getYaw() + OFFSET);

        m_logging.updateLogs();
    }
}
