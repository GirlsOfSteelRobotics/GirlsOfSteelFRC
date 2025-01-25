package com.gos.lib.pathing;

import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.util.Units;

public class TunablePathConstraints {

    private final GosDoubleProperty m_maxTranslationVelocityInchPerSec;
    private final GosDoubleProperty m_maxTranslationAccelerationInchPerSecSquared;
    private final GosDoubleProperty m_maxRotationalVelocityInchPerSec;
    private final GosDoubleProperty m_maxRotationalAccelerationInchPerSecSquared;


    public TunablePathConstraints(
        boolean isConstant,
        String baseName,
        double defaultMaxTranslationVelocityInchPerSec,
        double defaultMaxTranslationAccelerationInchPerSecSquared,
        double defaultMaxRotationalVelocityInchPerSec,
        double defaultMaxRotationalAccelerationInchPerSecSquared) {
        m_maxTranslationVelocityInchPerSec = new GosDoubleProperty(isConstant, baseName + ".Max Velocity (inches)", defaultMaxTranslationVelocityInchPerSec);
        m_maxTranslationAccelerationInchPerSecSquared = new GosDoubleProperty(isConstant, baseName + ".Max Acceleration (inches)", defaultMaxTranslationAccelerationInchPerSecSquared);
        m_maxRotationalVelocityInchPerSec = new GosDoubleProperty(isConstant, baseName + ".Angular Velocity (degrees)", defaultMaxRotationalVelocityInchPerSec);
        m_maxRotationalAccelerationInchPerSecSquared = new GosDoubleProperty(isConstant, baseName + ".Angular Acceleration (degrees)", defaultMaxRotationalAccelerationInchPerSecSquared);
    }

    public PathConstraints getConstraints() {
        return new PathConstraints(
            Units.inchesToMeters(m_maxTranslationVelocityInchPerSec.getValue()),
            Units.inchesToMeters(m_maxTranslationAccelerationInchPerSecSquared.getValue()),
            Math.toRadians(m_maxRotationalVelocityInchPerSec.getValue()),
            Math.toRadians(m_maxRotationalAccelerationInchPerSecSquared.getValue())
        );
    }
}
