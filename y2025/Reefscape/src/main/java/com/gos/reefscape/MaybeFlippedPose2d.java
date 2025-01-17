package com.gos.reefscape;

import choreo.util.ChoreoAllianceFlipUtil;
import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class MaybeFlippedPose2d {

    private final Pose2d m_bluePose;
    private final Pose2d m_redPose;

    public MaybeFlippedPose2d(Translation2d translation, Rotation2d rotation) {
        this(new Pose2d(translation, rotation));
    }

    public MaybeFlippedPose2d(double x, double y, Rotation2d rotation) {
        this(new Pose2d(x, y, rotation));
    }

    public MaybeFlippedPose2d(Pose2d bluePose) {
        this(bluePose, ChoreoAllianceFlipUtil.flip(bluePose));
    }

    public MaybeFlippedPose2d(Pose2d bluePose, Pose2d redPose) {
        m_bluePose = bluePose;
        m_redPose = redPose;
    }

    public Pose2d getPose() {
        if (GetAllianceUtil.isBlueAlliance()) {
            return m_bluePose;
        }
        return m_redPose;
    }


    public Pose2d getRedPose() {
        return m_redPose;
    }

    public Pose2d getBluePose() {
        return m_bluePose;
    }
}
