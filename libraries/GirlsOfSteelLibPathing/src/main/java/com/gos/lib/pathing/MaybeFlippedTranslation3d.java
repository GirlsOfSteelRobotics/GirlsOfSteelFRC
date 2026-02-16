package com.gos.lib.pathing;

import choreo.util.ChoreoAllianceFlipUtil;
import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation3d;

public class MaybeFlippedTranslation3d {

    private final Translation3d m_blueTranslation;
    private final Translation3d m_redTranslation;

    public MaybeFlippedTranslation3d(double x, double y, double z) {
        m_blueTranslation = new Translation3d(x,y,z);
        m_redTranslation = ChoreoAllianceFlipUtil.flip(m_blueTranslation);
    }

    public Translation3d getTranslation() {
        if(GetAllianceUtil.isBlueAlliance()) {
            return m_blueTranslation;
        } else {
            return m_redTranslation;
        }
    }
}
