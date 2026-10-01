package com.gos.lib.pathing;

import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.util.Units;

/**
 * Property class that wraps up configuring Path Planners {@link PathConstraints} class
 */
public class TunablePathConstraints {

    private final GosDoubleProperty m_maxTranslationVelocityInchPerSec;
    private final GosDoubleProperty m_maxTranslationAccelerationInchPerSecSquared;
    private final GosDoubleProperty m_maxRotationalVelocityInchPerSec;
    private final GosDoubleProperty m_maxRotationalAccelerationInchPerSecSquared;

    /**
     * Constructor. Values are in inch/s and inch/s/s
     * @param isConstant If the properties should be constant, aka not tunable
     * @param baseName The prefix used when naming the four properties.
     * @param defaultMaxTranslationVelocityInchPerSec The default max translation speed
     * @param defaultMaxTranslationAccelerationInchPerSecSquared The default max translation acceleration
     * @param defaultMaxRotationalVelocityInchPerSec The default max rotational velocity
     * @param defaultMaxRotationalAccelerationInchPerSecSquared The default max rotational acceleration
     */
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

    /**
     * Constructs a {@link PathConstraints} from the properties
     * @return The path constraints
     */
    public PathConstraints getConstraints() {
        return new PathConstraints(
            Units.inchesToMeters(m_maxTranslationVelocityInchPerSec.getValue()),
            Units.inchesToMeters(m_maxTranslationAccelerationInchPerSecSquared.getValue()),
            Math.toRadians(m_maxRotationalVelocityInchPerSec.getValue()),
            Math.toRadians(m_maxRotationalAccelerationInchPerSecSquared.getValue())
        );
    }
}
