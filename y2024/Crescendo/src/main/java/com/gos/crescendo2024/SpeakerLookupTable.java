package com.gos.crescendo2024;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class SpeakerLookupTable {
    private static final double MIN_DISTANCE_ANGLE = 10.0;
    private static final double MAX_DISTANCE_ANGLE = 45.0;

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        m_table.put(Units.feetToMeters(0), MIN_DISTANCE_ANGLE);
        m_table.put(Units.feetToMeters(9), 30.0);
        m_table.put(Units.feetToMeters(18), 40.0);
        m_table.put(Units.feetToMeters(27), MAX_DISTANCE_ANGLE);

    }

    public double getVelocityTable(double distance) {
        return m_table.get(distance);
    }
}
